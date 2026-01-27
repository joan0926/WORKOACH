package com.example.workcoach

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.workoach.DBHelper
import com.example.workoach.R

data class MoneySummary(val totalIncome: Int, val totalSpend: Int)

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val userid: String by lazy {
        requireActivity().intent.getStringExtra("USER_ID") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moneyBar = view.findViewById<ProgressBar>(R.id.moneyBar)
        val tvPercent = view.findViewById<TextView>(R.id.tvPercent)

        val summary = getMoneySummary(userid)
        val totalMoney = summary.totalIncome
        val usingMoney = summary.totalSpend

        moneyBar.max = totalMoney
        moneyBar.progress = usingMoney

        val percent = if (totalMoney > 0) (usingMoney * 100 / totalMoney) else 0
        tvPercent.text = "$percent%"
    }

    private fun getMoneySummary(userID: String): MoneySummary {
        val dbHelper = DBHelper(requireContext())
        val db = dbHelper.readableDatabase

        val incomeCursor = db.rawQuery(
            "SELECT IFNULL(SUM(money),0) FROM moneyTBL WHERE userid = ? AND state = 0",
            arrayOf(userID)
        )
        val totalIncome = if (incomeCursor.moveToFirst()) incomeCursor.getInt(0) else 0
        incomeCursor.close()

        val spendCursor = db.rawQuery(
            "SELECT IFNULL(SUM(money),0) FROM moneyTBL WHERE userid = ? AND state IN (1,2)",
            arrayOf(userID)
        )
        val totalSpend = if (spendCursor.moveToFirst()) spendCursor.getInt(0) else 0
        spendCursor.close()

        db.close()
        return MoneySummary(totalIncome, totalSpend)
    }
}
