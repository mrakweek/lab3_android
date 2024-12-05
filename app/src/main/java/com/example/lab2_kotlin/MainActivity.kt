package com.example.lab2_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShoppingAdapter
    private val viewModel = ShoppingViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ShoppingAdapter(this, viewModel.items.value ?: listOf(), viewModel)
        recyclerView.adapter = adapter

        viewModel.items.observe(this) { items ->
            adapter.updateItems(items)
        }

        findViewById<Button>(R.id.button_add).setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit, null)
        val editText = dialogView.findViewById<EditText>(R.id.edit_text_name)

        AlertDialog.Builder(this)
            .setTitle("Добавить элемент")
            .setView(dialogView)
            .setPositiveButton("Добавить") { _, _ ->
                val name = editText.text.toString()
                if (name.isNotEmpty()) {
                    viewModel.addItem(name)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}
