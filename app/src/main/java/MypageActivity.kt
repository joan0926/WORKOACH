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

    private lateinit var Btn_editprofile : Button
    private lateinit var Btn_editmoney : Button
    private lateinit var Btn_accountmanagement : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        initView()
        Btn_editprofile.setOnClickListener {
            showCustomDialog(R.layout.activity_editprofile,R.id.btnclose_editprofile)
        }
        Btn_editmoney.setOnClickListener {
            showCustomDialog(R.layout.activity_editmoney,R.id.btncloseeditmoney)
        }
        Btn_accountmanagement.setOnClickListener {
            showCustomDialog(R.layout.activity_accountmanagement, R.id.btnclose_accountmanage)
        }

    }

    private fun initView(){
        val balance = findViewById<TextView>(R.id.balance)
        balance.text = "아직 안 함"
    }
    fun showCustomDialog(@LayoutRes layoutResId: Int, closeBtnId: Int){
        val dialog = Dialog(this)

        val view = layoutInflater.inflate(layoutResId, null)
        dialog.setContentView(view)

        val btnClose = view.findViewById<ImageButton>(closeBtnId)
        btnClose?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}