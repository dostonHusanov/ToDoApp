package com.doston.checklist.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.toArgb
import com.doston.checklist.database.ChecklistViewModel
import com.doston.checklist.navigation.MainNav
import com.doston.checklist.ui.theme.MainColor
import com.doston.checklist.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val viewModel: ChecklistViewModel = viewModel()
            val isDarkTheme by viewModel.themeDark.collectAsState()
            ToDoAppTheme(darkTheme =isDarkTheme ) {

                val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)


                window.statusBarColor = backgroundColor.toArgb()
                window.navigationBarColor = backgroundColor.toArgb()

                MainNav(this@MainActivity, viewModel)
            }
        }
    }
}