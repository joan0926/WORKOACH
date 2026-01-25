package com.example.workoach

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProgressActivity : AppCompatActivity() {
    //private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        lateinit var button: Button

        //userID = intent.getStringExtra("USER_ID") ?:return
        button = findViewById<Button>(R.id.button)

        //변수에 잔액 데이터 넣기
        //val totalMoney = loadMoneyData(userID)
        button.setOnClickListener {
            showCustomDialog()
        }



    }

    fun showCustomDialog() { //다이얼로그 뜨기
        val dialog = Dialog(this)

        val view = layoutInflater.inflate(R.layout.activity_editmoney, null)

        dialog.setContentView(view)
        dialog.show()
        }
    //잔액 불러오기
    fun loadMoneyData(userid: String): Int {
        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase

        val sql = """
                SELECT totalmoney
                FROM moneyTBL
                WHERE userid = ?
                LIMIT 1
            """.trimIndent()

        val cursor = db.rawQuery(sql, arrayOf(userid))
        var totalmoney = 0
        if (cursor.moveToFirst()) {
            totalmoney = cursor.getInt(
                cursor.getColumnIndexOrThrow("totalmoney")
            )
        }
        cursor.close()
        db.close()

        return totalmoney
    }
    }

