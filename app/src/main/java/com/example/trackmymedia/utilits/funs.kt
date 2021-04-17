package com.example.trackmymedia.utilits

import androidx.fragment.app.Fragment
import com.example.trackmymedia.R

fun replaceFragment(fragment: Fragment) {
    APP_ACTIVITY.supportFragmentManager.beginTransaction()
        .replace(R.id.data_container, fragment).commit()
}