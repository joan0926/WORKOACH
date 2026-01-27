package com.example.workcoach

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.workoach.DBHelper
import com.example.workoach.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val userid: String by lazy {
        requireActivity().intent.getStringExtra("USER_ID") ?: ""
    }

    private lateinit var moneyBar: ProgressBar
    private lateinit var tvPercent: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvSpent: TextView
    private lateinit var tvRemain: TextView
    private lateinit var btnIncome: Button
    private lateinit var btnOutgoing: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View 연결
        moneyBar = view.findViewById(R.id.moneyBar)
        tvPercent = view.findViewById(R.id.tvPercent)
        tvTotal = view.findViewById(R.id.tvTotalMoney)
        tvSpent = view.findViewById(R.id.tvSpentMoney)
        tvRemain = view.findViewById(R.id.tvRemainMoney)
        btnIncome = view.findViewById(R.id.btnIncome)
        btnOutgoing = view.findViewById(R.id.btnOutgoing)

        // 처음 화면 갱신
        refreshUI()

        // 수입 버튼
        btnIncome.setOnClickListener {
            showMoneyDialog(0)
        }

        // 지출 버튼
        btnOutgoing.setOnClickListener {
            showMoneyDialog(1)
        }
    }

    // ============================
    // 전체 화면 갱신
    // ============================
    private fun refreshUI() {

        val salary = getSalary()              // 월급
        val income = getTotalIncome()         // 수입
        val spend = getTotalSpend()           // 지출

        val total = salary + income
        val remain = total - spend

        // ProgressBar
        moneyBar.max = total
        moneyBar.progress = spend

        val percent =
            if (total > 0) (spend * 100 / total) else 0

        tvPercent.text = "$percent%"

        // 금액 표시
        tvTotal.text = formatMoney(total)
        tvSpent.text = formatMoney(spend)
        tvRemain.text = formatMoney(remain)
    }

    // ============================
    // 월급 가져오기
    // ============================
    private fun getSalary(): Int {

        val db = DBHelper(requireContext()).readableDatabase

        val cursor = db.rawQuery(
            "SELECT IFNULL(jobsalary,0) FROM jobTBL WHERE userid = ?",
            arrayOf(userid)
        )

        val salary =
            if (cursor.moveToFirst()) cursor.getInt(0) else 0

        cursor.close()
        db.close()

        return salary
    }

    // ============================
    // 수입 합계
    // ============================
    private fun getTotalIncome(): Int {

        val db = DBHelper(requireContext()).readableDatabase

        val cursor = db.rawQuery(
            "SELECT IFNULL(SUM(money),0) FROM moneyTBL WHERE userid=? AND state=0",
            arrayOf(userid)
        )

        val result =
            if (cursor.moveToFirst()) cursor.getInt(0) else 0

        cursor.close()
        db.close()

        return result
    }

    // ============================
    // 지출 합계
    // ============================
    private fun getTotalSpend(): Int {

        val db = DBHelper(requireContext()).readableDatabase

        val cursor = db.rawQuery(
            "SELECT IFNULL(SUM(money),0) FROM moneyTBL WHERE userid=? AND state=1",
            arrayOf(userid)
        )

        val result =
            if (cursor.moveToFirst()) cursor.getInt(0) else 0

        cursor.close()
        db.close()

        return result
    }

    // ============================
    // 수입/지출 입력창
    // ============================
    private fun showMoneyDialog(state: Int) {

        val editText = EditText(requireContext())
        editText.inputType = android.text.InputType.TYPE_CLASS_NUMBER

        val title =
            if (state == 0) "수입 입력" else "지출 입력"

        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setView(editText)
            .setPositiveButton("등록") { _, _ ->

                val moneyText = editText.text.toString()

                if (moneyText.isNotEmpty()) {
                    insertMoney(moneyText.toInt(), state)
                    refreshUI()
                }
            }
            .setNegativeButton("취소", null)
            .show()
    }

    // ============================
    // DB 저장
    // ============================
    private fun insertMoney(money: Int, state: Int) {

        val db = DBHelper(requireContext()).writableDatabase

        val date =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date())

        val sql = """
            INSERT INTO moneyTBL(userid,state,money,date)
            VALUES(?,?,?,?)
        """

        db.execSQL(
            sql,
            arrayOf(userid, state, money, date)
        )

        db.close()
    }

    // ============================
    // 돈 포맷
    // ============================
    private fun formatMoney(money: Int): String {

        val df = DecimalFormat("#,###")

        return df.format(money) + " 원"
    }
}
