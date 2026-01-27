package com.example.workoach

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InconmeSetting: AppCompatActivity() {

    private lateinit var Text_income: EditText
    private lateinit var Text_incomeDate: EditText
    private lateinit var Btn_income: Button
    private lateinit var Btn_Close_income: ImageButton
    private lateinit var userid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incomsetting)
        userid = intent.getStringExtra("USER_ID") ?:return

        initView()
        setListeners()
        Btn_Close_income.setOnClickListener {
            finish()
        }

    }
    private fun initView(){
        Text_income=findViewById<EditText>(R.id.TextincomeSalary)
        Text_incomeDate=findViewById<EditText>(R.id.TextincomeDate)
        Btn_income=findViewById<Button>(R.id.btnincomeSetting)
        Btn_Close_income=findViewById<ImageButton>(R.id.btncloseincomeSetting)
    }

    private fun setListeners(){
        //수입 등록 버튼
        Btn_income.setOnClickListener {
            val income = Text_income.text.toString().trim()
            val incomeDate = Text_incomeDate.text.toString().trim()

            if(income.isEmpty() || incomeDate.isEmpty()){
                Toast.makeText(this, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //결과 전달
            insertIncome(userid, income, incomeDate)
            finish()
        }
    }
    private fun insertIncome(userid: String, income:String, incomeDate: String){
        val money = income.toIntOrNull() ?: return
        val state = 0

        val db= DBHelper(this).writableDatabase

        val sql = """
            INSERT INTO moneyTBL (userid, state, money, date)
            VALUES (? , ?, ?, ?)
        """.trimIndent()

        db.execSQL(sql, arrayOf(userid, state, money, incomeDate))
        db.close()

    }

}