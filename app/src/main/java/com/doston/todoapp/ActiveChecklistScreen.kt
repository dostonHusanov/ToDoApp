package com.doston.todoapp

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doston.todoapp.database.ChecklistViewModel
import com.doston.todoapp.ui.theme.ButtonColor
import com.doston.todoapp.ui.theme.MainColor
import com.doston.todoapp.ui.theme.WhiteColor
import com.doston.todoapp.ui.theme.YellowColor


@Composable
fun ActiveChecklistScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val checklists by viewModel.checklists.collectAsState()

    Scaffold(topBar = {
        Text(
            "Active Checklists",
            color = WhiteColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }, containerColor = MainColor, bottomBar = {
        Row(
            Modifier
                .fillMaxWidth()
                .background(ButtonColor)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .height(62.dp)
                    .width(80.dp)
                    .clickable { navController.navigate("create") }
                    .background(
                        shape = RoundedCornerShape(15.dp), color = YellowColor
                    )
                    .width(80.dp)) {
                Text("New", fontSize = 15.sp, color = Color.White, textAlign = TextAlign.Center)
                Text(
                    "Checklist", fontSize = 15.sp, color = Color.White, textAlign = TextAlign.Center
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .height(62.dp)
                    .width(80.dp)
                    .clickable { navController.navigate("archive") }
                    .background(
                        shape = RoundedCornerShape(15.dp), color = YellowColor
                    )
                    .width(80.dp)) {
                Text(
                    "Archive", fontSize = 15.sp, color = Color.White, textAlign = TextAlign.Center
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .height(62.dp)
                    .width(80.dp)
                    .clickable { navController.navigate("template") }
                    .background(
                        shape = RoundedCornerShape(15.dp), color = YellowColor
                    )
                    .width(80.dp)) {
                Text(
                    "Templates", fontSize = 15.sp, color = Color.White, textAlign = TextAlign.Center
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .height(62.dp)
                    .width(80.dp)
                    .clickable { navController.navigate("settings") }
                    .background(
                        shape = RoundedCornerShape(15.dp), color = YellowColor
                    )
                    .width(80.dp)) {
                Text(
                    "Settings", fontSize = 15.sp, color = Color.White, textAlign = TextAlign.Center
                )
            }
        }
    }) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            itemsIndexed(checklists) { index, checklist ->
                ChecklistItemCard(
                    id = checklist.id,
                    title = checklist.title,
                    createdDate = checklist.createdDate,
                    viewModel = viewModel,
                    progress = checklist.items.size / 5f
                )

            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ChecklistItemCard(
    id: Int = 0,
    title: String,
    createdDate: String,
    progress: Float,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    viewModel: ChecklistViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = ButtonColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(15.dp)
        ) {
            // Checkbox
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(2.dp, YellowColor, RoundedCornerShape(4.dp))
                    .background(
                        color = if (isChecked) YellowColor else Color.Transparent,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        onCheckedChange(!isChecked)
                        viewModel.archiveChecklist(id)

                    }, contentAlignment = Alignment.Center
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

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Created: $createdDate",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    LinearProgressIndicator(
                        progress = progress,
                        color = YellowColor,
                        modifier = Modifier
                            .width(80.dp)
                            .height(6.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
            }
        }
    }
}