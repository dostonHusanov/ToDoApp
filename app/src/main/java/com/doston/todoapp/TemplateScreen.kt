package com.doston.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doston.todoapp.database.ChecklistViewModel
import com.doston.todoapp.ui.theme.ButtonBlack
import com.doston.todoapp.ui.theme.ButtonColor
import com.doston.todoapp.ui.theme.MainColor
import com.doston.todoapp.ui.theme.WhiteColor
import com.doston.todoapp.ui.theme.YellowColor

@Composable
fun TemplateScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val checklists by viewModel.checklists.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredChecklists = checklists.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor)
    ) {

        SearchScreen(searchQuery) { newQuery ->
            searchQuery = newQuery
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            items(filteredChecklists) { template ->
                TemplateItem(
                    template.title,
                    template.items,
                    template.createdDate,
                    navController,
                    viewModel
                )
            }
        }

        Button(
            modifier = Modifier
                .height(42.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth(),
            shape = RectangleShape,
            onClick = { navController.navigate("create") },
            colors = ButtonColors(
                containerColor = YellowColor,
                contentColor = WhiteColor,
                disabledContainerColor = ButtonColor,
                disabledContentColor = ButtonBlack
            )
        ) {
            Text("Create Custom", color = WhiteColor)
        }
    }
}


@Composable
fun TemplateItem(
    title: String,
    items: List<String>,
    createdDate: String,
    navController: NavController,
    viewModel: ChecklistViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(191.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(ButtonColor)
            .border(1.dp, shape = RoundedCornerShape(15.dp), color = YellowColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = title,
            color = WhiteColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = createdDate,
            color = WhiteColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Button(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .height(40.dp)
                .fillMaxWidth(),
            shape = RectangleShape,
            onClick = {
                viewModel.addChecklist(title, items, createdDate)
                navController.popBackStack()
            },
            colors = ButtonColors(
                containerColor = YellowColor,
                contentColor = WhiteColor,
                disabledContainerColor = ButtonColor,
                disabledContentColor = ButtonBlack
            )
        ) {
            Text("Use Template")
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MainColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text("Search templates...", color = Color.Gray)
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFF5722),
                focusedTextColor = WhiteColor,
                unfocusedBorderColor = Color(0xFFFF5722),
                containerColor = Color(0xFF212121),
                cursorColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
}
