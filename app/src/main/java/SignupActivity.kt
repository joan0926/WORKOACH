package com.example.workoach

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import java.util.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        // 🔹 View 연결
        val btnProfile = findViewById<Button>(R.id.button_Profile)
        val password = findViewById<EditText>(R.id.text_Password)
        val passwordConfirm = findViewById<EditText>(R.id.text_PasswordConfirm)
        val passwordError = findViewById<TextView>(R.id.text_PasswordConfirm_err)
        val etJobDate = findViewById<EditText>(R.id.text_Date)

        // 🔹 기본 상태 (에러 숨김)
        passwordError.visibility = View.GONE

        // 🔹 버튼 클릭
        btnProfile.setOnClickListener {

            val pw = password.text.toString()
            val pwCheck = passwordConfirm.text.toString()

            if (pw != pwCheck) {
                // ❌ 비밀번호 불일치
                passwordConfirm.background =
                    ContextCompat.getDrawable(this, R.drawable.edittext_outline_error)

                passwordError.visibility = View.VISIBLE
                passwordConfirm.requestFocus()

                return@setOnClickListener
            }

            // ✅ 비밀번호 일치 → 다음 화면
            val intent = Intent(this, ProgressActivity::class.java)
            startActivity(intent)
        }

        // 🔹 다시 입력하면 에러 해제
        passwordConfirm.addTextChangedListener {
            passwordConfirm.background =
                ContextCompat.getDrawable(this, R.drawable.edittext_outline)
            passwordError.visibility = View.GONE
        }

        // 🔹 Edge-to-Edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔹 취직일 힌트
        val year = Calendar.getInstance().get(Calendar.YEAR)
        etJobDate.hint = "$year.00.00 ▼"

        // 🔹 DatePicker
        etJobDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val y = calendar.get(Calendar.YEAR)
            val m = calendar.get(Calendar.MONTH)
            val d = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val date = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"
                    etJobDate.setText(date)
                },
                y, m, d
            ).show()
        }
    }
}
