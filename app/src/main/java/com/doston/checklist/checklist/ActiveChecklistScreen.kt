package com.doston.checklist.checklist

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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

@Composable
fun ActiveChecklistScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val checklists by viewModel.checklists.collectAsState()
    val isDarkTheme by viewModel.themeDark.collectAsState()

    val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) WhiteColor else Color.Black
    val cardColor = if (isDarkTheme) ButtonColor else Color.White
    val accentColor = if (isDarkTheme) YellowColor else Color(0xFF6200EE)
    val dividerColor = if (isDarkTheme) ButtonColor else Color(0xFFE0E0E0)

    Scaffold(
        topBar = {
            Text(
                text = stringResource(R.string.active_checklists),
                color = textColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 15.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        floatingActionButton = {
            Card(
                modifier = Modifier
                    .size(70.dp)
                    .padding(end = 16.dp, bottom = 16.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = accentColor,
                    contentColor = WhiteColor
                ),
                onClick = { navController.navigate("create") }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add),
                    tint = WhiteColor,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }
        },
        containerColor = backgroundColor
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = dividerColor
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyColumn {
                val sortedChecklists = checklists.sortedByDescending { it.createdDate }
                itemsIndexed(sortedChecklists) { _, checklist ->
                    ChecklistItemCard(
                        id = checklist.id,
                        title = checklist.title,
                        createdDate = checklist.createdDate,
                        viewModel = viewModel,
                        progress = checklist.getCompletionProgress(),
                        completedCount = checklist.getCompletedCount(),
                        totalCount = checklist.items.size,
                        cardColor = cardColor,
                        textColor = textColor,
                        accentColor = accentColor,
                        onclick = {
                            val checklistJson = Json.encodeToString(checklist)
                            navController.currentBackStackEntry?.savedStateHandle?.set("check_json", checklistJson)
                            navController.navigate("info")
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun ChecklistItemCard(
    id: Int = 0,
    title: String,
    createdDate: String,
    progress: Float,
    completedCount: Int,
    totalCount: Int,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    viewModel: ChecklistViewModel,
    cardColor: Color,
    textColor: Color,
    accentColor: Color,
    onclick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 6.dp
        ),
        onClick = { onclick() },
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(15.dp)
        ) {
            val context = LocalContext.current
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(2.dp, accentColor, RoundedCornerShape(4.dp))
                    .background(
                        color = if (isChecked) accentColor else Color.Transparent,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        onCheckedChange(!isChecked)
                        viewModel.archiveChecklist(id)
                        Toast
                            .makeText(context, "Checklist archived", Toast.LENGTH_SHORT)
                            .show()
                    },
                contentAlignment = Alignment.Center
            ) {
                if (isChecked) {
                    Text(
                        text = "✓",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = title,
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = createdDate,
                        color = textColor.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Row (verticalAlignment = Alignment.CenterVertically)  {
                        LinearProgressIndicator(
                            progress = { progress },
                            color = accentColor,
                            modifier = Modifier
                                .width(80.dp)
                                .height(6.dp)
                                .clip(RoundedCornerShape(4.dp))
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$completedCount/$totalCount",
                            color = textColor.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}