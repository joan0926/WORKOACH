package com.example.workoach

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CheckDelAccount : AppCompatActivity() {
    private lateinit var Btn_deluser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.check_delaccount)

        initView()
        Btn_deluser.setOnClickListener {
            //DB에서 계정 데이터 삭제
            //메인으로 넘어가기
        }

    }
    fun initView(){
        Btn_deluser=findViewById<Button>(R.id.btn_truedelaccount)
    }
}