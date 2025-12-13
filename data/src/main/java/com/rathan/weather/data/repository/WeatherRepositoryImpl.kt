package com.rathan.weather.data.repository

import com.rathan.weather.data.WeatherAPI
import com.rathan.weather.data.mapper.toDomain
import com.rathan.weather.domain.model.Weather
import com.rathan.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherAPI: WeatherAPI
) : WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double, apiKey: String): Result<Weather> {
        return try {
            val response = weatherAPI.getWeather(
                lat = lat.toString(),
                lon = lon.toString(),
                appId = apiKey
            )
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

