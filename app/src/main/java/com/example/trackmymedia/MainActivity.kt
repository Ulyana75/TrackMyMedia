package com.example.trackmymedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trackmymedia.databinding.ActivityMainBinding
import com.example.trackmymedia.fragments.ChooseMediaFragment
import com.example.trackmymedia.utilits.APP_ACTIVITY
import com.example.trackmymedia.utilits.replaceFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        replaceFragment(ChooseMediaFragment())
    }

}