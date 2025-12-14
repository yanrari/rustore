package com.example.rustore

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Находим кнопку и настраиваем переход
        val goButton = findViewById<Button>(R.id.go)

        goButton.setOnClickListener {
            // Просто переходим к витрине
            val intent = Intent(this, CatalogActivity::class.java)
            startActivity(intent)
        }
    }
}
