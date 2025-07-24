// Updated ChecklistDbHelper.kt
package com.doston.checklist.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.doston.checklist.data.Checklist

class ChecklistDbHelper(context: Context) : SQLiteOpenHelper(context, "checklists.db", null, 4) { // Increment version

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
    CREATE TABLE checklists (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        title TEXT,
        items TEXT,
        completed_items TEXT,
        created_date TEXT,
        is_archived INTEGER DEFAULT 0
    )
""")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 4) {
            // Add completed_items column if upgrading from older version
            db.execSQL("ALTER TABLE checklists ADD COLUMN completed_items TEXT DEFAULT ''")
        }
    }

    fun insertChecklist(title: String, items: List<String>, createdDate: String, isArchived: Boolean = false) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("items", items.joinToString("|"))
            put("completed_items", "") // Initially no items are completed
            put("created_date", createdDate)
            put("is_archived", if (isArchived) 1 else 0)
        }
        db.insert("checklists", null, values)
        db.close()
    }

    fun updateItemCompletion(checklistId: Int, itemIndex: Int, isCompleted: Boolean) {
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT completed_items FROM checklists WHERE id = ?", arrayOf(checklistId.toString()))

        if (cursor.moveToFirst()) {
            val completedItemsStr = cursor.getString(0) ?: ""
            val completedIndices = if (completedItemsStr.isEmpty()) {
                mutableSetOf<Int>()
            } else {
                completedItemsStr.split(",").mapNotNull { it.toIntOrNull() }.toMutableSet()
            }

            if (isCompleted) {
                completedIndices.add(itemIndex)
            } else {
                completedIndices.remove(itemIndex)
            }

            val newCompletedStr = completedIndices.joinToString(",")

            val values = ContentValues().apply {
                put("completed_items", newCompletedStr)
            }
            db.update("checklists", values, "id = ?", arrayOf(checklistId.toString()))
        }
        cursor.close()
        db.close()
    }

    fun deleteArchivedById(id: Int) {
        val db = writableDatabase
        db.delete(
            "checklists",
            "id = ? AND is_archived = 1",
            arrayOf(id.toString())
        )
        db.close()
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
                val completedItemsStr = cursor.getString(cursor.getColumnIndexOrThrow("completed_items")) ?: ""
                val completedIndices = if (completedItemsStr.isEmpty()) {
                    emptySet()
                } else {
                    completedItemsStr.split(",").mapNotNull { it.toIntOrNull() }.toSet()
                }
                val createdDate = cursor.getString(cursor.getColumnIndexOrThrow("created_date"))
                val isArchived = cursor.getInt(cursor.getColumnIndexOrThrow("is_archived")) == 1
                list.add(Checklist(id, title, items, createdDate, isArchived, completedIndices))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return list
    }

    fun updateChecklist(id: Int, title: String, items: List<String>) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("items", items.joinToString("|"))
        }
        db.update("checklists", values, "id = ?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteAll(id: Int) {
        val db = writableDatabase
        db.delete("checklists", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    fun archiveChecklist(id: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("is_archived", 1)
        }
        db.update("checklists", values, "id = ?", arrayOf(id.toString()))
        db.close()
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
                val completedItemsStr = cursor.getString(cursor.getColumnIndexOrThrow("completed_items")) ?: ""
                val completedIndices = if (completedItemsStr.isEmpty()) {
                    emptySet()
                } else {
                    completedItemsStr.split(",").mapNotNull { it.toIntOrNull() }.toSet()
                }
                val createdDate = cursor.getString(cursor.getColumnIndexOrThrow("created_date"))
                list.add(Checklist(id, title, items, createdDate, true, completedIndices))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    fun clearAllArchived() {
        val db = writableDatabase
        db.delete("checklists", "is_archived = 1", null)
        db.close()
    }
}