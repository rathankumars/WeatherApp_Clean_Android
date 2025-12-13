package com.rathan.weather.data.mapper

import com.rathan.weather.data.dto.GeoDetailsDtoItem
import com.rathan.weather.domain.model.GeoLocation

fun GeoDetailsDtoItem.toDomain(): GeoLocation {
    return GeoLocation(
        country = country,
        lat = lat,
        lon = lon,
        name = name,
        state = state
    )
}

