package com.esp.basicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esp.basicapp.weather.OpenWeatherMapData
import com.esp.basicapp.weather.OpenWeatherMapService
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.get_weather
import kotlinx.android.synthetic.main.activity_main.txtTemp
import kotlinx.android.synthetic.main.activity_main.txtWeather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    companion object{
        private const val WEATHER_API_KEY = "c42bc6d679fc62dd60c6002b8bdfda59"
    }

    private val client = OkHttpClient()

    private val job = Job()
    private val scope = CoroutineScope(job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        get_weather.setOnClickListener {
            scope.launch(Dispatchers.Main) {
                val weatherData = loadWeatherWithRetrofit()
                txtWeather.text = weatherData.weather?.get(0)?.main
                txtTemp.text = getString(R.string.temp_c, weatherData.main?.temp)
            }
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun loadWeather():String{
        val city = "Tokyo"
        val request = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?q=${city}&lang=ja&units=metric&appid=${WEATHER_API_KEY}")
            .build()

        // withContext内は非同期で実行される
        val json = withContext(Dispatchers.IO){
            client.newCall(request).execute().body?.string()
        }
        return json!!
    }

    private fun parseJson(jsonString:String){
        val json = JsonParser.parseString(jsonString).asJsonObject
        val weathers = json.getAsJsonArray("weather")
        val weather0 = weathers[0].asJsonObject

        val weatherMain = weather0.get("main").asString
        txtWeather.text = weatherMain

        val main = json.get("main").asJsonObject
        val temp = main.get("temp").asFloat
        txtTemp.text = getString(R.string.temp_c, temp)
    }

    private suspend fun loadWeatherWithRetrofit():OpenWeatherMapData{
        return withContext(Dispatchers.IO){
            // Retrofitライブラリを作ります
            val service = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/")
                .build().create(OpenWeatherMapService::class.java)

            val data = service.weather(
                WEATHER_API_KEY,
                "tokyo",
                "ja",
                "metric"
            )
            data
        }
    }
}
