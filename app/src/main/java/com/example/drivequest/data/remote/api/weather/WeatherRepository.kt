package com.example.drivequest.data.remote.api.weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository(apiKey: String) {
    private val api: OpenWeatherApiService

    private val apiKeyInternal = apiKey

    init {
        api = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApiService::class.java)
    }

    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse {
        return api.getWeather(lat, lon, apiKeyInternal)
    }
}
