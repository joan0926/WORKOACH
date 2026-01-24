package com.example.workoach

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ChangePwActivity : AppCompatActivity(){

    private lateinit var Text_originPW : EditText
    private lateinit var Text_newPW : EditText
    private lateinit var Text_confirmPW : EditText
    private lateinit var  Btn_changePW : Button
    private lateinit var Btn_Close_changePW : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepw)

        initView()
        setListenners()
    }

    private fun initView(){
        Text_originPW = findViewById<EditText>(R.id.Textoriginpw)
        Text_newPW = findViewById<EditText>(R.id.Textnewpw)
        Text_confirmPW = findViewById<EditText>(R.id.Textnewpw_confirm)
        Btn_changePW = findViewById<Button>(R.id.btn_changepw)
        Btn_Close_changePW = findViewById<ImageButton>(R.id.btnclose_changepw)

    }

    private fun setListenners(){
        Btn_changePW.setOnClickListener {
            val text_originPW = Text_originPW.text.toString().trim()
            val newPW = Text_newPW.text.toString().trim()

            showCutomDialog()
        }


    }
    fun showCutomDialog(){
        val dialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.check_change, null)

        dialog.setContentView(view)
        dialog.show()
    }
}