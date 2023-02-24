package com.itis.example.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.itis.example.R
import com.itis.example.data.service.WeatherService
import com.itis.example.data.service.impl.WeatherServiceImpl
import com.itis.example.databinding.FragmentListBinding
import com.itis.example.ui.recycler.SpaceItemDecorator
import com.itis.example.ui.recycler.WeatherAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

class ListFragment : Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var songService: WeatherService? = null
    private var adapter: WeatherAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)
        songService = WeatherServiceImpl()
        val itemDecoration =
            SpaceItemDecorator(
                this.requireContext(),
                16.0f
            )

        adapter = WeatherAdapter {
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
                    DetailFragment.newInstance(it.id),
                    "ToDetail"
                )
                .addToBackStack("BackToList")
                .commit()
        }
        binding.run {
            adapter?.let {
                rvSong.adapter = ScaleInAnimationAdapter(it)
                rvSong.addItemDecoration(itemDecoration)
                it.submitList(songService?.getSongList())
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        adapter = null
        songService = null
        super.onDestroy()
    }
}