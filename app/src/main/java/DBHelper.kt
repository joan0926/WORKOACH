package com.example.workoach

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.SQLXML

class DBHelper(context: Context): SQLiteOpenHelper(context, "workoach.db",null,1) {
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
                "jobsalary TEXT," +
                "jobdate TEXT," +
                "FOREIGN KEY(userid) REFERENCES userTBL(userid))")

        //사용자 지출수입 정보 테이블(id, 사용자id, 지출0/수입1,돈크기)
        db!!.execSQL("CREATE TABLE moneyTBL(" +
                "moneyid INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "userid TEXT," +
                "state INTEGER," +
                "money INTEGER,"+
                "FOREIGN KEY(userid) REFERENCES userTBL(userid))")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS userTBL")
        db!!.execSQL("DROP TABLE IF EXISTS jobTBL")
        db!!.execSQL("DROP TABLE IF EXISTS moneyTBL")
        onCreate(db)
    }

}