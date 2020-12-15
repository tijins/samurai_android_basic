package com.esp.basicapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricConstants
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.security.KeyStore
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.nio.charset.Charset
import java.util.Enumeration
import java.util.concurrent.Executor
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class MainActivity : AppCompatActivity() {

    companion object {
        private const val KEY_NAME = "bio_key"
    }

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private var canUseBiometrics = false
    private var encryptedData: ByteArray? = null
    private var secretKey: SecretKey? = null
    private var iv: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        // KTXを導入するとできる
        hello.text = "ハロー"

        initializeCrypto()
        checkBiometric()
        btn_login.setOnClickListener {
            if (canUseBiometrics) {
                val cipher = getCipher()
                val secretKey = getSecretKey()
                if (encryptedData == null) {
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
                    iv = cipher.iv
                } else {
                    val ivParameterSpec = IvParameterSpec(iv)
                    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
                }

                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric login for my app")
                    .setSubtitle("Log in using your biometric credential")
                    .setNegativeButtonText("Use account password")
                    .build()

                biometricPrompt.authenticate(
                    promptInfo,
                    BiometricPrompt.CryptoObject(cipher)
                )
            } else {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_SETTINGS).apply {
                }
                startActivityForResult(enrollIntent, 0)
            }
        }
    }

    private fun checkBiometric() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Timber.d("App can authenticate using biometrics.")
                canUseBiometrics = true
                initializeBiometrics()
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Timber.e("No biometric features available on this device.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Timber.d("Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Timber.d("NONE ENROLLED")
            }
        }
    }

    private fun initializeCrypto() {
        generateSecretKey(
            KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setUserAuthenticationRequired(true)
                // Invalidate the keys if the user has registered a new biometric
                // credential, such as a new fingerprint. Can call this method only
                // on Android 7.0 (API level 24) or higher. The variable
                // "invalidatedByBiometricEnrollment" is true by default.
                //.setInvalidatedByBiometricEnrollment(true)
                .build()
        )
    }

    private fun initializeBiometrics() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    ).show()

                    if (encryptedData == null) {
                        val inputData = "hello".toByteArray(Charset.forName("utf-8"))
                        val encryptedData = result.cryptoObject?.cipher?.doFinal(inputData)?.also {
                            encryptedData = it
                        }
                        Timber.d(encryptedData.contentToString())
                    } else {
                        val decryptedData = result.cryptoObject?.cipher?.doFinal(encryptedData)
                        Timber.d(decryptedData?.toString(Charset.forName("utf-8")))
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
    }

    private fun generateSecretKey(keyGenParameterSpec: KeyGenParameterSpec) {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore"
        )
        keyGenerator.init(keyGenParameterSpec)
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")

        // Before the keystore can be accessed, it must be loaded.
        keyStore.load(null)
        return (keyStore.getKey(KEY_NAME, null) as SecretKey)
    }

    private fun getCipher(): Cipher {
        return Cipher.getInstance(
            KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/"
                + KeyProperties.ENCRYPTION_PADDING_PKCS7
        )
    }
}
