package com.vivekgroup.kotlin_sqllite.DBHelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.SyncStateContract.Columns

class DBHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        const val DATABASE_NAME = "MyDatabase"
        const val DATABASE_VERSION = 1
        const val Table_Name = "myTable"
        const val COLUMN_ID = "ID"
        const val COLUMN_NAME= "name"

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(db: SQLiteDatabase?) {
      val createTable ="CREATE TABLE $Table_Name($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_NAME TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("Drop table if exists $Table_Name")
        onCreate(db)
    }

    fun insert(name :String):Long{
        val value = ContentValues()
        value.put(COLUMN_NAME,name)

        val db =this.writableDatabase
        val  id =db.insert(Table_Name,null,value)
        db.close()

        return id
    }


    fun readData(): Cursor{
        val db= this.readableDatabase
        return  db.query(Table_Name,null,null,null,null,null,null)
    }

    fun updateData(id:Long,newName:String):Int{
        val value =ContentValues()
        value.put(COLUMN_NAME,newName)

        val db =this.writableDatabase
        return  db.update(Table_Name,value,"$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun deleteData(id :Long): Int{
        val db =this.writableDatabase
        return db.delete(Table_Name,"$COLUMN_ID=?", arrayOf(id.toString()))
    }
}