package com.doston.todoapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainMenuScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("active") },
                    icon = { androidx.compose.material3.Icon(painterResource(R.drawable.checklist), contentDescription = null) },
                    label = { Text("Checklists") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("templates") },
                    icon = { androidx.compose.material3.Icon(painterResource(R.drawable.template), contentDescription = null) },
                    label = { Text("Templates") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("archive") },
                    icon = { androidx.compose.material3.Icon(painterResource(R.drawable.archive), contentDescription = null) },
                    label = { Text("Archive") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("settings") },
                    icon = { androidx.compose.material3.Icon(painterResource(R.drawable.setting), contentDescription = null) },
                    label = { Text("Settings") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = { navController.navigate("active") }, modifier = Modifier.fillMaxWidth()) {
                Text("Checklists")
            }
            Button(onClick = { navController.navigate("templates") }, modifier = Modifier.fillMaxWidth()) {
                Text("Templates")
            }
            Button(onClick = { navController.navigate("archive") }, modifier = Modifier.fillMaxWidth()) {
                Text("Archive")
            }
            Button(onClick = { navController.navigate("settings") }, modifier = Modifier.fillMaxWidth()) {
                Text("Settings")
            }
        }
    }
}

@Composable
fun ActiveChecklistScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val checklists by viewModel.checklists.collectAsState()

    Scaffold(bottomBar = {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { navController.navigate("create") }) { Text("New Checklist") }
            Button(onClick = { navController.navigate("archive") }) { Text("Archive") }
            Button(onClick = { navController.navigate("templates") }) { Text("Templates") }
        }
    }) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            itemsIndexed(checklists) { index, checklist ->
                ListItem(
                    headlineContent = { Text(checklist.title) },
                    supportingContent = {
                        LinearProgressIndicator(progress = checklist.items.size / 5f)
                    },
                    trailingContent = {
                        IconButton(onClick = { /* archive logic if needed */ }) {
                            Icon(painterResource(R.drawable.archive), contentDescription = null)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ArchiveScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val checklists by viewModel.checklists.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(Modifier.weight(1f)) {
            items(checklists) { checklist ->
                ListItem(headlineContent = { Text(checklist.title) })
            }
        }
        Button(onClick = { viewModel.clearArchive() }, modifier = Modifier.fillMaxWidth()) {
            Text("Clear All")
        }
    }
}
// == CreateChecklistScreen.kt ==
@Composable
fun CreateChecklistScreen(navController: NavController, viewModel: ChecklistViewModel) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = viewModel.newChecklistTitle.value, onValueChange = { viewModel.newChecklistTitle.value = it }, label = { Text("Checklist Name") })
        Spacer(Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(viewModel.newChecklistItems.size) { index ->
                OutlinedTextField(
                    value = viewModel.newChecklistItems[index],
                    onValueChange = { viewModel.newChecklistItems[index] = it },
                    label = { Text("Item ${index + 1}") }
                )
            }
        }
        Button(onClick = { viewModel.newChecklistItems.add("") }) { Text("Add Item") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            viewModel.addChecklist(viewModel.newChecklistTitle.value, viewModel.newChecklistItems)
            viewModel.newChecklistTitle.value = ""
            viewModel.newChecklistItems.clear()
            navController.popBackStack()
        }, modifier = Modifier.fillMaxWidth()) { Text("Save") }
    }
}

@Composable
fun TemplateScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val checklists by viewModel.checklists.collectAsState()

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        items(checklists) { template ->
            ListItem(
                headlineContent = { Text(template.title) },
                supportingContent = { Text(template.items.joinToString()) },
                trailingContent = {
                    Button(onClick = {
                        viewModel.addChecklist(template.title, template.items)
                        navController.popBackStack()
                    }) { Text("Use Template") }
                }
            )
        }
        item {
            Button(onClick = { navController.navigate("create") }, modifier = Modifier.fillMaxWidth()) {
                Text("Create Custom")
            }
        }
    }
}


// == SettingsScreen.kt ==
@Composable
fun SettingsScreen(navController: NavController, viewModel: ChecklistViewModel) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Theme", style = MaterialTheme.typography.headlineSmall)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = viewModel.themeDark.value, onClick = { viewModel.themeDark.value = true })
            Text("Dark")
            RadioButton(selected = !viewModel.themeDark.value, onClick = { viewModel.themeDark.value = false })
            Text("Light")
        }
        Button(onClick = { viewModel.toggleTheme() }, modifier = Modifier.fillMaxWidth()) { Text("Apply") }
    }
}
