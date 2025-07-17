package com.doston.todoapp


data class Checklist(
    val id: Int = 0,
    val title: String,
    val items: List<String>
)