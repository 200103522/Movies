package com.example.movies

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class DbHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, "app", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users_list(id INT PRIMARY KEY, login TEXT, pass TEXT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users_list")
        onCreate(db)
    }

    fun addUser(users: Users){
        val values = ContentValues()
        values.put("login", users.login)
        values.put("pass", users.pass)

        val db = this.writableDatabase
        db.insert("users_list", null, values)

        db.close()



    }

}