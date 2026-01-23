package com.example.workoach

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
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


                lateinit var button: Button

                button = findViewById<Button>(R.id.button)

                button.setOnClickListener {
                    showCustomDialog()
                }

            }

            fun showCustomDialog() { //다이얼로그 뜨기
                val dialog = Dialog(this)

                val view = layoutInflater.inflate(R.layout.activity_editmoney, null)

                dialog.setContentView(view)
                dialog.show()
            }



    }

