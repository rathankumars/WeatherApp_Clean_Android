package com.rathan.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rathan.weather.domain.model.GeoLocation
import com.rathan.weather.domain.model.Weather
import com.rathan.weather.domain.usecase.GetGeoDetailsUseCase
import com.rathan.weather.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getGeoDetailsUseCase: GetGeoDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _searchSuggestions = MutableStateFlow<List<GeoLocation>>(emptyList())
    val searchSuggestions: StateFlow<List<GeoLocation>> = _searchSuggestions.asStateFlow()

    private val apiKey = com.rathan.weather.BuildConfig.WEATHER_API_KEY

    fun searchCity(query: String) {
        if (query.isBlank()) {
            _searchSuggestions.value = emptyList()
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            getGeoDetailsUseCase(query, limit = 5, apiKey).fold(
                onSuccess = { locations ->
                    _searchSuggestions.value = locations
                    _uiState.value = _uiState.value.copy(isLoading = false)
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to search city"
                    )
                    _searchSuggestions.value = emptyList()
                }
            )
        }
    }

    fun selectLocation(location: GeoLocation) {
        _uiState.value = _uiState.value.copy(selectedLocation = location)
        _searchSuggestions.value = emptyList()
        loadWeather(location.lat, location.lon)
    }

    fun loadWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            getWeatherUseCase(lat, lon, apiKey).fold(
                onSuccess = { weather ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        weather = weather,
                        error = null
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to load weather"
                    )
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val selectedLocation: GeoLocation? = null,
    val error: String? = null
)

