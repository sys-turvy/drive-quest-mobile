package com.example.drivequest.data.remote.api.weather

data class WeatherResponse(
    val name: String,
    val weather: List<Weather>,
    val main: Main
)

data class Weather(
    val description: String,
    val icon: String
)

data class Main(
    val temp: Float
)