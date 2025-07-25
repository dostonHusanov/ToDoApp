package com.doston.checklist.archive

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.doston.checklist.data.Checklist
import com.doston.checklist.database.ChecklistViewModel
import com.doston.checklist.ui.theme.ButtonColor
import com.doston.checklist.ui.theme.MainColor
import com.doston.checklist.ui.theme.WhiteColor
import com.doston.checklist.ui.theme.YellowColor
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveInfoScreen(
    viewModel: ChecklistViewModel,
    navController: NavHostController
) {
    val isDarkTheme by viewModel.themeDark.collectAsState()

    // Dynamic theme colors
    val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) WhiteColor else Color.Black
    val cardColor = if (isDarkTheme) ButtonColor else Color.White
    val accentColor = if (isDarkTheme) YellowColor else Color(0xFF6200EE)
    val dividerColor = if (isDarkTheme) ButtonColor else Color(0xFFE0E0E0)

    // Get the archived checklist data from navigation
    val checklistJson = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<String>("archive_json")

    val checklist = remember(checklistJson) {
        checklistJson?.let {
            runCatching { Json.decodeFromString<Checklist>(it) }.getOrNull()
        }
    }

    if (checklist == null) {
        Scaffold(
            topBar = {
                Text(
                    "Archive Details",
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            containerColor = backgroundColor
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Archived checklist not found.",
                    color = textColor,
                    fontSize = 16.sp
                )
            }
        }
        return
    }

    Scaffold(
        topBar = {
            Column {
                Text(
                    "Archive Details",
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
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
                .padding(16.dp)
        ) {
            // Header with title, date and completion status
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = checklist.title,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Created: ${checklist.createdDate}",
                                fontSize = 14.sp,
                                color = textColor.copy(alpha = 0.7f)
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "COMPLETED",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = accentColor,
                                modifier = Modifier
                                    .background(
                                        accentColor.copy(alpha = 0.1f),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${checklist.getCompletedCount()}/${checklist.items.size}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = accentColor
                            )
                            LinearProgressIndicator(
                                progress = { checklist.getCompletionProgress() },
                                color = accentColor,
                                trackColor = accentColor.copy(alpha = 0.3f),
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(8.dp)
                                    .padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Items list (read-only)
            Text(
                text = "Checklist Items (${checklist.items.size})",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(checklist.items) { index, item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            // Read-only checkbox indicator
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        color = if (checklist.completedIndices.contains(index))
                                            accentColor else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .background(
                                        color = Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (checklist.completedIndices.contains(index)) {
                                    Text(
                                        text = "✓",
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                } else {
                                    // Empty checkbox border
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(
                                                Color.Transparent,
                                                RoundedCornerShape(4.dp)
                                            )
                                            .run {
                                                // Add border using composed modifier
                                                this.background(
                                                    Color.Transparent,
                                                    RoundedCornerShape(4.dp)
                                                )
                                            }
                                    ) {
                                        // Border effect
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(
                                                    Color.Transparent,
                                                    RoundedCornerShape(4.dp)
                                                )
                                        ) {
                                            // Create border effect manually
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(1.dp)
                                                    .background(
                                                        cardColor,
                                                        RoundedCornerShape(3.dp)
                                                    )
                                            )
                                        }
                                        // Outer border
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(
                                                    accentColor.copy(alpha = 0.5f),
                                                    RoundedCornerShape(4.dp)
                                                )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(2.dp)
                                                    .background(
                                                        cardColor,
                                                        RoundedCornerShape(2.dp)
                                                    )
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = item,
                                fontSize = 16.sp,
                                color = if (checklist.completedIndices.contains(index))
                                    textColor.copy(alpha = 0.6f) else textColor,
                                textDecoration = if (checklist.completedIndices.contains(index))
                                    TextDecoration.LineThrough else TextDecoration.None,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}