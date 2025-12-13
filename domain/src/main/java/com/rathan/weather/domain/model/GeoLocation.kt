package com.rathan.weather.domain.model

data class GeoLocation(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String?
)

