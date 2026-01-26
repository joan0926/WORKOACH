package com.example.workoach

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val etId = findViewById<EditText>(R.id.text_Id)
        val etPw = findViewById<EditText>(R.id.text_Password)
        val btnLogin = findViewById<Button>(R.id.button_login)

        btnLogin.setOnClickListener {

            // 입력값 받기
            val userID = etId.text.toString().trim()
            val userPW = etPw.text.toString().trim()

            // 빈칸 체크
            if (userID.isEmpty() || userPW.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 지금은 바로 통과 (다음 단계에서 DB검사)
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USER_ID", userID)
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
