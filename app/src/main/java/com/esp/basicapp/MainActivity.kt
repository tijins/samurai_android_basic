package com.esp.basicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricConstants
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private var canUseBiometrics = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        // KTXを導入するとできる
        hello.text = "ハロー"

        checkBiometric()
        initializeBiometrics()
        btn_login.setOnClickListener {
            if(canUseBiometrics){
                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Biometric login for my app")
                        .setSubtitle("Log in using your biometric credential")
                        .setNegativeButtonText("Use account password")
                        .build()

                biometricPrompt.authenticate(promptInfo)
            }else{
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_SETTINGS).apply {
                }
                startActivityForResult(enrollIntent, 0)
            }
        }
    }

    private fun checkBiometric(){
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Timber.d("App can authenticate using biometrics.")
                canUseBiometrics = true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Timber.e("No biometric features available on this device.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Timber.d( "Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Timber.d( "NONE ENROLLED")
            }
        }
    }

    private fun initializeBiometrics(){
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int,
                                                       errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(applicationContext,
                                "Authentication error: $errString", Toast.LENGTH_SHORT)
                                .show()
                    }

                    override fun onAuthenticationSucceeded(
                            result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(applicationContext,
                                "Authentication succeeded!", Toast.LENGTH_SHORT)
                                .show()
                        Timber.d(result.cryptoObject.)
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(applicationContext, "Authentication failed",
                                Toast.LENGTH_SHORT)
                                .show()
                    }
                })
    }
}
