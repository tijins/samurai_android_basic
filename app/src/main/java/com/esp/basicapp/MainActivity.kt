package com.esp.basicapp

import android.Manifest
import android.content.ContentResolver
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.hello
import timber.log.Timber
import java.security.spec.ECField

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        // KTXを導入するとできる
        hello.text = "ハロー"
        listAudio()
    }

    private fun listAudio(){
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            Timber.e("No Grants")
            return
        }

        contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,   // The content URI of the words table
            null,                        // The columns to return for each row
            null,                   // Selection criteria
            null,      // Selection criteria
            null                          // The sort order for the returned rows
        ).use { cursor->
            if(cursor == null){
                return
            }
            while (cursor.moveToNext()){
                for (col in cursor.columnNames){
                    try {
                        Timber.d("${col}:${cursor.getString(cursor.getColumnIndex(col))}")
                    }catch (ex:Exception){
                    }
                }
                //最初の1件だけで停止
                break
            }
        }
    }
}
