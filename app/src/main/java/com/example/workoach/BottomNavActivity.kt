package com.example.workoach

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.workcoach.CoachFragment
import com.example.workcoach.HomeFragment
import com.example.workcoach.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.tab_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }

                R.id.tab_coach -> {
                    startActivity(Intent(this, CoachActivity::class.java))
                    true
                }

                R.id.tab_mypage -> {
                    startActivity(Intent(this, MypageActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }
}


