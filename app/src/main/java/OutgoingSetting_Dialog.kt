package com.example.workoach

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class OutgoingSettingDialog : DialogFragment() {

    private lateinit var Text_outgoing: EditText
    private lateinit var Text_outgoingDate: EditText
    private lateinit var Btn_outgoing: Button
    private lateinit var Btn_Close_outgoing: ImageButton

    private lateinit var userid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userid = arguments?.getString("USER_ID") ?: ""
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val view = LayoutInflater.from(context)
            .inflate(R.layout.activity_outgoingsetting, null)

        dialog.setContentView(view)
        dialog.setCancelable(false)

        initView(view)
        setListeners()

        return dialog
    }

    private fun initView(view: View) {
        Text_outgoing = view.findViewById(R.id.TextoutgoingSalary)
        Text_outgoingDate = view.findViewById(R.id.TextoutgoingDate)
        Btn_outgoing = view.findViewById(R.id.btnoutgoingSetting)
        Btn_Close_outgoing = view.findViewById(R.id.btncloseoutgingSetting)
    }

    private fun setListeners() {

        // 날짜 클릭 → 캘린더 열기
        Text_outgoingDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val dateText = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"
                    Text_outgoingDate.setText(dateText)
                },
                year, month, day
            ).show()
        }

        // 지출 등록 버튼
        Btn_outgoing.setOnClickListener {
            val outgoing = Text_outgoing.text.toString().trim()
            val outgoingDate = Text_outgoingDate.text.toString().trim()

            if (outgoing.isEmpty() || outgoingDate.isEmpty()) {
                Toast.makeText(context, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            insertOutgoingMoney(userid, outgoing, outgoingDate)
            dismiss()
        }

        // 닫기 버튼
        Btn_Close_outgoing.setOnClickListener {
            dismiss()
        }
    }

    private fun insertOutgoingMoney(userid: String, outgoing: String, outgoingDate: String) {
        val money = outgoing.toIntOrNull() ?: return
        val state = 1

        val db = DBHelper(requireContext()).writableDatabase

        val sql = """
            INSERT INTO moneyTBL (userid, state, money, date)
            VALUES (?, ?, ?, ?)
        """.trimIndent()

        db.execSQL(sql, arrayOf(userid, state, money, outgoingDate))
        db.close()
    }

    companion object {
        fun newInstance(userid: String): OutgoingSettingDialog {
            val dialog = OutgoingSettingDialog()
            val bundle = Bundle()
            bundle.putString("USER_ID", userid)
            dialog.arguments = bundle
            return dialog
        }
    }
}