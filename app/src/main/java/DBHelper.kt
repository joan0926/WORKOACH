package com.example.workoach

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.SQLXML

class DBHelper(context: Context): SQLiteOpenHelper(context, "workoach.db",null,1) {
    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //사용자 정보 테이블
        db!!.execSQL("CREATE TABLE userTBL(" +
                "userid TEXT PRIMARY KEY," +
                "userpw TEXT," +
                "username TEXT," +
                "startdate TEXT )")

        //사용자 직장 정보 테이블
        db!!.execSQL("CREATE TABLE jobTBL(" +
                "userid TEXT PRIMARY KEY," +
                "jobname TEXT," +
                "jobsalary INTEGER," +
                "jobdate TEXT," +
                "FOREIGN KEY(userid) REFERENCES userTBL(userid))")

        //사용자 금액 정보 테이블(id, 사용자id, 수입0/지출1/저축2, 돈크기)
        db!!.execSQL("CREATE TABLE moneyTBL(" +
                "moneyid INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "userid TEXT," +
                "state INTEGER," +
                "money INTEGER,"+
                "date TEXT,"+
                "FOREIGN KEY(userid) REFERENCES userTBL(userid))")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS userTBL")
        db!!.execSQL("DROP TABLE IF EXISTS jobTBL")
        db!!.execSQL("DROP TABLE IF EXISTS moneyTBL")
        onCreate(db)
    }

}