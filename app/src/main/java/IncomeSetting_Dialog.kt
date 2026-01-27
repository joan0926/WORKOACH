package com.example.workoach

import android.app.DatePickerDialog
import androidx.fragment.app.DialogFragment
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import java.util.Calendar


class IncomeSettingDialog: DialogFragment() {
    private lateinit var Text_income: EditText
    private lateinit var Text_incomeDate: EditText
    private lateinit var Btn_income: Button
    private lateinit var Btn_Close_income: ImageButton

    private lateinit var userid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userid = arguments?.getString("USER_ID") ?: ""
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val view = LayoutInflater.from(context)
            .inflate(R.layout.activity_incomsetting, null)
        dialog.setContentView(view)
        dialog.setCancelable(false)

        initView(view)
        setListeners()

        return dialog
    }

    private fun initView(view: View) {
        Text_income = view.findViewById(R.id.TextincomeSalary)
        Text_incomeDate = view.findViewById(R.id.TextincomeDate)
        Btn_income = view.findViewById(R.id.btnincomeSetting)
        Btn_Close_income = view.findViewById(R.id.btncloseincomeSetting)
    }

    private fun setListeners() {

        Text_incomeDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val date = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"
                    Text_incomeDate.setText(date)
                },
                year, month, day
            ).show()
        }

        Btn_income.setOnClickListener {
            val income = Text_income.text.toString().trim()
            val incomeDate = Text_incomeDate.text.toString().trim()

            if (income.isEmpty() || incomeDate.isEmpty()) {
                Toast.makeText(context, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            insertIncome(userid, income, incomeDate)
            dismiss()
        }

        Btn_Close_income.setOnClickListener {
            dismiss()
        }
    }


    private fun insertIncome(userid: String, income: String, incomeDate: String) {
        val money = income.toIntOrNull() ?: return
        val state = 0

        val db = DBHelper(requireContext()).writableDatabase

        val sql = """
            INSERT INTO moneyTBL (userid, state, money, date)
            VALUES (?, ?, ?, ?)
        """.trimIndent()

        db.execSQL(sql, arrayOf(userid, state, money, incomeDate))
        db.close()
    }

    companion object {
        fun newInstance(userid: String): IncomeSettingDialog {
            val dialog = IncomeSettingDialog()
            val bundle = Bundle()
            bundle.putString("USER_ID", userid)
            dialog.arguments = bundle
            return dialog
        }
    }
}