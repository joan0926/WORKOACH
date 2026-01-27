package com.example.workoach

import android.app.DatePickerDialog
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
        setupDatePicker()


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
        Btn_Close_saving.setOnClickListener {
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

    private fun setupDatePicker(){
        Text_savingDate.setOnClickListener {
            val calendar = java.util.Calendar.getInstance()

            val year = calendar.get(java.util.Calendar.YEAR)
            val month = calendar.get(java.util.Calendar.MONTH)
            val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val date = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"

                    Text_savingDate.setText(date)
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
    }
}