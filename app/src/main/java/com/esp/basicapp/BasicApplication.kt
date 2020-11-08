package com.esp.basicapp

import android.app.Application
import timber.log.Timber

class BasicApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // Timberを有効にします
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}
