package com.esp.basicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.esp.basicapp.data.User
import com.esp.basicapp.data.UserDatabase
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.load
import kotlinx.android.synthetic.main.activity_main.load_db
import kotlinx.android.synthetic.main.activity_main.save
import kotlinx.android.synthetic.main.activity_main.save_db
import kotlinx.android.synthetic.main.activity_main.txt_age
import kotlinx.android.synthetic.main.activity_main.txt_name
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    companion object {
        const val FILE_NAME = "sample.json"
        const val DB_FILE_NAME = "sample.db"
    }

    // coroutine
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    // Roomライブラリを使用する
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")
        // Roomライブラリを使用する
        db = Room.databaseBuilder(this, UserDatabase::class.java, DB_FILE_NAME).build()

        save.setOnClickListener {
            saveSample()
        }

        load.setOnClickListener {
            loadSample()
        }

        save_db.setOnClickListener {
            saveDatabase()
        }

        load_db.setOnClickListener {
            loadDatabase()
        }
    }

    private fun saveSample() {
        val sample = Sample()
            .apply {
                name = txt_name.text.toString()
                age = txt_age.text.toString().toInt()
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

    private fun loadSample() {
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

        if (sample != null) {
            txt_name.setText(sample.name)
            txt_age.setText(sample.age.toString())
        }
    }

    private fun loadSampleWithError() {
        try {
            val gson = GsonBuilder().create()
            // 存在しないファイルを開く
            val sample = openFileInput("NotFound.json")?.use {
                InputStreamReader(it, Charset.forName("UTF-8")).use { reader ->
                    gson.fromJson(reader, Sample::class.java)
                }
            }

            if (sample != null) {
                txt_name.setText(sample.name)
                txt_age.setText(sample.age.toString())
            }
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    private fun saveDatabase() {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                // 保存するデータを準備(uidは、仮の値。保存時に上書きされる)
                val user = User(0, txt_name.text.toString(), txt_age.text.toString().toInt())
                db.userDao().insert(user)
            }
        }
    }

    private fun loadDatabase() {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                val users = db.userDao().getAll()
                users.forEach {
                    Timber.d("user = ${it.uid}/ ${it.name}/ ${it.age}")
                }
            }
        }
    }
}
