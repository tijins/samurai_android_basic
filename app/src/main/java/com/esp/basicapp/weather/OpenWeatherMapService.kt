package com.esp.basicapp.weather

import retrofit2.http.GET
import retrofit2.http.Query

// API の定義を行う
interface OpenWeatherMapService {
    @GET("/data/2.5/weather")
    suspend fun weather(
        @Query("appid") appid:String,
        @Query("q") city:String,
        @Query("lang") lang:String,
        @Query("units") units:String,
    ):OpenWeatherMapData
}
