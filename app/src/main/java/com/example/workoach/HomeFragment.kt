package com.example.workcoach

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.workoach.DBHelper
import com.example.workoach.R
import com.example.workoach.com.example.workoach.EduCard
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
    private lateinit var cardContainer: LinearLayout

    private val eduCards = listOf(

        EduCard(
            R.drawable.briefcase,
            "주식",
            "회사의 조각",
            "주식을 사면 → 그 회사의 주인 중 한 명\n회사가 잘 되면 → 주식값이 오를 수 있음"
        ),

        EduCard(
            R.drawable.coin,
            "펀드",
            "전문가에게 맡기는 투자",
            "여러 사람의 돈을 모아서\n전문가가 주식·채권 등에 나눠 투자"
        ),

        EduCard(
            R.drawable.dollar,
            "ETF",
            "펀드 + 주식의 장점 합체",
            "펀드처럼 여러 자산에 분산 투자\n주식처럼 주식시장에서 바로 사고팔 수 있음"
        ),

        EduCard(
            R.drawable.charcinc,
            "금리",
            "돈의 가격",
            "금리 ↑ → 대출 이자 부담 커짐 / 예금 이자 많아짐\n금리 ↓ → 대출 쉬워짐 / 예금 이자 적어짐"
        ),

        EduCard(
            R.drawable.barchart,
            "환율",
            "돈의 교환 비율",
            "1달러 = 몇 원인가?\n해외 주식, 여행, 수입 물가에 영향"
        ),

        EduCard(
            R.drawable.coin,
            "CMA",
            "돈 잠시 쉬게 하는 통장",
            "투자 대기 자금 보관용\n→ \"안 쓰는 돈 잠깐 보관\"에 좋음"
        ),

        EduCard(
            R.drawable.dollar,
            "채권",
            "나라나 회사에 돈 빌려주기",
            "정해진 이자 받음\n만기 되면 원금 돌려받음"
        ),
    )

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
        cardContainer = view.findViewById(R.id.cardContainer)

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

        loadEduCards()
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

    // 카드 생성 함수
    private fun loadEduCards() {

        val inflater = layoutInflater

        // 기존 카드 제거 (중요)
        cardContainer.removeAllViews()

        // 랜덤 3개 선택
        val randomCards = eduCards.shuffled().take(3)

        for (card in randomCards) {

            val cardView = inflater.inflate(
                R.layout.item_edu_card,
                cardContainer,
                false
            )

            val icon = cardView.findViewById<ImageView>(R.id.edu_icon)
            val title = cardView.findViewById<TextView>(R.id.edu_title)
            val sub = cardView.findViewById<TextView>(R.id.edu_subtitle)
            val desc = cardView.findViewById<TextView>(R.id.edu_desc)

            icon.setImageResource(card.iconRes)
            title.text = card.title
            sub.text = card.subTitle
            desc.text = card.description

            cardContainer.addView(cardView)
        }
    }
}
