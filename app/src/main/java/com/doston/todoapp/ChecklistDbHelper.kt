package com.doston.todoapp


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ChecklistDbHelper(context: Context) : SQLiteOpenHelper(context, "checklists.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE checklists (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                items TEXT
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS checklists")
        onCreate(db)
    }

    fun insertChecklist(title: String, items: List<String>) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("items", items.joinToString("|"))
        }
        db.insert("checklists", null, values)
    }

    fun getAllChecklists(): List<Checklist> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM checklists", null)
        val list = mutableListOf<Checklist>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                val items = cursor.getString(cursor.getColumnIndexOrThrow("items")).split("|").filter { it.isNotBlank() }
                list.add(Checklist(id, title, items))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun deleteAll() {
        val db = writableDatabase
        db.execSQL("DELETE FROM checklists")
    }
}
