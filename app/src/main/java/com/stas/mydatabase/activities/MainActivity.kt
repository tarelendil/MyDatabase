package com.stas.mydatabase.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stas.mydatabase.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        SecureDatabase.initDb(this)
    }
}
