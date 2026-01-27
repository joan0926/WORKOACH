package com.example.workoach

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class Money : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_money)

        val editTextDate = findViewById<EditText>(R.id.text_MoneyDate)
        val workname = findViewById<EditText>(R.id.text_WorkName)
        val workmoney = findViewById<EditText>(R.id.text_TakeMoney)
        val btnMoney = findViewById<Button>(R.id.button_Money)

        val userId = intent.getStringExtra("USER_ID")

        val year = Calendar.getInstance().get(Calendar.YEAR)
        editTextDate.hint = "$year.00.00 ▼"

        btnMoney.setOnClickListener {

            // 🔥 HomeActivity ❌ → BottomNavActivity ✅
            val intent = Intent(this, BottomNavActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)

            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
