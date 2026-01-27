package com.example.workcoach

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.workoach.DBHelper
import com.example.workoach.R

data class MoneySummary(
    val totalIncome: Int,
    val totalSpend: Int
)

class HomeFragment : Fragment() {

    private lateinit var userid: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        userid= requireActivity().intent.getStringExtra("USER_ID") ?: ""
        val moneyBar = view.findViewById<ProgressBar>(R.id.moneyBar)
        val tvPercent = view.findViewById<TextView>(R.id.tvPercent)
        val button = view.findViewById<Button>(R.id.button)


        val summary = getMoneySummary(userid)
        val totalmoney = summary.totalIncome
        val usingmoney = summary.totalSpend


        // 테스트용 값
        //val totalmoney = 3_000_000
        //val usingmoney = 1_200_000

        moneyBar.max = totalmoney
        moneyBar.progress = usingmoney

        val percent = if (totalmoney > 0) (usingmoney * 100 / totalmoney) else 0
        tvPercent.text = "$percent%"

        // 월급 수정 버튼 클릭 → 다이얼로그
        button.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.activity_editmoney)
            dialog.show()
        }

        return view
    }
    private fun getMoneySummary(userID: String): MoneySummary{
        val dbHelper = DBHelper(requireContext())
        val db = dbHelper.readableDatabase

        val incomeCursor = db.rawQuery(
            """
                SELECT IFNULL(SUM(money),0)
                FROM moneyTBL
                WHERE userid =? AND state = 0
            """.trimIndent(),
            arrayOf(userID)
        )
        val totalIncome = if (incomeCursor.moveToFirst()){
            incomeCursor.getInt(0)
        }else 0
        incomeCursor.close()

        val spendCursor = db.rawQuery(
            """
                SELECT IFNULL(SUM(money),0)
                FROM moneyTBL
                WHERE userid =? AND state IN (1,2)
            """.trimIndent(),
            arrayOf(userID)
        )
        val totalSpend = if(spendCursor.moveToFirst()){
            spendCursor.getInt(0)
        }else 0
        spendCursor.close()
        db.close()

        return MoneySummary(
            totalIncome = totalIncome,
            totalSpend = totalSpend
        )
    }
}
