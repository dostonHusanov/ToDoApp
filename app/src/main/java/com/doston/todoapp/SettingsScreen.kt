package com.doston.todoapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.doston.todoapp.database.ChecklistViewModel

// == SettingsScreen.kt ==
@Composable
fun SettingsScreen(navController: NavController, viewModel: ChecklistViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Theme", style = MaterialTheme.typography.headlineSmall)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = viewModel.themeDark.value,
                onClick = { viewModel.themeDark.value = true })
            Text("Dark")
            RadioButton(
                selected = !viewModel.themeDark.value,
                onClick = { viewModel.themeDark.value = false })
            Text("Light")
        }
        Button(
            onClick = { viewModel.toggleTheme() },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Apply") }
    }
}