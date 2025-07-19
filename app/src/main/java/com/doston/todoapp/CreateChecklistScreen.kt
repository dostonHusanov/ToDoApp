package com.doston.todoapp

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateChecklistScreen(navController: NavController, viewModel: ChecklistViewModel) {

    // Clear the form when screen is first composed
    LaunchedEffect(Unit) {
        viewModel.clearAll() // Use existing clearAll method
    }

    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedTextColor = WhiteColor,
        unfocusedTextColor = ButtonBlack,
        disabledTextColor = ButtonColor,
        errorTextColor = Color.Red,
        cursorColor = WhiteColor,
        focusedBorderColor = ButtonBlack,
        unfocusedBorderColor = WhiteColor,
        unfocusedLabelColor = WhiteColor
    )
    val checklistTitle = mutableStateOf("")
    val newChecklistTitle = mutableStateOf("")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor)
            .padding(16.dp)
    ) {
        // Checklist Title
        OutlinedTextField(
            value = viewModel.checklistTitle.value,
            onValueChange = { viewModel.checklistTitle.value = it },
            label = { Text("Checklist Name") },
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // New Item Input
        OutlinedTextField(
            value = viewModel.newItemText.value,
            onValueChange = { viewModel.newItemText.value = it },
            label = { Text("New Item") },
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Add Item Button
        Button(
            onClick = { viewModel.addItem() },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonColors(
                containerColor = YellowColor,
                contentColor = WhiteColor,
                disabledContainerColor = ButtonColor,
                disabledContentColor = ButtonBlack
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(43.dp)
        ) {
            Text("Add Item", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Display Items
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(viewModel.newChecklistItems.size) { index ->
                OutlinedTextField(
                    value = viewModel.newChecklistItems[index],
                    onValueChange = { viewModel.newChecklistItems[index] = it },
                    label = { Text("Item ${index + 1}") },
                    colors = textFieldColors,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Cancel / Save Row
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Cancel Button
            Button(
                onClick = {
                    viewModel.clearAll() // Use existing clearAll method
                    navController.popBackStack()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(
                    containerColor = YellowColor,
                    contentColor = WhiteColor,
                    disabledContainerColor = ButtonColor,
                    disabledContentColor = ButtonBlack
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(43.dp)
            ) {
                Text("Cancel", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Save Button
            Button(
                onClick = {
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val currentDate = dateFormat.format(Date())

                    viewModel.addChecklist(
                        viewModel.checklistTitle.value,
                        viewModel.newChecklistItems,
                        currentDate
                    )

                    viewModel.clearAll()
                    navController.popBackStack()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(
                    containerColor = YellowColor,
                    contentColor = WhiteColor,
                    disabledContainerColor = ButtonColor,
                    disabledContentColor = ButtonBlack
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(43.dp)
            ) {
                Text("Save", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}