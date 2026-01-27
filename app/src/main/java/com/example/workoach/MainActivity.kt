package com.example.workoach

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 버튼 연결
        val btnSignupEnt = findViewById<Button>(R.id.button_Signup_Enter)
        val btnLoginEnt = findViewById<Button>(R.id.button_login_Enter)

        // 회원가입 버튼 클릭 → SignupActivity 이동
        btnSignupEnt.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        // 로그인 버튼 클릭 → Login 이동
        btnLoginEnt.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        // Edge-to-Edge padding 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.join)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}