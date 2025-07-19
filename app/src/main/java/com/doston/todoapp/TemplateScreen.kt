package com.doston.todoapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.doston.todoapp.database.ChecklistViewModel

@Composable
fun TemplateScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val checklists by viewModel.checklists.collectAsState()

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(checklists) { template ->
            ListItem(
                headlineContent = { Text(template.title) },
                supportingContent = { Text(template.items.joinToString()) },
                trailingContent = {
                    Button(onClick = {
                        viewModel.addChecklist(template.title, template.items, template.createdDate)
                        navController.popBackStack()
                    }) { Text("Use Template") }
                }
            )
        }
        item {
            Button(
                onClick = { navController.navigate("create") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Custom")
            }
        }
    }
}
