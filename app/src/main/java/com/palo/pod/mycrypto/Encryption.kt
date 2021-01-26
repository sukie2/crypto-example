package com.palo.pod.mycrypto

import android.util.Log
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

internal class Encryption {

    fun encrypt(
        dataToEncrypt: ByteArray,
        password: CharArray
    ): HashMap<String, ByteArray> {
        val map = HashMap<String, ByteArray>()

        try {
            // 1
            //TODO: STEP 1

            // 2
            //TODO: STEP 2

            // 3
            //TODO: STEP 3

            // 4
            //TODO: STEP 4

//            map["salt"] = salt
//            map["iv"] = iv
//            map["encrypted"] = encrypted

        } catch (e: Exception) {
            Log.e("Crypto App", "encryption exception", e)
        }
        return map
    }

    fun decrypt(map: HashMap<String, ByteArray>, password: CharArray): ByteArray? {
        var decrypted: ByteArray? = null
        try {

            //TODO: STEP 5

        } catch (e: Exception) {
            Log.e("Crypto App", "decryption exception", e)
        }

        return decrypted
    }
}

