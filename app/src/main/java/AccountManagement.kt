package com.example.workoach

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

class AccountManagement : AppCompatActivity() {

    private lateinit var Btn_logout : Button
    private lateinit var Btn_deluser : Button
    private lateinit var Btn_changepw : Button
    private lateinit var Btn_Close_management : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accountmanagement)

        initView()

        //로그아웃 팝업
        Btn_logout.setOnClickListener {
            //로그아웃 취소시 팝업창 닫힘
            showCustomDialog(R.layout.check_logout,R.id.btn_falselogout)
        }
        //계정 삭제 팝업
        Btn_deluser.setOnClickListener {
            showCustomDialog(R.layout.check_delaccount,R.id.btn_falsedelaccount)
        }
        //비밀번호 변경 팝업
        Btn_changepw.setOnClickListener {
            showCustomDialog(R.layout.activity_changepw, R.id.btnclose_changepw)
        }
    }

    fun initView(){
        Btn_logout=findViewById<Button>(R.id.btnlogout)
        Btn_deluser=findViewById<Button>(R.id.btndelaccount)
        Btn_changepw=findViewById<Button>(R.id.btnchange_password)
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