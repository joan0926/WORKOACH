package com.example.workoach

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

class Money : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_money)

        val editTextDate = findViewById<EditText>(R.id.text_MoneyDate)
        val workName = findViewById<EditText>(R.id.text_WorkName)
        val workMoney = findViewById<EditText>(R.id.text_TakeMoney)
        val btnMoney = findViewById<Button>(R.id.button_Money)

        val userId = intent.getStringExtra("USER_ID")

        // 힌트 설정
        val year = Calendar.getInstance().get(Calendar.YEAR)
        editTextDate.hint = "$year.00.00 ▼"

        // DatePicker
        editTextDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, y, m, d ->
                    editTextDate.setText("${y}년 ${m + 1}월 ${d}일")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // 버튼 클릭 → BottomNavActivity 이동
        btnMoney.setOnClickListener {
            val intent = Intent(this, BottomNavActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
            finish()
        }

        // Edge-to-Edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
