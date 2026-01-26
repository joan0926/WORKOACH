package com.example.workoach

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

data class MoneySummary(
    val totalIncome: Int,
    val totalSpend: Int
)

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

        //통액, 소비금액 변수에 집어넣기
        /*val summary = getMoneySummary(userID)
        val totalmoney = summary.totalIncome
        val usingmoney = summary.totalSpend*/


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

    //해인:잔액 계산 코드
    /*private fun getMoneySummary(userID: String): MoneySummary{
        val db = DBHelper(this).readableDatabase

        val incomeCursor = db.rawQuery(
            """
                SELECT IFNULL(SUM(money),0)
                FROM moneyTBL
                WHERE userid =? AND state = 0
            """.trimIndent(),
            arrayOf(userID)
        )
        val totalIncome = if (incomeCursor.moveToFirst()){
            incomeCursor.getInt(0)
            }else 0
            incomeCursor.close()

        val spendCursor = db.rawQuery(
            """
                SELECT IFNULL(SUM(money),0)
                FROM moneyTBL
                WHERE userid =? AND state IN (1,2)
            """.trimIndent(),
            arrayOf(userID)
        )
        val totalSpend = if(spendCursor.moveToFirst()){
            spendCursor.getInt(0)
            }else 0
            spendCursor.close()
            db.close()

        return MoneySummary(
            totalIncome = totalIncome,
            totalSpend = totalSpend
        )
    }*/

}
