package com.itis.example.presentation.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.itis.example.App
import com.itis.example.R
import com.itis.example.databinding.FragmentListBinding
import com.itis.example.di.ResourceProvider
import com.itis.example.domain.location.GetLocationUseCase
import com.itis.example.domain.weather.GetWeatherByNameUseCase
import com.itis.example.domain.weather.GetWeatherListUseCase
import com.itis.example.domain.weather.model.WeatherUIModel
import com.itis.example.presentation.recycler.SpaceItemDecorator
import com.itis.example.presentation.recycler.WeatherAdapter
import com.itis.example.presentation.fragment.viewmodel.ListViewModel
import com.itis.example.utils.showSnackbar
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import javax.inject.Inject

class ListFragment : Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var adapter: WeatherAdapter? = null

    @Inject
    lateinit var getWeatherByNameUseCase: GetWeatherByNameUseCase

    @Inject
    lateinit var getWeatherListUseCase: GetWeatherListUseCase

    @Inject
    lateinit var getLocationUseCase: GetLocationUseCase

    @Inject
    lateinit var resourceProvider: ResourceProvider

    private val viewModel: ListViewModel by viewModels {
        ListViewModel.provideFactory(
            getWeatherByNameUseCase,
            getWeatherListUseCase,
            getLocationUseCase,
            resourceProvider
        )
    }

    private val settings =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            granted?.run {
                when {
                    granted.values.all { true } -> viewModel.onLocationPermsIsGranted(true)
                    granted.values.all { false } -> viewModel.onLocationPermsIsGranted(false)
                    !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                            || !shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) -> {
                        showPermsOnSetting()
                    }

                    else -> {
                        showGivePermissions()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)
        observeViewModel()
        viewModel.onNeedAdapter()
        checkPermissions()
        initSearchView()
    }

    private fun initSearchView() {
        binding.run {
            svCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { q ->
                        viewModel.onLoadClick(q)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            loading.observe(viewLifecycleOwner) {
                binding.progress.isVisible = it
            }
            error.observe(viewLifecycleOwner) {
                it?.let {
                    showError(it)
                }
            }
            navigateToDetails.observe(viewLifecycleOwner) {
                it?.let {
                    navigateToDetail(it)
                }
            }
            adapter.observe(viewLifecycleOwner) {
                it?.let {
                    initAdapter(it)
                }
            }
            shouldShowRationale.observe(viewLifecycleOwner) {
                showGivePermissions()
            }
            location.observe(viewLifecycleOwner) {
                onNeedList()
            }
            perms.observe(viewLifecycleOwner) {
                onNeedLocation()
            }
            weatherUIList.observe(viewLifecycleOwner) {
                it?.let {
                    submitList(it)
                }
            }
            makeSnackBar.observe(viewLifecycleOwner) {
                it?.let {
                    showSnackBar(it)
                }
            }
        }
    }

    private fun initAdapter(listAdapter: WeatherAdapter) {
        val itemDecoration =
            SpaceItemDecorator(
                this.requireContext(),
                16.0f
            )
        with(binding) {
            adapter = listAdapter
            rvWeather.adapter = ScaleInAnimationAdapter(listAdapter)
            rvWeather.addItemDecoration(itemDecoration)
        }
    }

    private fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                viewModel.onLocationPermsIsGranted(true)
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                requestPerms()
            }

            else -> {
                permission.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun showGivePermissions() {
        binding.run {
            Snackbar.make(
                binding.root.rootView,
                getString(R.string.give_permissions),
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.allow)) { requestPerms() }
                .show()
        }
    }

    private fun submitList(weatherList: List<WeatherUIModel>) {
        adapter?.submitList(weatherList)
    }

    private fun showSnackBar(text: String) {
        Snackbar.make(
            binding.root,
            text,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    private fun requestPerms() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        activity?.requestPermissions(permissions, 0)
    }

    private fun showPermsOnSetting() {
        binding.run {
            Snackbar.make(
                root.rootView,
                getString(R.string.permissions_are_needed),
                Snackbar.LENGTH_LONG
            )
                .setAction(
                    getString(R.string.go_to_settings)
                ) {
                    openApplicationSettings()
                }
                .show()
        }
    }

    private fun openApplicationSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + requireContext().packageName)
        )
        settings.launch(appSettingsIntent)
    }

    private fun showError(error: Throwable) {
        binding.root
            .showSnackbar(error.message ?: "Error")
    }

    private fun navigateToDetail(idOfCity: Int) {
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out,
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out
            )
            .replace(
                R.id.container,
                DetailFragment.newInstance(idOfCity),
                "ToDetail"
            )
            .addToBackStack("BackToList")
            .commit()
    }

    override fun onDestroy() {
        _binding = null
        adapter = null
        super.onDestroy()
    }
}
