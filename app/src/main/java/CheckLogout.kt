package com.example.workoach

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CheckLogout: AppCompatActivity() {
    private lateinit var Btn_Yeslogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.check_logout)

        Btn_Yeslogout=findViewById<Button>(R.id.btn_truelogout)
        //메인화면으로 넘어가기
        //Btn_Yeslogout.setOnClickListener {
        //    val intent = Intent(this, MainActivity)
        //    startActivity(intent)
        //}
    }
}