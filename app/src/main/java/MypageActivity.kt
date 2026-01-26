package com.example.workoach

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

class MypageActivity : AppCompatActivity() {


    private lateinit var Btn_editprofile: Button //프로필 수정하기
    private lateinit var Btn_editmoney: Button //월급 수정하기
    private lateinit var Btn_accountmanagement: Button //계정관리
    private lateinit var userid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        val userid = intent.getStringExtra("USER_ID") ?: return

        initView()
        Btn_editprofile.setOnClickListener {
            showCustomDialog(R.layout.activity_editprofile, R.id.btnclose_editprofile)
        }
        Btn_editmoney.setOnClickListener {
            showCustomDialog(R.layout.activity_editmoney, R.id.btncloseeditmoney)
        }
        Btn_accountmanagement.setOnClickListener {
            showCustomDialog(R.layout.activity_accountmanagement, R.id.btnclose_accountmanage)
        }

    }

    private fun initView() {
        val balance = findViewById<TextView>(R.id.balance)
        val totalSaving = calSavingnSalaryPercent(userid,20)
        balance.text = totalSaving.toString()
    }

    fun showCustomDialog(@LayoutRes layoutResId: Int, closeBtnId: Int) {
        val dialog = Dialog(this)

        val view = layoutInflater.inflate(layoutResId, null)
        dialog.setContentView(view)

        val btnClose = view.findViewById<ImageButton>(closeBtnId)
        btnClose?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun calSavingnSalaryPercent(userid: String, percent: Int): Int {
        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase

        val sql="""
            SELECT
                IFNULL(
                    (SELECT SUM(money)
                    FROM moneyTBL
                    WHERE userid = ?
                        AND state =2),0
                )
                +
                IFNULL(
                    (SELECT jobsalary * ?/100
                    FROM jobTBL
                    WHERE userid = ?
                    LIMIT 1),0
                )AS resultMonney
        """.trimIndent()

        val cursor = db.rawQuery(
            sql, arrayOf(userid, percent.toString(),userid)
        )

        var result = 0
        if(cursor.moveToFirst()){
            result = cursor.getInt(
                cursor.getColumnIndexOrThrow("resultMoney")
            )
        }
        cursor.close()
        db.close()

        return result

    }


    }
