package com.example.workoach

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ChangePwActivity : AppCompatActivity(){

    private lateinit var Text_originPW : EditText
    private lateinit var Text_originPWErr: TextView
    private lateinit var Text_newPW : EditText
    private lateinit var Text_confirmPW : EditText
    private lateinit var Text_newPWErr : TextView
    private lateinit var  Btn_changePW : Button
    private lateinit var Btn_Close_changePW : ImageButton
    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepw)

        userID = intent.getStringExtra("USER_ID") ?:return

        initView()
        setListenners()
    }

    private fun initView(){
        Text_originPW = findViewById<EditText>(R.id.Textoriginpw)
        Text_originPWErr=findViewById<TextView>(R.id.text_orginPWConfirm_err)
        Text_newPW = findViewById<EditText>(R.id.Textnewpw)
        Text_confirmPW = findViewById<EditText>(R.id.Textnewpw_confirm)
        Btn_changePW = findViewById<Button>(R.id.btn_changepw)
        Btn_Close_changePW = findViewById<ImageButton>(R.id.btnclose_changepw)
        Text_newPWErr=findViewById<TextView>(R.id.text_newPasswordConfirm_err)

        Btn_changePW.isEnabled=false
    }

    private fun setListenners(){

        val pwWatcher = object : TextWatcher{
            override fun afterTextChanged(s: Editable?){
                validateNewPassword()
            }

            override fun beforeTextChanged(s: CharSequence?, start:Int, count: Int, after:Int){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        }
        Text_newPW.addTextChangedListener(pwWatcher)
        Text_confirmPW.addTextChangedListener(pwWatcher)

        Btn_changePW.setOnClickListener {
            handleChangePW()
        }
        Btn_Close_changePW.setOnClickListener{
            finish()
        }
    }

    private fun validateNewPassword(): Boolean{
        val newPW = Text_newPW.text.toString()
        val confirmPW = Text_confirmPW.text.toString()

        return if (newPW.isNotEmpty() && confirmPW.isNotEmpty() && newPW!=confirmPW){
            showError(
                editText = Text_confirmPW,
                errorText = Text_newPWErr,
            )
            Btn_changePW.isEnabled= false
            false
        } else if(newPW.isNotEmpty()&&confirmPW.isNotEmpty()) {
            clearError(Text_confirmPW, Text_newPWErr)
            Btn_changePW.isEnabled = true
            true
        }else {
            Btn_changePW.isEnabled = false
            false
        }
    }

    private fun handleChangePW(){
        val originPW = Text_originPW.text.toString().trim()
        val newPW = Text_newPW.text.toString().trim()
        val confirmPW = Text_confirmPW.text.toString().trim()

        if (originPW.isEmpty()|| newPW.isEmpty() || confirmPW.isEmpty()){
            return
        }

        if(!checkOriginPW(originPW)){
            showError(
                editText=Text_originPW,
                errorText = Text_originPWErr,
            )
            return
        } else{
            clearError(Text_originPW, Text_originPWErr)
        }

        if(!validateNewPassword()) return

        showCustomDialog(newPW)

    }

    private fun checkOriginPW(inputPW:String):Boolean{
        val db= DBHelper(this).readableDatabase

        val cursor = db.rawQuery(
            "SELECT userpw FROM userTBL WHERE userid=?",
            arrayOf(userID)
        )
        val result = cursor.moveToFirst() && cursor.getString(0)==inputPW

        cursor.close()
        db.close()
        return result
    }

    private fun showCustomDialog(newPW: String){
        val dialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.check_change, null)
        dialog.setContentView(view)

        //변경 취소되면 다이얼로그 창 닫기
        val btnCancel = view.findViewById<ImageButton>(R.id.btn_cancelchange)
        val btnChangePW = view.findViewById<Button>(R.id.btn_truechange)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        //변경 아니요면 비밀번호 변경

        btnChangePW.setOnClickListener {
            changePW(userID, newPW)
            dialog.dismiss()
            finish()
        }

        dialog.show()
    }

    private fun changePW(userid: String, newPW: String){
        val dbHelper = DBHelper(this)
        val db = dbHelper.writableDatabase

        val sql="""
            UPDATE userTBL
            SET userpw = ?
            WHERE userid = ?
        """.trimIndent()

        db.execSQL(sql, arrayOf(newPW, userid))
        db.close()
    }

    private fun showError(editText: EditText, errorText: TextView){
        editText.background=
            ContextCompat.getDrawable(this, R.drawable.edittext_outline_error)
        errorText.visibility=View.VISIBLE
        editText.requestFocus()
    }

    private fun clearError(editText : EditText, errorText: TextView){
        editText.background=
            ContextCompat.getDrawable(this, R.drawable.edittext_outline)
        editText.error = null
        errorText.visibility = View.GONE
    }
}