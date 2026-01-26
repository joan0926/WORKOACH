package com.example.workoach

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userID = intent.getStringExtra("USER_ID") ?: ""

        val moneyBar = findViewById<ProgressBar>(R.id.moneyBar)
        val tvPercent = findViewById<TextView>(R.id.tvPercent)
        val button = findViewById<Button>(R.id.button)

        // ===============================
        // 🔥 실험용 직접 값
        val totalmoney = 3000000
        val usingmoney = 1200000
        // ===============================

        /*
        // ===============================
        // DB 읽기 (나중에 사용)
        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase

        val sql = """
            SELECT totalmoney, usingmoney
            FROM moneyTBL
            WHERE userid = ?
            LIMIT 1
        """.trimIndent()

        val cursor = db.rawQuery(sql, arrayOf(userID))

        if (cursor.moveToFirst()) {

            totalmoney =
                cursor.getInt(cursor.getColumnIndexOrThrow("totalmoney"))

            usingmoney =
                cursor.getInt(cursor.getColumnIndexOrThrow("usingmoney"))
        }

        cursor.close()
        db.close()
        // ===============================
        */

        // ProgressBar
        moneyBar.max = totalmoney
        moneyBar.progress = usingmoney

        // 퍼센트 계산
        val percent =
            if (totalmoney > 0)
                (usingmoney * 100 / totalmoney)
            else 0

        tvPercent.text = "$percent%"

        // 버튼 → 다이얼로그
        button.setOnClickListener {
            showCustomDialog()
        }
    }

    private fun showCustomDialog() {
        val dialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.activity_editmoney, null)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun setupBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        // 현재 탭 표시
        bottomNav.selectedItemId = R.id.tab_home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_home -> true // 자기 자신

                R.id.tab_coach -> {
                    startActivity(Intent(this, coach::class.java))
                    false
                }

                R.id.tab_mypage -> {
                    startActivity(Intent(this, MypageActivity::class.java))
                    false
                }

                else -> false
            }
        }
    }
}
