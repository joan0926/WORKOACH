package com.example.workoach

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class EditMoneyDialog : DialogFragment() {

    private lateinit var Text_CompanyName: EditText
    private lateinit var Text_Salary: EditText
    private lateinit var Text_SalaryDate: EditText
    private lateinit var Btn_Edit: Button
    private lateinit var Btn_Close: ImageButton

    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userID = arguments?.getString("USER_ID") ?: ""
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val view = LayoutInflater.from(context)
            .inflate(R.layout.activity_editmoney, null)

        dialog.setContentView(view)
        dialog.setCancelable(false) // 밖 클릭해도 닫히지 않게 하려면 false

        initView(view)
        setListeners()

        return dialog
    }

    private fun initView(view: View) {
        Text_CompanyName = view.findViewById(R.id.TextCompanyname)
        Text_Salary = view.findViewById(R.id.TextSalary)
        Text_SalaryDate = view.findViewById(R.id.TextSalaryDate)
        Btn_Edit = view.findViewById(R.id.btnEditMoney)
        Btn_Close = view.findViewById(R.id.btncloseeditmoney)
    }

    private fun setListeners() {

        // 캘린더 띄우기
        Text_SalaryDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val date = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"
                    Text_SalaryDate.setText(date)
                },
                year, month, day
            ).show()
        }

        // 수정 버튼 클릭
        Btn_Edit.setOnClickListener {
            val companyname = Text_CompanyName.text.toString().trim()
            val textsalary = Text_Salary.text.toString().trim()
            val datesalary = Text_SalaryDate.text.toString().trim()

            if (companyname.isEmpty() || textsalary.isEmpty() || datesalary.isEmpty()) {
                Toast.makeText(context, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateJobInfo(userID, companyname, textsalary, datesalary)
            dismiss() // 다이얼로그 닫기
        }

        Btn_Close.setOnClickListener {
            dismiss()
        }
    }

    private fun updateJobInfo(userid: String, companyname: String, textsalary: String, datesalary: String) {
        val db = DBHelper(requireContext()).writableDatabase

        val sql = """
            UPDATE jobTBL
            SET jobname = ?,
                jobsalary = ?,
                jobdate = ?
            WHERE userid = ?
        """.trimIndent()

        db.execSQL(sql, arrayOf(companyname, textsalary, datesalary, userid))
        db.close()
    }

    companion object {
        fun newInstance(userID: String): EditMoneyDialog {
            val dialog = EditMoneyDialog()
            val bundle = Bundle()
            bundle.putString("USER_ID", userID)
            dialog.arguments = bundle
            return dialog
        }
    }
}
