// Add this function to ChecklistViewModel.kt
package com.doston.checklist.database

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.doston.checklist.data.Checklist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChecklistViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = ChecklistDbHelper(application)
    private val sharedPreferences = application.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    private val _themeDark = MutableStateFlow(
        sharedPreferences.getBoolean("is_dark_theme", true)
    )
    private val _checklists = MutableStateFlow<List<Checklist>>(emptyList())
    val checklists = _checklists.asStateFlow()

    val newChecklistItems = mutableStateListOf<String>()

    val themeDark = _themeDark.asStateFlow()
    val themeDarkState = mutableStateOf(true)

    var editingIndex = mutableStateOf(-1)
    var checklistTitle = mutableStateOf("")
    var newItemText = mutableStateOf("")

    private val _archivedChecklists = MutableStateFlow<List<Checklist>>(emptyList())
    val archivedChecklists = _archivedChecklists.asStateFlow()

    private val _selectedChecklist = MutableStateFlow<Checklist?>(null)
    val selectedChecklist = _selectedChecklist.asStateFlow()

    init {
        loadChecklists()
    }

    // NEW: Toggle item completion
    fun toggleItemCompletion(checklistId: Int, itemIndex: Int, isCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.updateItemCompletion(checklistId, itemIndex, isCompleted)
            loadChecklists()
        }
    }

    fun selectChecklist(checklist: Checklist) {
        _selectedChecklist.value = checklist
    }

    fun clearSelectedChecklist() {
        _selectedChecklist.value = null
    }

    fun addItem() {
        if (newItemText.value.isNotBlank()) {
            newChecklistItems.add(newItemText.value.trim())
            newItemText.value = ""
        }
    }

    fun clearAll() {
        checklistTitle.value = ""
        newItemText.value = ""
        newChecklistItems.clear()
    }

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

    fun setTheme(isDark: Boolean) {
        _themeDark.value = isDark
        themeDarkState.value = isDark

        sharedPreferences.edit()
            .putBoolean("is_dark_theme", isDark)
            .apply()
    }
}