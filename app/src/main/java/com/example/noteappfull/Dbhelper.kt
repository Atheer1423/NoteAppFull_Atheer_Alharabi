package com.example.noteretrieve

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import android.widget.Toast
import com.example.noteappfull.NoteInfo

class dbhelper(context: Context) :SQLiteOpenHelper(context,"myDB.db",null,1) {
    var sqLiteDatabase: SQLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL("create table notes (id INTEGER PRIMARY KEY ,note text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS notes")
        onCreate(db)
    }

    fun addNote(Note: NoteInfo) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("note", Note.note)
        db.insert("notes", null, cv)
        db.close()
    }

    @SuppressLint("Range")
    fun viewNotes(): ArrayList<NoteInfo> {
        val noteList: ArrayList<NoteInfo> = ArrayList()
        val selectQuery = "SELECT * FROM notes"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var noteText: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                noteText = cursor.getString(cursor.getColumnIndex("note"))

                val note = NoteInfo(id, noteText)
                noteList.add(note)
            } while (cursor.moveToNext())
        }

        return noteList
    }

    fun deleteNote(note: NoteInfo) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("id", note.index)
        db.delete("notes", "id=${note.index}", null)
        db.close()
    }

    fun updatenote(newNote: NoteInfo) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("note", newNote.note)
        db.update("notes", cv, "id=${newNote.index}", null)
        db.close()

    }

}