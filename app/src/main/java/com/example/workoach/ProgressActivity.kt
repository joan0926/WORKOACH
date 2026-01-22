package com.example.workoach

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvPercent = findViewById<TextView>(R.id.tvPercent)

        // 예시 데이터
        val goalAmount = 1_000_000   // 목표 금액
        val currentAmount = 450_000  // 현재 금액

        val percent = (currentAmount * 100) / goalAmount

        progressBar.progress = percent
        tvPercent.text = "$percent%"
    }
}
