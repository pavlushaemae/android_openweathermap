package com.itis.example.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itis.example.R
import com.itis.example.databinding.ActivityMainBinding
import com.itis.example.ui.fragment.ListFragment

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
