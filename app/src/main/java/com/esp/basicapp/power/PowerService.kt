package com.esp.basicapp.power

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

class PowerService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private var receiver:PowerReceiver? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        if(receiver == null){
            registerPowerReceiver()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        if(receiver != null){
            unregisterReceiver(receiver)
            receiver = null
        }

    }

    private fun registerPowerReceiver(){
        receiver = PowerReceiver()
        registerReceiver(receiver, IntentFilter(Intent.ACTION_POWER_CONNECTED))
    }
}
