package com.doston.todoapp.main


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doston.todoapp.R
import com.doston.todoapp.database.ChecklistViewModel
import com.doston.todoapp.navigation.MainNav
import com.doston.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         window.statusBarColor = ContextCompat.getColor(this, R.color.background)
window.navigationBarColor= ContextCompat.getColor(this, R.color.background)
        setContent {
            ToDoAppTheme  {
                val viewModel: ChecklistViewModel = viewModel()
                MainNav(this,viewModel)
            }
        }
    }
}