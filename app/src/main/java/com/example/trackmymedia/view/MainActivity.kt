package com.example.trackmymedia.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.trackmymedia.databinding.ActivityMainBinding
import com.example.trackmymedia.utilits.APP_ACTIVITY
import com.example.trackmymedia.utilits.replaceFragment
import com.example.trackmymedia.view.fragments.ChooseMediaFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        toolbar = binding.mainToolbar
        setSupportActionBar(toolbar)
        replaceFragment(ChooseMediaFragment(), false)
    }

}