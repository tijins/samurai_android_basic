package com.esp.basicapp.power

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import timber.log.Timber

class PowerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d(intent.action)
    }
}
