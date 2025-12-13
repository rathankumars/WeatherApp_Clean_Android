package com.rathan.weather.data

import com.rathan.weather.data.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    //https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    @GET("/data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String
    ): WeatherDto
}