package com.rathan.weather.domain.repository

import com.rathan.weather.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double, apiKey: String): Result<Weather>
}