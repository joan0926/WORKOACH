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

        passwordError.visibility = TextView.GONE
        val year = Calendar.getInstance().get(Calendar.YEAR)
        etDate.hint = "$year.00.00 ▼"

        // DatePicker
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

        // 재입력 시 에러 제거
        etPasswordConfirm.addTextChangedListener {
            etPasswordConfirm.background = ContextCompat.getDrawable(this, R.drawable.edittext_outline)
            passwordError.visibility = TextView.GONE
        }

        btnProfile.setOnClickListener {
            val id = etId.text.toString().trim()
            val pw = etPassword.text.toString().trim()
            val pwCheck = etPasswordConfirm.text.toString().trim()
            val name = etName.text.toString().trim()
            val date = etDate.text.toString().trim()

            if (id.isEmpty() || pw.isEmpty() || pwCheck.isEmpty() || name.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pw != pwCheck) {
                etPasswordConfirm.background = ContextCompat.getDrawable(this, R.drawable.edittext_outline_error)
                passwordError.visibility = TextView.VISIBLE
                etPasswordConfirm.requestFocus()
                return@setOnClickListener
            }

            saveUser(id, pw, name, date)

            // 회원가입 완료 → 월급 입력(MoneyActivity) 이동
            val intent = Intent(this, Money::class.java)
            intent.putExtra("USER_ID", id)
            startActivity(intent)
            finish()
        }

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
