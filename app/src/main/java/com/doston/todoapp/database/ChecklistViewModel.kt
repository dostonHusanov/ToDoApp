package com.doston.todoapp.database


import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.doston.todoapp.data.Checklist
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

    // Updated theme management with StateFlow for better Compose integration
    private val _themeDark = MutableStateFlow(true)
    val themeDark = _themeDark.asStateFlow()

    // Keep the old mutableStateOf for backward compatibility if needed elsewhere
    val themeDarkState = mutableStateOf(true)

    var editingIndex = mutableStateOf(-1)
    init {
        loadChecklists()
    }
    var checklistTitle = mutableStateOf("")
    var newItemText = mutableStateOf("")

    fun addItem() {
        if (newItemText.value.isNotBlank()) {
            newChecklistItems.add(newItemText.value)
            newItemText.value = ""
        }
    }

    fun clearAll() {
        checklistTitle.value = ""
        newItemText.value = ""
        newChecklistItems.clear()
    }

    private val _archivedChecklists = MutableStateFlow<List<Checklist>>(emptyList())
    val archivedChecklists = _archivedChecklists.asStateFlow()

    private fun loadChecklists() {
        viewModelScope.launch(Dispatchers.IO) {
            val all = dbHelper.getAllChecklists()
            _checklists.value = all.filter { !it.isArchived }
            _archivedChecklists.value = all.filter { it.isArchived }
        }
    }
    fun moveItem(from: Int, to: Int) {
        val item = newChecklistItems.removeAt(from)
        newChecklistItems.add(to, item)
    }

    fun startEditing(index: Int) {
        editingIndex.value = index
    }

    fun stopEditing() {
        editingIndex.value = -1
    }

    fun updateItem(index: Int, newValue: String) {
        newChecklistItems[index] = newValue
    }

    fun removeItem(index: Int) {
        newChecklistItems.removeAt(index)
    }

    fun archiveChecklist(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.archiveChecklist(id)
            loadChecklists()
        }
    }

    fun clearArchive() {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.clearAllArchived()
            loadChecklists()
        }
    }

    fun deleteArchivedList(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.deleteArchivedById(id)
            loadChecklists()
        }
    }

    fun addChecklist(name: String, items: List<String>, createdDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.insertChecklist(name, items, createdDate)
            loadChecklists()
        }
    }

    // Updated theme methods
    fun toggleTheme() {
        val newValue = !_themeDark.value
        _themeDark.value = newValue
        themeDarkState.value = newValue
    }

    fun setTheme(isDark: Boolean) {
        _themeDark.value = isDark
        themeDarkState.value = isDark
    }

    fun getCurrentTheme(): Boolean {
        return _themeDark.value
    }
}