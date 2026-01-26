package com.example.workoach

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditMoneyActivity : AppCompatActivity(){

    private lateinit var Text_CompanyName: EditText
    private lateinit var Text_Salary: EditText
    private lateinit var Text_SalaryDate: EditText
    private lateinit var Btn_Edit: Button
    private lateinit var Btn_Close: ImageButton
    private lateinit var userID : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editmoney)

        userID = intent.getStringExtra("USER_ID") ?: return

        initView()
        setListeners()
        setupDatePicker()


    }

    private fun initView(){
        Text_CompanyName=findViewById<EditText>(R.id.TextCompanyname)
        Text_Salary=findViewById<EditText>(R.id.TextSalary)
        Text_SalaryDate=findViewById<EditText>(R.id.TextSalaryDate)
        Btn_Edit=findViewById<Button>(R.id.btnEditMoney)
        Btn_Close=findViewById<ImageButton>(R.id.btncloseeditmoney)
    }

    private fun setListeners() {


        //월급 수정 버튼
        Btn_Edit.setOnClickListener {
            val companyname = Text_CompanyName.text.toString().trim()
            val textsalary = Text_Salary.text.toString().trim()
            val datesalary = Text_SalaryDate.text.toString().trim()
            val salary = textsalary.toLongOrNull()

            if (companyname.isEmpty() || textsalary.isEmpty() || datesalary.isEmpty()) {
                Toast.makeText(this, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateJobInfo(userID, companyname, textsalary, datesalary)
            //액티비티 종료
            finish()

        }

    }
    private fun updateJobInfo(
        userid: String,
        companyname: String,
        textsalary: String,
        datesalary: String
    ) {
        val dbHelper = DBHelper(this)
        val db = dbHelper.writableDatabase

        val sql = """
                UPDATE jobTBL
                SET jobname = ?,
                    jobsalary =?,
                    jobdate =?
                WHERE userid = ?
            """.trimIndent()

        db.execSQL(sql, arrayOf(companyname, textsalary, datesalary, userid))

        db.close()
    }

    private fun setupDatePicker(){
        Text_SalaryDate.setOnClickListener {
            val calendar = java.util.Calendar.getInstance()

            val year = calendar.get(java.util.Calendar.YEAR)
            val month = calendar.get(java.util.Calendar.MONTH)
            val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // 월은 0부터 시작하니까 +1
                    val date = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"

                    Text_SalaryDate.setText(date)
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
    }

}
