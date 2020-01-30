package com.student.dublin_assignment_kotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Databse creation for the android
//(MD.ROKIBUL ISLAM
//STIDENT ID:18315)
class Database(context: Context?) :
    SQLiteOpenHelper(context, Db_name, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
//         used to create the databse table from the query
        db.execSQL(
            "Create table " + Table_name + "("
                    + Id + " integer primary key autoincrement ,"
                    + Area + " text ,"
                    + Level + " text ,"
                    + Type + " text )"
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase, oldVersion: Int, newVersion: Int
    )
    {

    }
//     used to insert the data into table
    fun INSERT_NEW_USER_DATA(area: String?, level: String?, type: String?): Boolean
    {
        val sqLiteDatabase = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Area, area)
        contentValues.put(Level, level)
        contentValues.put(Type, type)
        val res = sqLiteDatabase.insert(Table_name, null, contentValues)
        //Toast.makeText(C, res +"   signup", Toast.LENGTH_SHORT).show();
        return if (res == -1L) { false } else { true }
    }
// get the warning from the table to show on acitvirty
    fun GET_WARNING(): Cursor {
        val sqLiteDatabase = writableDatabase
//    query line
        return sqLiteDatabase.rawQuery("select * from "+Table_name, null)
    }

    fun delete_ALL(id: String): Int {
        val db = writableDatabase
        return db.delete(Table_name, Id + "=" + id, null)
    }

    fun update_all(
        id: String,
        area: String?,
        level: String?,
        type: String?
    ): Int {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(area, area)
        cv.put(type, type)
        cv.put(level, level)
        return db.update(Table_name, cv, id + "=" + id, null)
    }

    companion object {
        protected const val Db_name = "warning.db"
        const val Table_name = "warn_table"
        const val Id = "id"
        const val Area = "areea"
        const val Level = "levele"
        const val Type = "typee"
    }
}