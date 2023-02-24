package com.itis.example.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.itis.example.R

class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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