package com.palo.pod.mycrypto

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.nio.charset.Charset
import kotlin.text.Charsets.ISO_8859_1
import kotlin.text.Charsets.UTF_8

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val file_name = "data.txt"
        var s = ""
        application.assets.open(file_name).apply {
            s = this.readBytes().toString(Charsets.UTF_8)
        }.close()

        val workingFile = File(filesDir.absolutePath + java.io.File.separator + "datas.txt")
        createDataSource("secrets", file_name, workingFile)
    }

    private fun createDataSource(password: String, filename: String, outFile: File) {
        val inputStream = applicationContext.assets.open(filename)
        val bytes = inputStream.readBytes()
        inputStream.close()

        val passwordArray = CharArray(password.length)
        password.toCharArray()
        val map = Encryption().encrypt(bytes, passwordArray)
        ObjectOutputStream(FileOutputStream(outFile)).use {
            it -> it.write("map".toByteArray())
        }

        applicationContext.openFileOutput("test.txt", Context.MODE_PRIVATE).use{
            it.write("tesiiit".toByteArray())
        }
    }
}