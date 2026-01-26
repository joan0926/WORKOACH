package com.example.workoach

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity


class coach : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach)

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
}
