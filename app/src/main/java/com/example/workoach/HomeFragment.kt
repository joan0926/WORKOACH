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
import com.example.workoach.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val moneyBar = view.findViewById<ProgressBar>(R.id.moneyBar)
        val tvPercent = view.findViewById<TextView>(R.id.tvPercent)
        val button = view.findViewById<Button>(R.id.button)

        // 테스트용 값
        val totalmoney = 3_000_000
        val usingmoney = 1_200_000

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
}
