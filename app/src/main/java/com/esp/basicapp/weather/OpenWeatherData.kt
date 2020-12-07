package com.esp.basicapp.weather

import com.google.gson.annotations.SerializedName

data class OwmWeather(
    var id: Int = 0,
    var main: String? = null,
    var description:String? = null
)

data class OwmMain(
    var temp:Float = 0F,
    @SerializedName("temp_min")
    var tempMin: Float = 0F,
    @SerializedName("temp_max")
    var tempMax: Float = 0F,
)

data class OpenWeatherMapData (
    var weather:List<OwmWeather>? = null,
    var main:OwmMain? = null,
    var name:String? = null,
)
