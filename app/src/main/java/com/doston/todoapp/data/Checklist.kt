package com.doston.todoapp.data


data class Checklist(
    val id: Int = 0,
    val title: String,
    val items: List<String>,
    val createdDate:String,val isArchived: Boolean = false


)