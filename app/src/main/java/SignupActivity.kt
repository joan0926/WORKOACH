package com.example.workoach

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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

        passwordError.visibility = View.GONE

        btnProfile.setOnClickListener {
            val id = etId.text.toString().trim()
            val pw = etPassword.text.toString().trim()
            val pwCheck = etPasswordConfirm.text.toString().trim()
            val name = etName.text.toString().trim()
            val date = etDate.text.toString().trim()

            if (id.isEmpty() || pw.isEmpty() || pwCheck.isEmpty() || name.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "모든 항목을 다 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pw != pwCheck) {
                etPasswordConfirm.background =
                    ContextCompat.getDrawable(this, R.drawable.edittext_outline_error)
                passwordError.visibility = View.VISIBLE
                etPasswordConfirm.requestFocus()
                return@setOnClickListener
            }

            saveUser(id, pw, name, date)

            // 🔹 회원가입 완료 → 바로 월급 입력 화면으로 이동
            val intent = Intent(this, Money::class.java)
            intent.putExtra("USER_ID", id)
            startActivity(intent)
            finish()
        }

        etPasswordConfirm.addTextChangedListener {
            etPasswordConfirm.background =
                ContextCompat.getDrawable(this, R.drawable.edittext_outline)
            passwordError.visibility = View.GONE
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val year = Calendar.getInstance().get(Calendar.YEAR)
        etDate.hint = "$year.00.00 ▼"

        etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val y = calendar.get(Calendar.YEAR)
            val m = calendar.get(Calendar.MONTH)
            val d = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    etDate.setText("${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일")
                }, y, m, d
            ).show()
        }
    }

    private fun saveUser(id: String, pw: String, name: String, date: String) {
        val dbHelper = DBHelper(this)
        val db = dbHelper.writableDatabase
        val sql = """
            INSERT INTO userTBL(userid, userpw, username, startdate)
            VALUES(?,?,?,?)
        """.trimIndent()
        db.execSQL(sql, arrayOf(id, pw, name, date))
        db.close()
    }
}
