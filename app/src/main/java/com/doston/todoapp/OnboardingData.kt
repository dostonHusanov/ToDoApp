package com.doston.todoapp

class OnboardingData(val image: Int, val title: String, val desc: String)

val listData = listOf(
    OnboardingData(
        image = R.drawable.task_yellow,
        title = "Organize Your Tasks",
        desc = "Create and manage checklists with ease"
    ),
    OnboardingData(
        image = R.drawable.settings,
        title = "Customize Your Experience",
        desc = "Use templates and switch themes"
    )
)