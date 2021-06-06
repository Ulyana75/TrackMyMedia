package com.example.trackmymedia.utilits

import androidx.core.content.ContextCompat
import com.example.trackmymedia.R

enum class ColorsManager(private val color: Int) {

    ZERO(ContextCompat.getColor(APP_ACTIVITY, R.color.rateZero)),
    ONE(ContextCompat.getColor(APP_ACTIVITY, R.color.rateOne)),
    TWO(ContextCompat.getColor(APP_ACTIVITY, R.color.rateTwo)),
    THREE(ContextCompat.getColor(APP_ACTIVITY, R.color.rateThree)),
    FOUR(ContextCompat.getColor(APP_ACTIVITY, R.color.rateFour)),
    FIVE(ContextCompat.getColor(APP_ACTIVITY, R.color.rateFive)),
    SIX(ContextCompat.getColor(APP_ACTIVITY, R.color.rateSix)),
    SEVEN(ContextCompat.getColor(APP_ACTIVITY, R.color.rateSeven)),
    EIGHT(ContextCompat.getColor(APP_ACTIVITY, R.color.rateEight)),
    NINE(ContextCompat.getColor(APP_ACTIVITY, R.color.rateNine)),
    TEN(ContextCompat.getColor(APP_ACTIVITY, R.color.rateTen));

    fun getColor(): Int {
        return color
    }
}