// Updated Checklist data class
package com.doston.checklist.data

import kotlinx.serialization.Serializable

@Serializable
data class Checklist(
    val id: Int,
    val title: String,
    val items: List<String>,
    val createdDate: String,
    val isArchived: Boolean = false,
    val completedIndices: Set<Int> = emptySet()
) {
    fun getCompletionProgress(): Float {
        return if (items.isEmpty()) 0f else completedIndices.size.toFloat() / items.size.toFloat()
    }

    fun getCompletedCount(): Int = completedIndices.size
}