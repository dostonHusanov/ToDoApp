package com.doston.todoapp


import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChecklistViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = ChecklistDbHelper(application)

    private val _checklists = MutableStateFlow<List<Checklist>>(emptyList())
    val checklists = _checklists.asStateFlow()

    val newChecklistTitle = mutableStateOf("")
    val newChecklistItems = mutableStateListOf<String>()
    val themeDark = mutableStateOf(true)

    init {
        loadChecklists()
    }

    private fun loadChecklists() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dbHelper.getAllChecklists()
            _checklists.value = list
        }
    }

    fun addChecklist(name: String, items: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.insertChecklist(name, items)
            loadChecklists()
        }
    }

    fun clearArchive() {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.deleteAll()
            loadChecklists()
        }
    }

    fun toggleTheme() {
        themeDark.value = !themeDark.value
    }
}