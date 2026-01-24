package com.example.workoach

import android.app.DatePickerDialog
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

        //날짜
        Text_workDate.setOnClickListener {
            val calendar = java.util.Calendar.getInstance()

            val year = calendar.get(java.util.Calendar.YEAR)
            val month = calendar.get(java.util.Calendar.MONTH)
            val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // 월은 0부터 시작하니까 +1
                    val date = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"

                    Text_workDate.setText(date)
                },
                year,
                month,
                day


            )

            datePickerDialog.show()
        }
    }

}