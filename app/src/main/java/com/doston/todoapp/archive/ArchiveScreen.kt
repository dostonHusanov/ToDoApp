package com.doston.todoapp.archive

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.doston.todoapp.R
import com.doston.todoapp.database.ChecklistViewModel
import com.doston.todoapp.ui.theme.ButtonColor
import com.doston.todoapp.ui.theme.MainColor
import com.doston.todoapp.ui.theme.WhiteColor
import com.doston.todoapp.ui.theme.YellowColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val checklists by viewModel.archivedChecklists.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Archived Checklists", color = WhiteColor) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ButtonColor)
            )
        },
        containerColor = MainColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            LazyColumn(Modifier.weight(1f)) {
                items(checklists) { checklist ->
                    ListItem(
                        headlineContent = { Text(checklist.title, color = WhiteColor, fontWeight = FontWeight.Bold
                        ) },
                        supportingContent = {
                            Text(
                                "Created: ${checklist.createdDate}",
                                color = Color.LightGray
                            )
                        },
                        tonalElevation = 2.dp,
                        colors = ListItemDefaults.colors(containerColor = ButtonColor), modifier = Modifier.border(1.dp, color = YellowColor, shape = RoundedCornerShape(15.dp)).clip(
                            RoundedCornerShape(15.dp)
                        ),   trailingContent = {
                            Icon(
                                painter = painterResource(R.drawable.delete),
                                contentDescription = "Delete",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
viewModel.deleteArchivedList(checklist.id)
                                        viewModel.archivedChecklists

                                    }
                            )
                        }

                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(shape = RoundedCornerShape(15.dp),
                onClick = { viewModel.clearArchive() },
                modifier = Modifier.fillMaxWidth().height(42.dp),
                colors = ButtonDefaults.buttonColors(containerColor = YellowColor)
            ) {
                Text("Clear All", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
