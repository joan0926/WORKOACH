package com.example.workcoach

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.workoach.DBHelper
import com.example.workoach.R

class MyPageFragment : Fragment() {

    private lateinit var Btn_editprofile: Button
    private lateinit var Btn_editmoney: Button
    private lateinit var Btn_accountmanagement: Button

    private var userid = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.activity_mypage, container, false)

        userid = requireActivity().intent.getStringExtra("USER_ID") ?: ""

        Btn_editprofile = view.findViewById(R.id.button_editProfile)
        Btn_editmoney = view.findViewById(R.id.btn_editmoney)
        Btn_accountmanagement = view.findViewById(R.id.btn_manageaccount)

        val balance = view.findViewById<TextView>(R.id.balance)
        balance.text = calSavingnSalaryPercent(userid, 20).toString()

        Btn_editprofile.setOnClickListener {
            showCustomDialog(R.layout.activity_editprofile, R.id.btnclose_editprofile)
        }

        Btn_editmoney.setOnClickListener {
            showCustomDialog(R.layout.activity_editmoney, R.id.btncloseeditmoney)
        }

        Btn_accountmanagement.setOnClickListener {
            showCustomDialog(R.layout.activity_accountmanagement, R.id.btnclose_accountmanage)
        }

        return view
    }

    private fun showCustomDialog(@LayoutRes layoutResId: Int, closeBtnId: Int) {
        val dialog = Dialog(requireContext())
        val v = layoutInflater.inflate(layoutResId, null)
        dialog.setContentView(v)

        v.findViewById<ImageButton>(closeBtnId)?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun calSavingnSalaryPercent(userid: String, percent: Int): Int {
        val dbHelper = DBHelper(requireContext())
        val db = dbHelper.readableDatabase

        val sql = """
            SELECT
                IFNULL(
                    (SELECT SUM(money)
                    FROM moneyTBL
                    WHERE userid = ?
                        AND state = 2), 0
                )
                +
                IFNULL(
                    (SELECT jobsalary * ? / 100
                    FROM jobTBL
                    WHERE userid = ?
                    LIMIT 1), 0
                ) AS resultMoney
        """.trimIndent()

        val cursor = db.rawQuery(sql, arrayOf(userid, percent.toString(), userid))

        var result = 0
        if (cursor.moveToFirst()) {
            result = cursor.getInt(cursor.getColumnIndexOrThrow("resultMoney"))
        }

        cursor.close()
        db.close()
        return result
    }
}
