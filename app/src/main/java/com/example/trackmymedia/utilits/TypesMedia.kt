package com.example.trackmymedia.utilits

import java.io.Serializable

enum class TypesMedia(private val typeInRussian: String) : Serializable {
    BOOK("Книги"), FILM("Фильмы"),
    SERIES("Сериалы"), GAME("Игры");

    fun getStringOnRussian(): String {
        return this.typeInRussian
    }
}