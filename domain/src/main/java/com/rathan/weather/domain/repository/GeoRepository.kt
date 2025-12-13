package com.rathan.weather.domain.repository

import com.rathan.weather.domain.model.GeoLocation

interface GeoRepository {
    suspend fun getGeoDetails(cityName: String, limit: Int, apiKey: String): Result<List<GeoLocation>>
}