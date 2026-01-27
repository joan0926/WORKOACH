package com.example.workoach

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

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment())
            .commit()

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment())
                        .commit()
                    true
                }
                R.id.tab_coach -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CoachFragment())
                        .commit()
                    true
                }
                R.id.tab_mypage -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MyPageFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
