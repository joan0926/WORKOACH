package com.example.workoach

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditProfile : AppCompatActivity() {

    private lateinit var Text_username: EditText
    private lateinit var Text_workDate: EditText
    private lateinit var Btn_editprofile: Button
    private lateinit var Btn_Close_editprofile: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        initView()
        setListeners()
        Btn_Close_editprofile.setOnClickListener {
            finish()
        }

    }

    private fun initView(){
        Text_username=findViewById<EditText>(R.id.Texteditprofile_Name)
        Text_workDate=findViewById<EditText>(R.id.TextemployDate)
        Btn_editprofile=findViewById<Button>(R.id.btneditprofile)
        Btn_Close_editprofile=findViewById<ImageButton>(R.id.btnclose_editprofile)
    }
    private fun setListeners(){
        Btn_editprofile.setOnClickListener {
            val username = Text_username.text.toString().trim()
            val workDate = Text_workDate.text.toString().trim()

            if(username.isEmpty() || workDate.isEmpty()){
                Toast.makeText(this, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent().apply{
                putExtra("username",username)
                putExtra("workDate", workDate)
            }
            setResult(RESULT_OK,intent)
            finish()
        }
    }

}