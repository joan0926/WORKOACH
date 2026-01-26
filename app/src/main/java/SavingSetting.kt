package com.example.workoach

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SavingSetting : AppCompatActivity() {
    private lateinit var Text_saving: EditText
    private lateinit var Text_savingDate: EditText
    private lateinit var Btn_saving: Button
    private lateinit var Btn_Close_saving: ImageButton
    private lateinit var userid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savingsetting)

        userid = intent.getStringExtra("USER_ID") ?:return

        initView()
        setListeners()
        Btn_Close_saving.setOnClickListener {
            finish()
        }

    }
    private fun initView(){
        Text_saving=findViewById<EditText>(R.id.TextsavingSalary)
        Text_savingDate=findViewById<EditText>(R.id.TextsavingDate)
        Btn_saving=findViewById<Button>(R.id.btnsavingSetting)
        Btn_Close_saving=findViewById<ImageButton>(R.id.btnclosesavingSetting)
    }

    private fun setListeners(){
        //저축 등록 버튼
        Btn_saving.setOnClickListener {
            val saving = Text_saving.text.toString().trim()
            val savingDate = Text_savingDate.text.toString().trim()

            if(saving.isEmpty() || savingDate.isEmpty()){
                Toast.makeText(this, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //moneyTBL에 추가
            insertSaving(userid, saving, savingDate)
            finish()
        }
    }

    private fun insertSaving(userid: String, saving:String, savingDate: String){
        val money = saving.toIntOrNull() ?: return
        val state = 2

        val db= DBHelper(this).writableDatabase

        val sql = """
            INSERT INTO moneyTBL (userid, state, money, date)
            VALUES (? , ?, ?, ?)
        """.trimIndent()

        db.execSQL(sql, arrayOf(userid, state, money, savingDate))
        db.close()

    }
}