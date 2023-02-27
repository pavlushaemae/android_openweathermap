package com.itis.example.ui.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.itis.example.R
import com.itis.example.data.service.WeatherService
import com.itis.example.data.service.impl.WeatherServiceImpl
import com.itis.example.databinding.FragmentListBinding
import com.itis.example.ui.recycler.SpaceItemDecorator
import com.itis.example.ui.recycler.WeatherAdapter
import com.itis.example.utils.hideKeyboard
import com.itis.example.utils.showSnackbar
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.coroutines.launch
import timber.log.Timber

class ListFragment : Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var weatherService: WeatherService? = null
    private var adapter: WeatherAdapter? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var userLongitude: Double = DEFAULT_LONGITUDE
    private var userLatitude: Double = DEFAULT_LATITUDE


    private val settings =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            granted?.run {
                when {
                    granted.values.all { true } -> {
                        findLocation()
                    }
                    !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                            || !shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) -> {
                        showPermsOnSetting()
                    }
                    else -> {
                        binding.run {
                            Snackbar.make(
                                binding.root.rootView,
                                "Дайте пермишены",
                                Snackbar.LENGTH_LONG
                            )
                                .setAction("Разрешаю") { requestPerms() }
                                .show()
                        }
                    }
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)
        weatherService = WeatherServiceImpl
        val itemDecoration =
            SpaceItemDecorator(
                this.requireContext(),
                16.0f
            )

        adapter = WeatherAdapter {
            navigateToDetail(it.id)
        }
        binding.run {
            findLocation()
            adapter?.let {
                svCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        onLoadClick()
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }

                })
                rvWeather.adapter = ScaleInAnimationAdapter(it)
                rvWeather.addItemDecoration(itemDecoration)

            }
        }
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
                "Необходимо дать пермишены",
                Snackbar.LENGTH_LONG
            )
                .setAction(
                    "Перейти в настройки"
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

    private fun onLoadClick() {
        binding.run {
            svCity.hideKeyboard()
            loadWeather(svCity.query.toString())
        }
    }

    private fun findLocation() {
        when {
            ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                showLoading(true)
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireActivity()).also {
                        it.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                Timber.e(location.toString())
                                if (location != null) {
                                    userLatitude = location.latitude
                                    userLongitude = location.longitude
                                } else {
                                    Snackbar.make(
                                        binding.root,
                                        "Локация не найдена",
                                        Snackbar.LENGTH_LONG
                                    )
                                        .show()
                                }
                                submitRVList()
                            }
                    }

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

    private fun submitRVList() {
        lifecycleScope.launch {
            Timber.e("$userLongitude, $userLatitude")
            adapter?.submitList(
                weatherService?.getSomeCities(
                    userLatitude,
                    userLongitude,
                    10
                )
            )
            showLoading(false)
        }
    }

    private fun loadWeather(city: String) {
        lifecycleScope.launch {
            try {
                showLoading(true)
                weatherService?.getWeatherByName(city).let {
                    it?.apply {
                        navigateToDetail(this.id)
                    }
                }
            } catch (error: Throwable) {
                showError(error)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.progress.isVisible = isShow
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
        weatherService = null
        fusedLocationClient = null
        super.onDestroy()
    }

    companion object {
        private const val DEFAULT_LATITUDE = 55.75
        private const val DEFAULT_LONGITUDE = 37.61
    }
}
