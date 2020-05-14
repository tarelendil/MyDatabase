package com.stas.mydatabase.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stas.mydatabase.R
import com.stas.mydatabase.database.AppDatabase
import com.stas.mydatabase.database.DatabaseManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DatabaseManager.getInstance()
        Thread{
            DatabaseManager.getInstance().insertUser()
        }
//        SecureDatabase.initDb(this)
    }
}
