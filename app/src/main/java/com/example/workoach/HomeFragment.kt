package com.example.workcoach

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.workoach.DBHelper
import com.example.workoach.R
import com.example.workoach.com.example.workoach.EduCard

data class MoneySummary(
    val totalIncome: Int,
    val totalSpend: Int
)

class HomeFragment : Fragment() {

    private lateinit var userid: String
    private lateinit var cardContainer: LinearLayout

    private val allCards = listOf(
        EduCard(R.drawable.moneybag, "주식", "회사의 조각", "주식을 사면 → 그 회사의 주인 중 한 명\n" + "회사가 잘 되면 → 주식값이 오를 수 있음"),
        EduCard(R.drawable.briefcase, "펀드", "전문가에게 맡기는 투자", "여러 사람의 돈을 모아서\n" + "전문가가 주식·채권 등에 나눠 투자"),
        EduCard(R.drawable.charcinc, "ETF", "펀드 + 주식의 장점 합체", "펀드처럼 여러 자산에 분산 투자\n" + "주식처럼 주식시장에 바로 사고팔 수 있음"),
        EduCard(R.drawable.coin, "금리", "돈의 가격", "금리 ↑ → 대출 이자 부담 커짐 / 예금 이자 많아짐\n" + "금리 ↓ → 대출 쉬워짐 / 예금 이자 적어짐"),
        EduCard(R.drawable.dollar, "환율", "돈의 교환 비율", "1달러 = 몇 원인가?\n" + "해외 주식, 여행, 수입 물가에 영향"),
        EduCard(R.drawable.barchart, "CMA", "돈 잠시 쉬게 하는 통장", "투자 대기 자금 보관용\n" + "→ “안 쓰는 돈 잠깐 보관”에 좋음"),
        EduCard(R.drawable.moneybag, "채권", "나라나 회사에 돈 빌려주기", "정해진 이자 받음\n" + "만기 되면 원금 돌려받음"),
    )

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

            dialog.setCancelable(false) // 바깥 터치로 닫히게 할지 (원하면 true)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardContainer = view.findViewById(R.id.cardContainer)

        showRandomCards()
    }

    override fun onResume() {
        super.onResume()
        // 페이지 재방문 시 카드 교체
        showRandomCards()
    }

    private fun showRandomCards() {
        cardContainer.removeAllViews()

        val randomCards = allCards.shuffled().take(3)

        randomCards.forEach { card ->
            val cardView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_edu_card, cardContainer, false)

            cardView.findViewById<ImageView>(R.id.edu_icon)
                .setImageResource(card.iconRes)
            cardView.findViewById<TextView>(R.id.edu_title)
                .text = card.title
            cardView.findViewById<TextView>(R.id.edu_subtitle)
                .text = card.subTitle
            cardView.findViewById<TextView>(R.id.edu_desc)
                .text = card.description

            cardContainer.addView(cardView)
        }
    }
}
