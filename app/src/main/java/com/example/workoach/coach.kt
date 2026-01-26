package com.example.workoach

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class coach : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach)

        // ⭐ 하단 네비게이션 연결
        setupBottomNav()

        val moneyBar = findViewById<ProgressBar>(R.id.moneyBar)

        val userId = intent.getStringExtra("USER_ID") ?: return

        // DB에서 값 불러오기
        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase

        val sql = """
            SELECT totalmoney, usingmoney
            FROM moneyTBL
            WHERE userid = ?
            LIMIT 1
        """.trimIndent()

        val cursor = db.rawQuery(sql, arrayOf(userId))

        if (cursor.moveToFirst()) {

            val totalmoney =
                cursor.getInt(cursor.getColumnIndexOrThrow("totalmoney"))

            val usingmoney =
                cursor.getInt(cursor.getColumnIndexOrThrow("usingmoney"))

            // ProgressBar 적용
            moneyBar.max = totalmoney
            moneyBar.progress = usingmoney
        }

        cursor.close()
        db.close()
    }

    // ===============================
    // 🔻 하단 네비게이션 함수
    // ===============================
    private fun setupBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        // 현재 탭: 코치
        bottomNav.selectedItemId = R.id.tab_coach

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.tab_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    false
                }

                R.id.tab_coach -> true // 자기 자신

                R.id.tab_mypage -> {
                    startActivity(Intent(this, MypageActivity::class.java))
                    false
                }

                else -> false
            }
        }
    }
}

