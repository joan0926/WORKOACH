package com.example.workoach

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class CoachActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach)

        val coachMoneyBar = findViewById<ProgressBar>(R.id.moneyBar)

        // =========================
        // 나중에 DB 연결 예정
        // =========================

        /*
        val userID = intent.getStringExtra("USER_ID") ?: ""

        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase

        val sql = """
            SELECT tagetmoney, collectedmoney
            FROM moneyTBL
            WHERE userid = ?
            LIMIT 1
        """.trimIndent()

        val cursor = db.rawQuery(sql, arrayOf(userID))

        var tagetmoney = 0
        var collectedmoney = 0

        if (cursor.moveToFirst()) {
            tagetmoney = cursor.getInt(cursor.getColumnIndexOrThrow("tagetmoney"))
            collectedmoney = cursor.getInt(cursor.getColumnIndexOrThrow("collectedmoney"))
        }

        cursor.close()
        db.close()
        */

        // =========================
        // 테스트용 직접 값
        // =========================

        val tagetmoney = 2_000_000
        val collectedmoney = 750_000

        // Progress 적용
        coachMoneyBar.max = tagetmoney
        coachMoneyBar.progress = collectedmoney
    }
}
