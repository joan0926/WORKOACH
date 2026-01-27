package com.example.workoach

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OutgoingSetting : AppCompatActivity() {
    private lateinit var Text_outgoing: EditText
    private lateinit var Text_outgoingDate: EditText
    private lateinit var Btn_outgoing: Button
    private lateinit var Btn_Close_outgoing: ImageButton
    private lateinit var userid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outgoingsetting)

        userid = intent.getStringExtra("USER_ID")?:return

        initView()
        setListeners()
        setupDatePicker()


    }
    private fun initView(){
        Text_outgoing=findViewById<EditText>(R.id.TextoutgoingSalary)
        Text_outgoingDate=findViewById<EditText>(R.id.TextoutgoingDate)
        Btn_outgoing=findViewById<Button>(R.id.btnoutgoingSetting)
        Btn_Close_outgoing=findViewById<ImageButton>(R.id.btncloseoutgingSetting)
    }

    private fun setListeners(){
        //지출 등록 버튼
        Btn_outgoing.setOnClickListener {
            val outgoing = Text_outgoing.text.toString().trim()
            val outgoingDate = Text_outgoingDate.text.toString().trim()

            if(outgoing.isEmpty() || outgoingDate.isEmpty()){
                Toast.makeText(this, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //DB에 값 저장
            insertOutgoingMoney(userid, outgoing, outgoingDate)
            finish()
        }
        Btn_Close_outgoing.setOnClickListener {
            finish()
        }
    }

    private fun insertOutgoingMoney(userid: String, outgoing:String, outgoingDate: String){
        val money = outgoing.toIntOrNull() ?: return
        val state = 1

        val db= DBHelper(this).writableDatabase

        val sql = """
            INSERT INTO moneyTBL (userid, state, money, date)
            VALUES (? , ?, ?, ?)
        """.trimIndent()

        db.execSQL(sql, arrayOf(userid, state, money, outgoingDate))
        db.close()

    }

    private fun setupDatePicker(){
        Text_outgoingDate.setOnClickListener {
            val calendar = java.util.Calendar.getInstance()

            val year = calendar.get(java.util.Calendar.YEAR)
            val month = calendar.get(java.util.Calendar.MONTH)
            val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val date = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"

                    Text_outgoingDate.setText(date)
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
    }
}