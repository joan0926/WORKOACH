package com.example.workoach

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MypageActivity : AppCompatActivity() {

    private lateinit var Btn_editprofile: Button
    private lateinit var Btn_editmoney: Button
    private lateinit var Btn_accountmanagement: Button
    private lateinit var userid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        userid = intent.getStringExtra("USER_ID") ?: ""

        initView()
        setupBottomNav()

        // 프로필 수정
        Btn_editprofile.setOnClickListener {
            showCustomDialog(R.layout.activity_editprofile, R.id.btnclose_editprofile)
        }

        // 월급 수정
        Btn_editmoney.setOnClickListener {
            showCustomDialog(R.layout.activity_editmoney, R.id.btncloseeditmoney)
        }

        // 계정 관리
        Btn_accountmanagement.setOnClickListener {
            showCustomDialog(R.layout.activity_accountmanagement, R.id.btnclose_accountmanage)
        }
    }

    private fun initView() {
        Btn_editprofile = findViewById(R.id.button_editProfile)
        Btn_editmoney = findViewById(R.id.btn_editmoney)
        Btn_accountmanagement = findViewById(R.id.btn_manageaccount)

        val balance = findViewById<TextView>(R.id.balance)
        val totalSaving = calSavingnSalaryPercent(userid, 20)
        balance.text = totalSaving.toString()
    }

    private fun setupBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNav.selectedItemId = R.id.tab_mypage

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    true
                }
                R.id.tab_coach -> {
                    startActivity(Intent(this, coach::class.java))
                    finish()
                    true
                }
                R.id.tab_mypage -> true
                else -> false
            }
        }
    }

    fun showCustomDialog(@LayoutRes layoutResId: Int, closeBtnId: Int) {
        val dialog = Dialog(this)
        val view = layoutInflater.inflate(layoutResId, null)
        dialog.setContentView(view)

        val btnClose = view.findViewById<ImageButton>(closeBtnId)
        btnClose?.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    fun calSavingnSalaryPercent(userid: String, percent: Int): Int {
        val dbHelper = DBHelper(this)
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
