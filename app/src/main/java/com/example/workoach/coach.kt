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


        val currentMoney = getSavingInfo(userId)
        val targetMoney = 2_000_000

            // ProgressBar 적용
            moneyBar.max = targetMoney //목표금액
            moneyBar.progress = currentMoney  //현재 모은 금액(저금만 일단 계산함)

    }

    private fun getSavingInfo(userID: String): Int{
        // DB에서 값 불러오기
        val db = DBHelper(this).readableDatabase
        //저축값 계산
        val currentCursor = db.rawQuery(
            """
                SELECT IFNULL(SUM(money),0)
                FROM moneyTBL
                WHERE userid = ? AND state = 2
            """.trimIndent(),
            arrayOf(userID)
        )
        val currentMoney = if(currentCursor.moveToFirst()){
            currentCursor.getInt(0)
        }else 0

        currentCursor.close()
        db.close()

        return currentMoney


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

