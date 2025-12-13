package com.rathan.weather.data

import com.rathan.weather.data.dto.GeoDetailsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodingAPI {
    //  http://api.openweathermap.org/geo/1.0/direct?q={city name},{state code},{country code}&limit={limit}&appid={API key}
    @GET("/geo/1.0/direct")
    suspend fun getGeoDetails(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("appid") appId: String
    ): GeoDetailsDto
}