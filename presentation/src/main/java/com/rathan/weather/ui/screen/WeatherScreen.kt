package com.rathan.weather.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rathan.weather.ui.viewmodel.WeatherViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchSuggestions by viewModel.searchSuggestions.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                viewModel.searchCity(query)
            },
            label = { Text("Search City") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Search Suggestions
        if (searchSuggestions.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                LazyColumn {
                    items(searchSuggestions) { location ->
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = "${location.name}, ${location.country}",
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            supportingContent = {
                                location.state?.let { state ->
                                    Text(text = state)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.selectLocation(location)
                                    searchQuery = "${location.name}, ${location.country}"
                                }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Loading Indicator
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        // Error Message
        uiState.error?.let { error ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    TextButton(onClick = { viewModel.clearError() }) {
                        Text("Dismiss")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Weather Content
        uiState.weather?.let { weather ->
            WeatherContent(weather = weather)
        }
    }
}

@Composable
fun WeatherContent(weather: com.rathan.weather.domain.model.Weather) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Location Name
            Text(
                text = weather.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Country
            Text(
                text = weather.sys.country,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Temperature
            Text(
                text = "${weather.main.temp.roundToInt()}°C",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold
            )

            // Weather Description
            weather.weather.firstOrNull()?.let { weatherInfo ->
                Text(
                    text = weatherInfo.description.replaceFirstChar { it.uppercase() },
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Weather Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetailItem(
                    label = "Feels Like",
                    value = "${weather.main.feelsLike.roundToInt()}°C"
                )
                WeatherDetailItem(
                    label = "Humidity",
                    value = "${weather.main.humidity}%"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetailItem(
                    label = "Min Temp",
                    value = "${weather.main.tempMin.roundToInt()}°C"
                )
                WeatherDetailItem(
                    label = "Max Temp",
                    value = "${weather.main.tempMax.roundToInt()}°C"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetailItem(
                    label = "Pressure",
                    value = "${weather.main.pressure} hPa"
                )
                WeatherDetailItem(
                    label = "Wind Speed",
                    value = "${weather.wind.speed} m/s"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherDetailItem(
                    label = "Visibility",
                    value = "${weather.visibility / 1000} km"
                )
                WeatherDetailItem(
                    label = "Clouds",
                    value = "${weather.clouds.all}%"
                )
            }
        }
    }
}

@Composable
fun WeatherDetailItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

