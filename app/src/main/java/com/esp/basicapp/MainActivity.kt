package com.esp.basicapp

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.btn_crypt
import kotlinx.android.synthetic.main.activity_main.btn_decrypt
import kotlinx.android.synthetic.main.activity_main.btn_delete
import kotlinx.android.synthetic.main.activity_main.hello
import timber.log.Timber
import java.nio.charset.Charset
import java.security.KeyStore
import java.util.Base64
import java.util.concurrent.Executor
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class MainActivity : AppCompatActivity() {

    companion object {
        private const val KEY_NAME = "bio_key"
        private const val PREFERENCES_NAME = "pref"
    }

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private var canUseBiometrics = false
    private var iv: ByteArray? = null
    private var data: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        // KTXを導入するとできる
        hello.text = "ハロー"

        checkBiometric()
        btn_crypt.setOnClickListener {
            if (canUseBiometrics) {
                save()
            } else {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_SETTINGS).apply {
                }
                startActivityForResult(enrollIntent, 0)
            }
        }
        btn_decrypt.setOnClickListener {
            if (canUseBiometrics) {
                load()
            } else {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_SETTINGS).apply {
                }
                startActivityForResult(enrollIntent, 0)
            }
        }
        btn_delete.setOnClickListener {
            deleteValue()
        }
    }

    private fun save() {
        val cipher = getCipher()
        val secretKey = getSecretKey()

        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        iv = cipher.iv
        data = null

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        biometricPrompt.authenticate(
            promptInfo,
            BiometricPrompt.CryptoObject(cipher)
        )
    }

    private fun load() {
        val cipher = getCipher()
        val secretKey = getSecretKey()
        loadValue()//iv, dataを読み出す

        val ivParameterSpec = IvParameterSpec(iv)
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
        } catch (e: KeyPermanentlyInvalidatedException) {
            Toast.makeText(this, "秘密鍵が無効になりました", Toast.LENGTH_SHORT).show()
            return
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
    }

    private fun deleteValue() {
        getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit().apply {
            clear()
            apply()
        }
    }

    private fun saveValue(iv: ByteArray, data: ByteArray) {
        getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit().apply {
            val base64 = Base64.getEncoder()
            putString("iv", base64.encodeToString(iv))
            putString("data", base64.encodeToString(data))
            apply()
        }
    }

    private fun loadValue() {
        val pref = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        Base64.getDecoder().also {
            iv = it.decode(pref.getString("iv", ""))
            data = it.decode(pref.getString("data", ""))
            Timber.d("iv=${iv?.joinToString { b -> b.toString(16) }} data=${data?.joinToString { b -> b.toString(16) }}")
        }
    }

    private fun checkBiometric() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Timber.d("App can authenticate using biometrics.")
                canUseBiometrics = true
                initializeCrypto()
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
        try {
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
        } catch (e: KeyPermanentlyInvalidatedException) {
            Toast.makeText(this, "秘密鍵が無効になりました", Toast.LENGTH_SHORT).show()
        }
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

                    if (data == null) {
                        val inputData = "hello".toByteArray(Charset.forName("utf-8"))
                        data = result.cryptoObject?.cipher?.doFinal(inputData)
                        saveValue(iv!!, data!!)
                    } else {
                        val decryptedData = result.cryptoObject?.cipher?.doFinal(data)
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
