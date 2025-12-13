package com.rathan.weather.domain.usecase

import com.rathan.weather.domain.model.Weather
import com.rathan.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double, apiKey: String): Result<Weather> {
        return weatherRepository.getWeather(lat, lon, apiKey)
    }
}

