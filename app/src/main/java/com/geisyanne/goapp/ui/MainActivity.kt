package com.geisyanne.goapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geisyanne.goapp.databinding.ActivityMainBinding
import com.geisyanne.goapp.R

import com.geisyanne.goapp.ui.features.travelRequest.TravelRequestFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_fragment_main, TravelRequestFragment())
                .commit()
        }

    }

}