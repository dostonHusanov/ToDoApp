package com.doston.checklist.archive

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doston.checklist.R
import com.doston.checklist.database.ChecklistViewModel
import com.doston.checklist.ui.theme.ButtonColor
import com.doston.checklist.ui.theme.MainColor
import com.doston.checklist.ui.theme.WhiteColor
import com.doston.checklist.ui.theme.YellowColor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val checklists by viewModel.archivedChecklists.collectAsState()
    val isDarkTheme by viewModel.themeDark.collectAsState()

    var showClearAllDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var checklistToDelete by remember { mutableStateOf<Int?>(null) }

    val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) WhiteColor else Color.Black
    val cardColor = if (isDarkTheme) ButtonColor else Color.White
    val accentColor = if (isDarkTheme) YellowColor else Color(0xFF6200EE)
    val dividerColor = if (isDarkTheme) ButtonColor else Color(0xFFE0E0E0)
    Scaffold(
        topBar = {
            Column {
                Text(
                    "Archived Checklists",
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    color = dividerColor
                )
            }
        },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 10.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            if (checklists.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.delete), // You might want a different icon
                            contentDescription = "No archived items",
                            tint = textColor.copy(alpha = 0.5f),
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "No archived checklists",
                            color = textColor.copy(alpha = 0.7f),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            "Completed checklists will appear here",
                            color = textColor.copy(alpha = 0.5f),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(checklists) { checklist ->
                        ArchiveItem(
                            title = checklist.title,
                            date = checklist.createdDate,
                            competedItem = "${checklist.getCompletedCount()}/${checklist.items.size}",
                            onClick = {
                                val checklistJson = Json.encodeToString(checklist)
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "archive_json",
                                    checklistJson
                                )
                                navController.navigate("archive_info")
                            },
                            onDelete = {
                                checklistToDelete = checklist.id
                                showDeleteDialog = true
                            }, cardColor, textColor, accentColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    shape = RoundedCornerShape(15.dp),
                    onClick = { showClearAllDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    enabled = checklists.isNotEmpty()
                ) {
                    Text(
                        "Clear All",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                checklistToDelete = null
            },
            title = {
                Text(
                    "Delete Checklist",
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    "Are you sure you want to permanently delete this archived checklist?",
                    color = textColor
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        checklistToDelete?.let { id ->
                            viewModel.deleteArchivedList(id)
                        }
                        showDeleteDialog = false
                        checklistToDelete = null
                    }
                ) {
                    Text("Delete", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        checklistToDelete = null
                    }
                ) {
                    Text("Cancel", color = textColor)
                }
            },
            containerColor = cardColor
        )
    }

    if (showClearAllDialog) {
        AlertDialog(
            onDismissRequest = { showClearAllDialog = false },
            title = {
                Text(
                    "Clear All Archives",
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    "Are you sure you want to permanently delete all ${checklists.size} archived checklists? This action cannot be undone.",
                    color = textColor
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearArchive()
                        showClearAllDialog = false
                    }
                ) {
                    Text("Clear All", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showClearAllDialog = false }
                ) {
                    Text("Cancel", color = textColor)
                }
            },
            containerColor = cardColor
        )
    }
}

@Composable
fun ArchiveItem(
    title: String,
    date: String,
    competedItem: String,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    cardColor: Color, textColor: Color, accentColor: Color
) {




    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 14.dp)
            .clickable { onClick() }, shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = "Completed: $date",
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.7f)
                )
                Text(
                    text = "$competedItem completed",
                    fontSize = 14.sp,
                    color = accentColor,
                    fontWeight = FontWeight.Medium
                )
            }

            Icon(
                painter = painterResource(R.drawable.delete),
                contentDescription = "Delete",
                tint = Color.Red,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onDelete() }
                    .padding(start = 12.dp)
            )
        }
    }
}
