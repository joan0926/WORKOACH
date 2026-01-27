package com.example.workoach

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

class AccountManagement : AppCompatActivity() {

    companion object{
        const val RESULT_LOGOUT = 1001
        const val RESULT_DELETE = 1002
        const val RESULT_CHANGE_PW = 1003
    }

    private lateinit var Btn_logout : Button
    private lateinit var Btn_deluser : Button
    private lateinit var Btn_changepw : Button
    private lateinit var Btn_Close_management : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accountmanagement)

        val dialog = Dialog(this)

        initView()

        //로그아웃 팝업
        Btn_logout.setOnClickListener {
            setResult(RESULT_LOGOUT)
            finish()
        }
        //계정 삭제 팝업
        Btn_deluser.setOnClickListener {
            setResult(RESULT_DELETE)
            finish()
        }
        //비밀번호 변경 팝업
        Btn_changepw.setOnClickListener {
            setResult(RESULT_CHANGE_PW)
            finish()
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