package com.example.workoach

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar


class Money : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_money)

        val editTextDate = findViewById<EditText>(R.id.text_MoneyDate)
        val year = Calendar.getInstance().get(Calendar.YEAR)
        editTextDate.hint = "$year.00.00 ▼"
        //DB저장변수들
        val userId = intent.getStringExtra("USER_ID")
        val workname = findViewById<EditText>(R.id.text_WorkName)
        val workmoney = findViewById<EditText>(R.id.text_TakeMoney)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //버튼 누르면 홈화면으로 넘어가는 코드(넘어가는 거, DB에 저장) 만들어야 함


    }
}