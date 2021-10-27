package com.example.noteappfull

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteretrieve.adapteritem
import com.example.noteretrieve.dbhelper

class MainActivity : AppCompatActivity() {
    lateinit var bt: Button
    lateinit var et: EditText
    lateinit var Rc: RecyclerView
    lateinit var Inputs: ArrayList<NoteInfo>
    lateinit var db: dbhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = dbhelper(this)
        Rc = findViewById(R.id.rv)
        Inputs = ArrayList()
        Rc.adapter = adapteritem(this, Inputs)
        Rc.layoutManager = LinearLayoutManager(this)
        bt = findViewById(R.id.b)
        et = findViewById(R.id.et)
        bt.setOnClickListener {
            AddToDB()

        }
    }

    fun AddToDB() {
        db.addNote(NoteInfo(0, et.text.toString()))
        et.text.clear()
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
        updateRc()

    }

    fun updateRc() {
        Rc.adapter = adapteritem(this, getItemsList())
        Rc.layoutManager = LinearLayoutManager(this)
    }

    private fun getItemsList(): ArrayList<NoteInfo> {
        return db.viewNotes()
    }

    fun updateNote(index: Int, newNote: String) {
        db.updatenote(NoteInfo(index, newNote))
        updateRc()
    }

    fun DeleteNote(id: Int) {
        db.deleteNote(NoteInfo(id, ""))
        updateRc()
    }

    fun dialog(ind:Int): String {
        var newNote = ""
        val dialogBuilder = AlertDialog.Builder(this)
        val input = EditText(this)
        dialogBuilder.setMessage("Enter new note:")
            .setPositiveButton("Submit", DialogInterface.OnClickListener { dialog, id ->
                updateNote(ind, input.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(input)
        alert.show()
        return newNote
    }
}