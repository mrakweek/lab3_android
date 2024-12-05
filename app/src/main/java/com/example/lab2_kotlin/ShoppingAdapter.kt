package com.example.lab2_kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class ShoppingAdapter(
    private val context: Context,
    private var items: List<ShoppingItem>,
    private val viewModel: ShoppingViewModel
) : RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>() {

    inner class ShoppingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNumber: TextView = itemView.findViewById(R.id.text_view_number)
        val imageViewIcon: ImageView = itemView.findViewById(R.id.image_view_icon)
        val textViewName: TextView = itemView.findViewById(R.id.text_view_name)
        val buttonEdit: Button = itemView.findViewById(R.id.button_edit)
        val buttonDelete: Button = itemView.findViewById(R.id.button_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val item = items[position]
        holder.textViewNumber.text = "${position + 1}."
        holder.imageViewIcon.setImageResource(item.imageRes)
        holder.textViewName.text = item.name

        holder.buttonDelete.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Удалить элемент")
                .setMessage("Вы уверены, что хотите удалить этот элемент?")
                .setPositiveButton("Да") { _, _ ->
                    viewModel.removeItem(item)
                }
                .setNegativeButton("Нет", null)
                .show()
        }

        holder.buttonEdit.setOnClickListener {
            showEditDialog(item)
        }
    }

    fun updateItems(newItems: List<ShoppingItem>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    private fun showEditDialog(item: ShoppingItem) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_edit, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.edit_text_name)
        editTextName.setText(item.name)

        AlertDialog.Builder(context)
            .setTitle("Редактировать элемент")
            .setView(dialogView)
            .setPositiveButton("Сохранить") { _, _ ->
                val newName = editTextName.text.toString().trim()
                if (newName.isNotEmpty()) {
                    viewModel.editItem(item, newName)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
}
