package com.example.workoach

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class SavingSettingDialog : DialogFragment() {

    private lateinit var Text_saving: EditText
    private lateinit var Text_savingDate: EditText
    private lateinit var Btn_saving: Button
    private lateinit var Btn_Close_saving: ImageButton

    private lateinit var userid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userid = arguments?.getString("USER_ID") ?: ""
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val view = LayoutInflater.from(context)
            .inflate(R.layout.activity_savingsetting, null)

        dialog.setContentView(view)
        dialog.setCancelable(false)

        initView(view)
        setListeners()

        return dialog
    }

    private fun initView(view: View) {
        Text_saving = view.findViewById(R.id.TextsavingSalary)
        Text_savingDate = view.findViewById(R.id.TextsavingDate)
        Btn_saving = view.findViewById(R.id.btnsavingSetting)
        Btn_Close_saving = view.findViewById(R.id.btnclosesavingSetting)
    }

    private fun setListeners() {
        Text_savingDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    Text_savingDate.setText(
                        "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"
                    )
                },
                year, month, day
            ).show()
        }

        Btn_saving.setOnClickListener {
            val saving = Text_saving.text.toString().trim()
            val savingDate = Text_savingDate.text.toString().trim()

            if (saving.isEmpty() || savingDate.isEmpty()) {
                Toast.makeText(context, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            insertSaving(userid, saving, savingDate)
            dismiss()
        }

        Btn_Close_saving.setOnClickListener {
            dismiss()
        }
    }

    private fun insertSaving(userid: String, saving: String, savingDate: String) {
        val money = saving.toIntOrNull() ?: return
        val state = 2

        val db = DBHelper(requireContext()).writableDatabase
        val sql = """
            INSERT INTO moneyTBL (userid, state, money, date)
            VALUES (?, ?, ?, ?)
        """.trimIndent()

        db.execSQL(sql, arrayOf(userid, state, money, savingDate))
        db.close()
    }

    companion object {
        fun newInstance(userid: String): SavingSettingDialog {
            val dialog = SavingSettingDialog()
            val bundle = Bundle()
            bundle.putString("USER_ID", userid)
            dialog.arguments = bundle
            return dialog
        }
    }
}
