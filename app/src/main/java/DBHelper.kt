package com.example.workoach

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "workoach.db", null, 1) {
    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE userTBL(
                userid TEXT PRIMARY KEY,
                userpw TEXT,
                username TEXT,
                startdate TEXT
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE jobTBL(
                userid TEXT PRIMARY KEY,
                jobname TEXT,
                jobsalary INTEGER,
                jobdate TEXT,
                FOREIGN KEY(userid) REFERENCES userTBL(userid)
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE moneyTBL(
                moneyid INTEGER PRIMARY KEY AUTOINCREMENT,
                userid TEXT,
                state INTEGER,
                money INTEGER,
                date TEXT,
                FOREIGN KEY(userid) REFERENCES userTBL(userid)
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS userTBL")
        db.execSQL("DROP TABLE IF EXISTS jobTBL")
        db.execSQL("DROP TABLE IF EXISTS moneyTBL")
        onCreate(db)
    }
}
