package com.example.trackmymedia.utilits

enum class TypesMedia(private val typeInRussian: String) {
    BOOK("Книги"), FILM("Фильмы"),
    SERIES("Сериалы"), GAME("Игры");

    fun getStringOnRussian(): String {
        return this.typeInRussian
    }
}