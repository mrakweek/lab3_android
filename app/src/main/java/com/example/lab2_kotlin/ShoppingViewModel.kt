package com.example.lab2_kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class ShoppingItem(
    val id: Int,
    var name: String,
    val imageRes: Int
)

class ShoppingViewModel : ViewModel() {
    private val _items = MutableLiveData<List<ShoppingItem>>()
    val items: LiveData<List<ShoppingItem>> get() = _items

    private var nextId = 1
    private val imageList = listOf(
        R.drawable.ic_launcher_foreground
    )

    init {
        _items.value = mutableListOf()
    }

    fun addItem(name: String) {
        val newItem = ShoppingItem(
            id = nextId++,
            name = name,
            imageRes = imageList.random()
        )
        val currentList = _items.value.orEmpty().toMutableList()
        currentList.add(newItem)
        _items.value = currentList
    }

    fun removeItem(item: ShoppingItem) {
        val currentList = _items.value.orEmpty().toMutableList()
        currentList.remove(item)
        _items.value = currentList
    }

    fun editItem(item: ShoppingItem, newName: String) {
        val currentList = _items.value.orEmpty().toMutableList()
        val index = currentList.indexOfFirst { it.id == item.id }
        if (index != -1) {
            currentList[index] = item.copy(name = newName)
            _items.value = currentList
        }
    }
}
