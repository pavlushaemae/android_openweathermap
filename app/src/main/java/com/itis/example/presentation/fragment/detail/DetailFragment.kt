package com.itis.example.presentation.fragment.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.itis.example.R
import com.itis.example.databinding.FragmentDetailBinding
import com.itis.example.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val cityId by lazy {
        arguments?.getInt(ARG_ID) ?: 0
    }

    @Inject
    lateinit var viewModelFactory: DetailViewModel.DetailViewModelFactory

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModel.provideFactory(viewModelFactory, cityId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        observeViewModel()
        viewModel.loadWeather()
    }

    private fun observeViewModel() {
        with(viewModel) {
            fillViews()
            loading.observe(viewLifecycleOwner) {
                binding.progress.isVisible = it
            }
            error.observe(viewLifecycleOwner) {
                it?.let {
                    showError(it)
                }
            }
        }
    }

    private fun fillViews() {
        viewModel.weather.observe(viewLifecycleOwner) {
            it?.let {
                binding.run {
                    tvName.text = it.cityName
                    tvHumidity.text = it.humidity
                    tvTemp.text = it.temperature
                    tvWind.text = it.wind
                    tvWindSpeed.text = it.windSpeed
                    tvPressure.text = it.pressure
                    tvTempFeel.text = it.tempFeel
                    tvSeaLevel.text = it.seaLevel
                    tvTempMax.text = it.tempMax
                    tvMinTemp.text = it.minTemp
                    ivIcon.load(it.icon) {
                        crossfade(true)
                    }
                }
            }
        }
    }

    private fun showError(error: Throwable) {
        binding.root
            .showSnackbar(error.message ?: "Error")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_ID = "arg_id"

        fun newInstance(idOfCity: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, idOfCity)
                }
            }
    }
}
