package com.esp.basicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.load
import kotlinx.android.synthetic.main.activity_main.save
import kotlinx.android.synthetic.main.activity_main.txt_age
import kotlinx.android.synthetic.main.activity_main.txt_name
import timber.log.Timber
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    companion object{
        const val FILE_NAME = "sample.json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        save.setOnClickListener {
            saveSample()
        }

        load.setOnClickListener {
            loadSample()
        }

    }

    private fun saveSample(){
        val sample = Sample()
            .apply {
                name = txt_name.text.toString()
                age =  txt_age.text.toString().toInt()
            }

        val gson = GsonBuilder().create()
        val text = gson.toJson(sample)
        Timber.d(text)

        // アプリ用ディレクトリにファイルを開く
        openFileOutput(FILE_NAME, MODE_PRIVATE)?.use {
            // テキスト書き込みの設定（文字コードはUTF-8）
            OutputStreamWriter(it, Charset.forName("UTF-8")).use { writer ->
                writer.write(text)
            }
        }
    }

    private fun loadSample(){
        val gson = GsonBuilder().create()

        // アプリ用ディレクトリのファイルを開く
        val sample = openFileInput(FILE_NAME)?.use {
//            こっちの方が効率は良い
//            InputStreamReader(it, Charset.forName("UTF-8")).use { reader ->
//               gson.fromJson(reader, Sample::class.java)
//            }

//          サンプルなので分かりやすく
            InputStreamReader(it, Charset.forName("UTF-8")).use { reader ->
                val text = reader.readText()
                Timber.d(text)
                // Kotlin は、ブロック内の最後に参照した要素を、ブロックの戻り値として、取り出せる＝sampleに代入される
                gson.fromJson(text, Sample::class.java)
            }
        }

        if(sample != null){
            txt_name.setText(sample.name)
            txt_age.setText(sample.age.toString())
        }

    }

    private fun loadSampleWithError(){
        try {
            val gson = GsonBuilder().create()
            // 存在しないファイルを開く
            val sample = openFileInput("NotFound.json")?.use {
                InputStreamReader(it, Charset.forName("UTF-8")).use { reader ->
                    gson.fromJson(reader, Sample::class.java)
                }
            }

            if(sample != null){
                txt_name.setText(sample.name)
                txt_age.setText(sample.age.toString())
            }
        }catch (ex:Exception){
            Timber.e(ex)
        }
    }

}
