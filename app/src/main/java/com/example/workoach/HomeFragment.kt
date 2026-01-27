package com.example.workcoach

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.workoach.DBHelper
import com.example.workoach.IncomeSettingDialog
import com.example.workoach.OutgoingSettingDialog
import com.example.workoach.R

data class MoneySummary(val totalIncome: Int, val totalSpend: Int)

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var userid: String
    private lateinit var cardContainer: LinearLayout
    private lateinit var Btn_incomeSetting: Button
    private lateinit var Btn_outgoingSetting: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = super.onCreateView(inflater, container, savedInstanceState)!!

        userid = requireActivity().intent.getStringExtra("USER_ID") ?: ""
        val moneyBar = view.findViewById<ProgressBar>(R.id.moneyBar)
        val tvPercent = view.findViewById<TextView>(R.id.tvPercent)
        Btn_incomeSetting = view.findViewById(R.id.btnIncome)
        Btn_outgoingSetting = view.findViewById(R.id.btnOutgoing)

        val summary = getMoneySummary(userid)
        val totalmoney = summary.totalIncome
        val usingmoney = summary.totalSpend

        moneyBar.max = totalmoney
        moneyBar.progress = usingmoney

        val percent = if (totalmoney > 0) (usingmoney * 100 / totalmoney) else 0
        tvPercent.text = "$percent%"

        Btn_incomeSetting.setOnClickListener {
            val dialog_income = IncomeSettingDialog.newInstance(userid)
            dialog_income.show(parentFragmentManager, "IncomeSetting_Dialog")
        }

        Btn_outgoingSetting.setOnClickListener {
            val dialog_outgoing = OutgoingSettingDialog.newInstance(userid)
            dialog_outgoing.show(parentFragmentManager, "OutgoingSettingDialog")
        }

        return view
    }

    private fun getMoneySummary(userID: String): MoneySummary {
        val dbHelper = DBHelper(requireContext())
        val db = dbHelper.readableDatabase

        val incomeCursor = db.rawQuery(
            "SELECT IFNULL(SUM(money),0) FROM moneyTBL WHERE userid=? AND state=0",
            arrayOf(userID)
        )
        val totalIncome = if (incomeCursor.moveToFirst()) incomeCursor.getInt(0) else 0
        incomeCursor.close()

        val spendCursor = db.rawQuery(
            "SELECT IFNULL(SUM(money),0) FROM moneyTBL WHERE userid=? AND state IN (1,2)",
            arrayOf(userID)
        )
        val totalSpend = if (spendCursor.moveToFirst()) spendCursor.getInt(0) else 0
        spendCursor.close()
        db.close()

        return MoneySummary(totalIncome, totalSpend)
    }
}
