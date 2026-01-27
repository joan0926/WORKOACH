package com.example.workoach

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
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

        val etId = findViewById<EditText>(R.id.text_Id)
        val etPassword = findViewById<EditText>(R.id.text_Password)
        val etPasswordConfirm = findViewById<EditText>(R.id.text_PasswordConfirm)
        val etName = findViewById<EditText>(R.id.text_Name)
        val etDate = findViewById<EditText>(R.id.text_Date)
        val btnProfile = findViewById<Button>(R.id.button_Profile)
        val passwordError = findViewById<TextView>(R.id.text_PasswordConfirm_err)

        // 초기 설정
        passwordError.visibility = TextView.GONE
        val year = Calendar.getInstance().get(Calendar.YEAR)
        etDate.hint = "$year.00.00 ▼"

        // 1. 날짜 선택 (DatePicker)
        etDate.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, y, m, d ->
                    etDate.setText("${y}년 ${m + 1}월 ${d}일")
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // 2. 비밀번호 재입력 시 에러 메시지 숨기기
        etPasswordConfirm.addTextChangedListener {
            etPasswordConfirm.background = ContextCompat.getDrawable(this, R.drawable.edittext_outline)
            passwordError.visibility = TextView.GONE
        }

        // 3. 완료 버튼 클릭 (이 안에 모든 로직이 들어있어야 합니다)
        btnProfile.setOnClickListener {
            val id = etId.text.toString().trim()
            val pw = etPassword.text.toString().trim()
            val pwCheck = etPasswordConfirm.text.toString().trim()
            val name = etName.text.toString().trim()
            val date = etDate.text.toString().trim()

            // [검증 1] 모든 빈칸 확인
            if (id.isEmpty() || pw.isEmpty() || pwCheck.isEmpty() || name.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 여기서 멈춤
            }

            // [검증 2] 비밀번호 일치 확인
            if (pw != pwCheck) {
                etPasswordConfirm.background = ContextCompat.getDrawable(this, R.drawable.edittext_outline_error)
                passwordError.visibility = TextView.VISIBLE
                etPasswordConfirm.requestFocus()
                return@setOnClickListener // 여기서 멈춤
            }

            // [진행 1] DB 저장
            saveUser(id, pw, name, date)

            // [진행 2] 모든게 완벽할 때만 다음 화면(Money)으로 이동
            val intent = Intent(this, Money::class.java)
            intent.putExtra("USER_ID", id)
            startActivity(intent)
            finish() // 회원가입 화면 종료 (뒤로가기 방지)
        }

        // 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun saveUser(id: String, pw: String, name: String, date: String) {
        val dbHelper = DBHelper(this)
        val db = dbHelper.writableDatabase
        db.execSQL(
            "INSERT INTO userTBL(userid, userpw, username, startdate) VALUES(?,?,?,?)",
            arrayOf(id, pw, name, date)
        )
        db.close()
    }
}