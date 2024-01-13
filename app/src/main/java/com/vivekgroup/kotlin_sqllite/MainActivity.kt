package com.vivekgroup.kotlin_sqllite

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.vivekgroup.kotlin_sqllite.DBHelper.DBHelper

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var resultTextView: TextView

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DBHelper(this)
        resultTextView = findViewById(R.id.resultTextView)

        val addButton: Button = findViewById(R.id.addButton)
        val queryButton: Button = findViewById(R.id.queryButton)
        val updateButton: Button = findViewById(R.id.updateButton)
        val deleteButton: Button = findViewById(R.id.deleteButton)

        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val idEditText: EditText = findViewById(R.id.idEditText)

        addButton.setOnClickListener {
            val name =nameEditText.text.toString()
            val insertedID =dbHelper.insert(name)
            updateUI("INserted with id : $insertedID")
        }

        queryButton.setOnClickListener {
            val cursor: Cursor = dbHelper.readData()
            val data = StringBuilder()
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME))
                data.append("ID: $id, Name: $name\n")
            }
            updateUI(data.toString())
            cursor.close()
        }

        updateButton.setOnClickListener {
            val id = idEditText.text.toString().toLong()
            val newName = nameEditText.text.toString()
            val updatedRows = dbHelper.updateData(id, newName)
            updateUI("Updated $updatedRows rows")
        }

        deleteButton.setOnClickListener {
            val id = idEditText.text.toString().toLong()
            val deletedRows = dbHelper.deleteData(id)
            updateUI("Deleted $deletedRows rows")
        }




}
    private fun updateUI(message: String){
        resultTextView.text=message
    }
}