package com.example.workoach

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

class MypageActivity : AppCompatActivity() {


    private lateinit var Btn_editprofile: Button
    private lateinit var Btn_editmoney: Button
    private lateinit var Btn_accountmanagement: Button
    private lateinit var userid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        val userid = intent.getStringExtra("USER_ID") ?: return

        initView()
        Btn_editprofile.setOnClickListener {
            showCustomDialog(R.layout.activity_editprofile, R.id.btnclose_editprofile)
        }
        Btn_editmoney.setOnClickListener {
            showCustomDialog(R.layout.activity_editmoney, R.id.btncloseeditmoney)
        }
        Btn_accountmanagement.setOnClickListener {
            showCustomDialog(R.layout.activity_accountmanagement, R.id.btnclose_accountmanage)
        }

    }

    private fun initView() {
        val balance = findViewById<TextView>(R.id.balance)
        val totalMoney = loadMoneyData(userid)
        balance.text = totalMoney.toString()
    }

    fun showCustomDialog(@LayoutRes layoutResId: Int, closeBtnId: Int) {
        val dialog = Dialog(this)

        val view = layoutInflater.inflate(layoutResId, null)
        dialog.setContentView(view)

        val btnClose = view.findViewById<ImageButton>(closeBtnId)
        btnClose?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

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