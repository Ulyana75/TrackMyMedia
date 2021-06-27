package com.example.trackmymedia.utilits

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trackmymedia.R

fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
    if(addToBackStack) {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.data_container, fragment).addToBackStack(null)
            .commit()
    }
    else {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.data_container, fragment)
            .commit()
    }

}

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun addButtonBack() {
    APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    APP_ACTIVITY.toolbar.setNavigationOnClickListener {
        APP_ACTIVITY.supportFragmentManager.popBackStack()
    }
}

fun removeButtonBack() {
    APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
}

fun getCurrentFragment(): Fragment {
    return APP_ACTIVITY.supportFragmentManager.fragments[APP_ACTIVITY.supportFragmentManager.fragments.size - 1]
}