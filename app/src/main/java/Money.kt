package com.example.workoach

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
        val workname = findViewById<EditText>(R.id.text_WorkName)
        val workmoney = findViewById<EditText>(R.id.text_TakeMoney)
        val btnMoney = findViewById<Button>(R.id.button_Money)

        val userId = intent.getStringExtra("USER_ID") ?: return

        val year = Calendar.getInstance().get(Calendar.YEAR)
        editTextDate.hint = "$year.00.00 ▼"

        btnMoney.setOnClickListener {

            val jobNameText = workname.text.toString().trim()
            val jobSalaryText = workmoney.text.toString().trim()
            val jobDateText = editTextDate.text.toString().trim()

            val jobSalary = try {
                jobSalaryText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "월급은 숫자로 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveJobToDB(userId, jobNameText, jobSalary, jobDateText)


            // 월급 입력 후 HomeFragment 포함 BottomNavActivity 이동
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
    private fun saveJobToDB(userId: String, jobName: String, jobSalary: Int, jobDate: String) {
        val dbHelper = DBHelper(this)
        val db = dbHelper.writableDatabase

        db.beginTransaction()
        try {
            db.execSQL(
                """
                INSERT INTO jobTBL (userid, jobname, jobsalary, jobdate)
                VALUES (?, ?, ?, ?)
                """.trimIndent(),
                arrayOf(userId, jobName, jobSalary, jobDate)
            )
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            Toast.makeText(this, "DB 저장 오류: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            db.endTransaction()
            db.close()
        }
    }
}
