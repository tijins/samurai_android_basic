package com.esp.basicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.btn_translate
import kotlinx.android.synthetic.main.activity_main.edit_english
import kotlinx.android.synthetic.main.activity_main.edit_japanese
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    companion object {
        private const val WEATHER_API_KEY = "c42bc6d679fc62dd60c6002b8bdfda59"
    }

    private val client = OkHttpClient()

    private val job = Job()
    private val scope = CoroutineScope(job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        btn_translate.setOnClickListener {
            scope.launch(Dispatchers.Main) {
                val word = edit_english.text.toString()
                val json = postTranslate(word)
                parseJson(json)
            }
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun postTranslate(text: String): String {
        return withContext(Dispatchers.IO) {
            val mediaType = "application/json".toMediaTypeOrNull()
            val body: RequestBody = """[ {  "Text": "$text" }]""".toRequestBody(mediaType)
            val request: Request = Request.Builder()
                .url("https://microsoft-translator-text.p.rapidapi.com/translate?from=en&profanityAction=NoAction&textType=plain&to=ja&api-version=3.0")
                .post(body)
                .addHeader("x-rapidapi-host", "microsoft-translator-text.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "6050c6ccf1mshd8d3b186e96d4cep17f566jsn54173426b271")
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .build()

            val response: Response = client.newCall(request).execute()
            response.body?.string()!!
        }
    }

    private fun parseJson(jsonString: String) {
        val json = JsonParser.parseString(jsonString)
        val para1 = json.asJsonArray[0].asJsonObject
        val translations = para1.get("translations").asJsonArray
        val item1 = translations[0].asJsonObject
        val text = item1.get("text").asString
        edit_japanese.setText(text)
    }
}
