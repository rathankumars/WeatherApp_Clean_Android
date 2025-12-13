package com.rathan.weather.data.repository

import com.rathan.weather.data.GeoCodingAPI
import com.rathan.weather.data.mapper.toDomain
import com.rathan.weather.domain.model.GeoLocation
import com.rathan.weather.domain.repository.GeoRepository
import javax.inject.Inject

class GeoRepositoryImpl @Inject constructor(
    private val geoCodingAPI: GeoCodingAPI
) : GeoRepository {

    override suspend fun getGeoDetails(cityName: String, limit: Int, apiKey: String): Result<List<GeoLocation>> {
        return try {
            val response = geoCodingAPI.getGeoDetails(
                query = cityName,
                limit = limit,
                appId = apiKey
            )
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

