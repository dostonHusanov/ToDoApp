package com.doston.todoapp.database


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.doston.todoapp.data.Checklist

class ChecklistDbHelper(context: Context) : SQLiteOpenHelper(context, "checklists.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
    CREATE TABLE checklists (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        title TEXT,
        items TEXT,
        created_date TEXT,
        is_archived INTEGER DEFAULT 0
    )
""")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS checklists")
        onCreate(db)
    }

    fun insertChecklist(title: String, items: List<String>, createdDate: String, isArchived: Boolean = false) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("items", items.joinToString("|"))
            put("created_date", createdDate)
            put("is_archived", if (isArchived) 1 else 0)
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
                val createdDate = cursor.getString(cursor.getColumnIndexOrThrow("created_date"))
                val isArchived = cursor.getInt(cursor.getColumnIndexOrThrow("is_archived")) == 1
                list.add(Checklist(id, title, items, createdDate, isArchived))
            } while (cursor.moveToNext())
        }
cursor.close()

        return list
    }
    fun updateChecklist(id: Int, title: String, items: List<String>) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("items", items.joinToString("|"))
        }
        db.update("checklists", values, "id = ?", arrayOf(id.toString()))

    }

    fun deleteAll(id: Int) {
        val db = writableDatabase
        db.delete("checklists", "id = ?", arrayOf(id.toString()))

    }
    fun archiveChecklist(id: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("is_archived", 1)
        }
        db.update("checklists", values, "id = ?", arrayOf(id.toString()))

    }
    fun getArchivedChecklists(): List<Checklist> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM checklists WHERE is_archived = 1", null)
        val list = mutableListOf<Checklist>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                val items = cursor.getString(cursor.getColumnIndexOrThrow("items")).split("|").filter { it.isNotBlank() }
                val createdDate = cursor.getString(cursor.getColumnIndexOrThrow("created_date"))
                list.add(Checklist(id, title, items, createdDate))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
    fun clearAllArchived() {
        val db = writableDatabase
        db.delete("checklists", "is_archived = 1", null)
        db.close()
    }

}
