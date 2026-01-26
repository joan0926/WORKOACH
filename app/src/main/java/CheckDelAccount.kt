package com.example.workoach

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CheckDelAccount : AppCompatActivity() {
    private lateinit var Btn_deluser: Button
    private lateinit var userid :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.check_delaccount)
        userid = intent.getStringExtra("USER_ID") ?:return

        initView()
        Btn_deluser.setOnClickListener {

            //DB에서 계정 데이터 삭제
            val dbHelper = DBHelper(this)
            val db = dbHelper.writableDatabase

            db.beginTransaction()
            try {
                db.delete("moneyTBL", "userid=?", arrayOf(userid))
                db.delete("jobTBL", "userid=?", arrayOf(userid))
                db.delete("userTBL", "userid=?", arrayOf(userid))
                db.setTransactionSuccessful()
            }finally {
                db.endTransaction()
                db.close()
            }

            //로그인 삭제
            getSharedPreferences("login", MODE_PRIVATE)
                .edit()
                .clear()
                .apply()
            //메인으로 넘어가기
            val intent=Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }
    fun initView(){
        Btn_deluser=findViewById<Button>(R.id.btn_truedelaccount)
    }
}