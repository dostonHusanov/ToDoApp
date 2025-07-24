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
    // Helper function to calculate completion progress
    fun getCompletionProgress(): Float {
        return if (items.isEmpty()) 0f else completedIndices.size.toFloat() / items.size.toFloat()
    }

    // Helper function to get completed items count
    fun getCompletedCount(): Int = completedIndices.size
}