package com.esp.basicapp

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.android.synthetic.main.activity_main.btn_upload
import kotlinx.android.synthetic.main.activity_main.hello
import timber.log.Timber
import java.time.Duration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        // KTXを導入するとできる
        hello.text = "ハロー"

        //WorkManagerを設定
        btn_upload.setOnClickListener {
            registerTask()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun registerTask() {
        val uploadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInitialDelay(Duration.ZERO)
            .setConstraints(Constraints.Builder().setRequiresCharging(true).build())
            .build()
        WorkManager.getInstance(this).enqueue(uploadWorkRequest)
    }
}

class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        // Do the work here--in this case, upload the images.
        for (i in 0 until 5) {
            Thread.sleep(1000)
            Timber.d("count $i")
        }

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}
