package com.palo.pod.mycrypto

import android.content.Context
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import java.io.*

class MainActivity : AppCompatActivity() {
    private lateinit var secretTextView: EditText
    private lateinit var dataTextView: EditText
    private lateinit var workingFile: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workingFile = File(filesDir.absolutePath + File.separator + "private_data.txt")
        secretTextView = findViewById(R.id.inputTextSecret)
        dataTextView = findViewById(R.id.inputTextData)

        findViewById<MaterialButton>(R.id.btnEncrypt).setOnClickListener { view ->
            if (secretTextView.text.isNotBlank() && dataTextView.text.isNotBlank()) {
                encryptFile(secretTextView.text.toString(), dataTextView.text.toString())
                showSnackBar("Data was written to your file", view)
                secretTextView.setText("")
                dataTextView.setText("")
            } else {
                showSnackBar("Data or secret missing", view)
            }
        }

        findViewById<MaterialButton>(R.id.btnDecrypt).setOnClickListener { view ->
            decryptFile(secretTextView.text.toString(), view)
        }
    }

    private fun showSnackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    private fun encryptFile(key: String, data: String) {
        val keyArray = CharArray(key.length)
        secretTextView.text.getChars(0, secretTextView.length(), keyArray, 0)

        val map = Encryption().encrypt(
            data.toByteArray(Charsets.UTF_8),
            secretTextView.text.toString().toCharArray()
        )

        // Write to file
        ObjectOutputStream(FileOutputStream(workingFile)).use {
            it.writeObject(map)
        }
    }

    private fun decryptFile(key: String, view: View) {
        var decrypted: ByteArray? = null
        ObjectInputStream(FileInputStream(workingFile)).use { it ->
            val data = it.readObject()
            when (data) {
                is Map<*, *> -> {
                    if (data.containsKey("iv") && data.containsKey("salt") && data.containsKey("encrypted")) {
                        val iv = data["iv"]
                        val salt = data["salt"]
                        val encrypted = data["encrypted"]
                        if (iv is ByteArray && salt is ByteArray && encrypted is ByteArray) {
                            decrypted = Encryption().decrypt(
                                hashMapOf("iv" to iv, "salt" to salt, "encrypted" to encrypted),
                                key.toCharArray()
                            )
                            if (decrypted != null) {
                                val decryptedData = String(decrypted!!, Charsets.UTF_8)
                                showSnackBar("Your secret is: $decryptedData", view)
                            } else {
                                showSnackBar("Incorrect key", view)
                            }
                        }
                    }
                }
            }
        }
    }
}