package com.rathan.weather.data.mapper

import com.rathan.weather.data.dto.WeatherDto
import com.rathan.weather.domain.model.Weather
import com.rathan.weather.domain.model.Clouds
import com.rathan.weather.domain.model.Coord
import com.rathan.weather.domain.model.Main
import com.rathan.weather.domain.model.Rain
import com.rathan.weather.domain.model.Sys
import com.rathan.weather.domain.model.WeatherX
import com.rathan.weather.domain.model.Wind

fun WeatherDto.toDomain(): Weather {
    return Weather(
        base = base,
        clouds = clouds.toDomain(),
        cod = cod,
        coord = coord.toDomain(),
        dt = dt,
        id = id,
        main = main.toDomain(),
        name = name,
        rain = rain?.toDomain(),
        sys = sys.toDomain(),
        timezone = timezone,
        visibility = visibility,
        weather = weather.map { it.toDomain() },
        wind = wind.toDomain()
    )
}

fun com.rathan.weather.data.dto.Clouds.toDomain(): Clouds {
    return Clouds(all = all)
}

fun com.rathan.weather.data.dto.Coord.toDomain(): Coord {
    return Coord(lat = lat, lon = lon)
}

fun com.rathan.weather.data.dto.Main.toDomain(): Main {
    return Main(
        feelsLike = feels_like,
        grndLevel = grnd_level,
        humidity = humidity,
        pressure = pressure,
        seaLevel = sea_level,
        temp = temp,
        tempMax = temp_max,
        tempMin = temp_min
    )
}

fun com.rathan.weather.data.dto.Rain?.toDomain(): Rain? {
    return this?.let { Rain(oneH = `1h`) }
}

fun com.rathan.weather.data.dto.Sys.toDomain(): Sys {
    return Sys(
        country = country,
        id = id,
        sunrise = sunrise,
        sunset = sunset,
        type = type
    )
}

fun com.rathan.weather.data.dto.WeatherX.toDomain(): WeatherX {
    return WeatherX(
        description = description,
        icon = icon,
        id = id,
        main = main
    )
}

fun com.rathan.weather.data.dto.Wind.toDomain(): Wind {
    return Wind(
        deg = deg,
        gust = gust,
        speed = speed
    )
}

