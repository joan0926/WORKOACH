package com.example.workoach

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class testttttttttttt : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testttttttttttt)

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
}
