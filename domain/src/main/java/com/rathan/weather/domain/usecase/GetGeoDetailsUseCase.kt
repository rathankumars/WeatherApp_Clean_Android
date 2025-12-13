package com.rathan.weather.domain.usecase

import com.rathan.weather.domain.model.GeoLocation
import com.rathan.weather.domain.repository.GeoRepository
import javax.inject.Inject

class GetGeoDetailsUseCase @Inject constructor(
    private val geoRepository: GeoRepository
) {
    suspend operator fun invoke(cityName: String, limit: Int = 5, apiKey: String): Result<List<GeoLocation>> {
        return geoRepository.getGeoDetails(cityName, limit, apiKey)
    }
}

