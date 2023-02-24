package com.itis.example.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.itis.example.R
import com.itis.example.databinding.ActivityMainBinding
import com.itis.example.ui.fragment.ListFragment
import com.itis.example.utils.hideKeyboard
import com.itis.example.utils.showSnackbar
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListFragment(), "ToListFragment")
            .commit()
    }
}