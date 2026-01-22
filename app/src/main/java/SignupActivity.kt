package com.example.workoach

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

class SignupActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        // Edge-to-Edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔹 생년월일 EditText
        val etJobDate = findViewById<EditText>(R.id.text_Date)


        val editTextDate = findViewById<EditText>(R.id.text_Date)
        val year = Calendar.getInstance().get(Calendar.YEAR)
        editTextDate.hint = "$year.00.00 ▼"




        etJobDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // 월은 0부터 시작하니까 +1
                    val date = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"

                    etJobDate.setText(date)
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }

    }
}
