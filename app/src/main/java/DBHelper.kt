package com.example.workoach

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.SQLXML

class DBHelper(context: Context): SQLiteOpenHelper(context, "workoach.db",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE userTBL(" +
                "userid TEXT PRIMARY KEY," +
                "userpw TEXT," +
                "username TEXT," +
                "startdate TEXT )")


    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS userTBL")
        onCreate(db)
    }

}