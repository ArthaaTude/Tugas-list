package com.example.tugaslist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ListAdapter
    private lateinit var data: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val edtItem = findViewById<EditText>(R.id.edtItem)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        data = mutableListOf(
            "Apel",
            "Jeruk",
            "Mangga",
            "Pisang",
            "Semangka"
        )

        adapter = ListAdapter(data) { position: Int ->
            val input = EditText(this)
            input.setText(data[position])

            AlertDialog.Builder(this)
                .setTitle("Edit / Hapus")
                .setView(input)
                .setPositiveButton("Update") { _, _ ->
                    data[position] = input.text.toString()
                    adapter.notifyItemChanged(position)
                }
                .setNegativeButton("Hapus") { _, _ ->
                    data.removeAt(position)

                    if (data.isEmpty()) {
                        adapter.notifyDataSetChanged()
                    } else {
                        adapter.notifyItemRemoved(position)
                    }
                }
                .setNeutralButton("Batal", null)
                .show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAdd.setOnClickListener {
            val text = edtItem.text.toString()
            if (text.isNotEmpty()) {
                data.add(text)
                adapter.notifyItemInserted(data.size - 1)
                edtItem.text.clear()
            }
        }
    }
}