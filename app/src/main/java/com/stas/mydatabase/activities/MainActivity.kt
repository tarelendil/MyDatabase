package com.stas.mydatabase.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.stas.mydatabase.R
import com.stas.mydatabase.database.AppDatabase
import com.stas.mydatabase.database.DatabaseManager
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DatabaseManager.getInstance()
        Thread {
            DatabaseManager.getInstance()
                .insertUsers(
                    arrayListOf(
                        UserMapped("Alfredo", "Pavon", true),
                        UserMapped("Vashe", "Huynia", false),
                        UserMapped("Grisha", "El Gringo", true)
                    )
                )
            DatabaseManager.getInstance().users.forEach { Log.i("asd", it.toString()) }
        }.start()
//        SecureDatabase.initDb(this)
    }
}
