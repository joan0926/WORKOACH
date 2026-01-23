package com.example.workoach

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class IncomeSetting: AppCompatActivity() {

    private lateinit var Text_income: EditText
    private lateinit var Text_incomeDate: EditText
    private lateinit var Btn_income: Button
    private lateinit var Btn_Close_income: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incomsetting)

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
            val intent = Intent().apply{
                putExtra("income",income)
                putExtra("incomeDate", incomeDate)
            }
            setResult(RESULT_OK,intent)
            //finish()
        }
    }

}