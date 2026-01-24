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

        //로그아웃 팝업
        Btn_logout.setOnClickListener {
            showCustomDialog(R.layout.check_logout)
        }
        //계정 삭제 팝업
        Btn_deluser.setOnClickListener {
            showCustomDialog(R.layout.check_delaccount)
        }
        //비밀번호 변경 팝업
        Btn_changepw.setOnClickListener {
            showCustomDialog(R.layout.activity_changepw)
        }
    }

    fun showCustomDialog(@LayoutRes layoutResId: Int){
        val dialog = Dialog(this)

        val view = layoutInflater.inflate(layoutResId, null)

        dialog.setContentView(view)
        dialog.show()
    }

}