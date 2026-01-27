package com.example.workcoach

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.workoach.DBHelper
import com.example.workoach.R

class CoachFragment : Fragment() {

    private lateinit var userid: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_coach, container, false)

        val moneyBar = view.findViewById<ProgressBar>(R.id.moneyBar)

        userid = requireActivity().intent.getStringExtra("USER_ID") ?: ""


        val currentMoney = getSavingInfo(userid)
        val targetMoney = 5_000_000

        // ProgressBar 적용
        moneyBar.max = targetMoney //목표금액
        moneyBar.progress = currentMoney  //현재 모은 금액(저금만 일단 계산함)

        return view
    }
    private fun getSavingInfo(userID: String): Int{
        // DB에서 값 불러오기
        val dbHelper = DBHelper(requireContext())
        val db = dbHelper.readableDatabase
        //저축값 계산
        val currentCursor = db.rawQuery(
            """
                SELECT IFNULL(SUM(money),0)
                FROM moneyTBL
                WHERE userid = ? AND state = 2
            """.trimIndent(),
            arrayOf(userID)
        )
        val currentMoney = if(currentCursor.moveToFirst()){
            currentCursor.getInt(0)
        }else 0

        currentCursor.close()
        db.close()

        return currentMoney


    }




}
