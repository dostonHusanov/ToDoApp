package com.doston.todoapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.toArgb
import com.doston.todoapp.R
import com.doston.todoapp.database.ChecklistViewModel
import com.doston.todoapp.navigation.MainNav
import com.doston.todoapp.ui.theme.ButtonColor
import com.doston.todoapp.ui.theme.MainColor
import com.doston.todoapp.ui.theme.ToDoAppTheme
import com.doston.todoapp.ui.theme.WhiteColor
import com.doston.todoapp.ui.theme.YellowColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ToDoAppTheme {
                val viewModel: ChecklistViewModel = viewModel()
                val isDarkTheme by viewModel.themeDark.collectAsState()

                val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)


                window.statusBarColor = backgroundColor.toArgb()
                window.navigationBarColor = backgroundColor.toArgb()

                MainNav(this@MainActivity, viewModel)
            }
        }
    }
}