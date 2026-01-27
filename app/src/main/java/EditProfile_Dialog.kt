package com.example.workoach

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class EditProfileDialog : DialogFragment() {

    private lateinit var Text_username: EditText
    private lateinit var Text_workDate: EditText
    private lateinit var Btn_editprofile: Button
    private lateinit var Btn_Close_editprofile: ImageButton

    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fragment로 전달된 USER_ID
        userID = arguments?.getString("USER_ID") ?: ""
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val view = LayoutInflater.from(context)
            .inflate(R.layout.activity_editprofile, null) // 기존 XML 사용
        dialog.setContentView(view)
        dialog.setCancelable(false) // 밖 클릭해도 닫히지 않게

        initView(view)
        setListeners()

        return dialog
    }

    private fun initView(view: View) {
        Text_username = view.findViewById(R.id.Texteditprofile_Name)
        Text_workDate = view.findViewById(R.id.TextemployDate)
        Btn_editprofile = view.findViewById(R.id.btneditprofile)
        Btn_Close_editprofile = view.findViewById(R.id.btnclose_editprofile)
    }

    private fun setListeners() {

        // 취직일 EditText 클릭 시 캘린더 표시
        Text_workDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val date = "${selectedYear}년 ${selectedMonth + 1}월 ${selectedDay}일"
                    Text_workDate.setText(date)
                },
                year, month, day
            ).show()
        }

        // 수정 버튼 클릭
        Btn_editprofile.setOnClickListener {
            val username = Text_username.text.toString().trim()
            val workDate = Text_workDate.text.toString().trim()

            if (username.isEmpty() || workDate.isEmpty()) {
                Toast.makeText(context, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateUserAndJob(userID, username, workDate)
            dismiss()
        }

        // 닫기 버튼 클릭
        Btn_Close_editprofile.setOnClickListener {
            dismiss()
        }
    }

    private fun updateUserAndJob(userid: String, username: String, workDate: String) {
        val db = DBHelper(requireContext()).writableDatabase
        db.beginTransaction()
        try {
            db.execSQL(
                """
                    UPDATE userTBL
                    SET username = ?
                    WHERE userid = ?
                """.trimIndent(),
                arrayOf(username, userid)
            )
            db.execSQL(
                """
                    UPDATE jobTBL
                    SET jobdate = ?
                    WHERE userid = ?
                """.trimIndent(),
                arrayOf(workDate, userid)
            )
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    companion object {
        fun newInstance(userID: String): EditProfileDialog {
            val dialog = EditProfileDialog()
            val bundle = Bundle()
            bundle.putString("USER_ID", userID)
            dialog.arguments = bundle
            return dialog
        }
    }
}
