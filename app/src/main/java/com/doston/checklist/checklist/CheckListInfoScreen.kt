// Updated CheckListInfoScreen.kt
package com.doston.checklist.checklist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.doston.checklist.R
import com.doston.checklist.data.Checklist
import com.doston.checklist.database.ChecklistViewModel
import kotlinx.serialization.json.Json

@Composable
fun CheckListInfoScreen(
    viewModel: ChecklistViewModel,
    navController: NavHostController
) {
    val isDarkTheme by viewModel.themeDark.collectAsState()
    val backgroundColor = if (isDarkTheme) Color(0xFF1C1C1C) else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val cardColor = if (isDarkTheme) Color(0xFF2C2C2C) else Color.White
    val accentColor = if (isDarkTheme) Color(0xFFFF5722) else Color(0xFF6200EE)

    val checklistJson = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<String>("check_json")

    val checklist = remember(checklistJson) {
        checklistJson?.let {
            runCatching { Json.decodeFromString<Checklist>(it) }.getOrNull()
        }
    }

    if (checklist == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(R.string.checklist_not_found), color = textColor)
        }
        return
    }

    var checkedStates by remember {
        mutableStateOf(
            checklist.items.mapIndexed { index, item ->
                item to checklist.completedIndices.contains(index)
            }.toMap()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = checklist.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.weight(1f)
            )

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${checklist.getCompletedCount()}/${checklist.items.size}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = accentColor
                )
                LinearProgressIndicator(
                    progress = { checklist.getCompletionProgress() },
                    color = accentColor,
                    modifier = Modifier
                        .width(100.dp)
                        .height(8.dp)
                        .padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            itemsIndexed(checklist.items) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = checkedStates[item] == true,
                            onCheckedChange = { isChecked ->
                                checkedStates = checkedStates.toMutableMap().apply {
                                    this[item] = isChecked
                                }

                                viewModel.toggleItemCompletion(checklist.id, index, isChecked)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = accentColor,
                                uncheckedColor = if (isDarkTheme) Color.Gray else Color.DarkGray,
                                checkmarkColor = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = item,
                            fontSize = 16.sp,
                            color = if (checkedStates[item] == true) textColor.copy(alpha = 0.5f) else textColor,
                            textDecoration = if (checkedStates[item] == true)
                                TextDecoration.LineThrough else TextDecoration.None
                        )
                    }
                }
            }
        }
    }
}