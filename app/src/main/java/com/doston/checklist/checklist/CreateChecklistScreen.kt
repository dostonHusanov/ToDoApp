package com.doston.checklist.checklist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doston.checklist.database.ChecklistViewModel
import com.doston.checklist.ui.theme.ButtonBlack
import com.doston.checklist.ui.theme.ButtonColor
import com.doston.checklist.ui.theme.MainColor
import com.doston.checklist.ui.theme.WhiteColor
import com.doston.checklist.ui.theme.YellowColor
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.doston.checklist.R

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateChecklistScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val checklists by viewModel.archivedChecklists.collectAsState()
    val isDarkTheme by viewModel.themeDark.collectAsState()

    val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) WhiteColor else Color.Black
    val cardColor = if (isDarkTheme) ButtonColor else Color.White
    val accentColor = if (isDarkTheme) YellowColor else Color(0xFF6200EE)
    val topBarColor = if (isDarkTheme) ButtonColor else Color.White
    LaunchedEffect(Unit) {
        viewModel.clearAll()
    }

    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedTextColor = textColor,
        unfocusedTextColor = ButtonBlack,
        disabledTextColor = ButtonColor,
        errorTextColor = Color.Red,
        cursorColor = textColor,
        focusedBorderColor = textColor,
        unfocusedBorderColor = textColor,
        unfocusedLabelColor = textColor
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp), verticalArrangement = Arrangement.SpaceBetween
    ) {
Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween) {
    OutlinedTextField(
        value = viewModel.checklistTitle.value,
        onValueChange = { viewModel.checklistTitle.value = it },
        label = { Text(stringResource(id = R.string.checklist_name)) },
        colors = textFieldColors,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = viewModel.newItemText.value,
        onValueChange = { viewModel.newItemText.value = it },
        label = { Text(stringResource(id = R.string.new_item)) },
        colors = textFieldColors,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = { viewModel.addItem() },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = accentColor,
            contentColor = textColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(43.dp)
    ) {
        Text(stringResource(id = R.string.add_item), color = textColor, fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}

        Spacer(modifier = Modifier.height(8.dp))
Column(modifier = Modifier.fillMaxSize().weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        ReorderableChecklist(viewModel = viewModel, modifier = Modifier.weight(1f))
}
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    viewModel.clearAll()
                    navController.popBackStack()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accentColor,
                    contentColor = textColor
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(43.dp)
            ) {
                Text(stringResource(R.string.cancel), color = textColor, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val currentDate = dateFormat.format(Date())

                    viewModel.addChecklist(
                        viewModel.checklistTitle.value,
                        viewModel.newChecklistItems.toList(),
                        currentDate
                    )
                    viewModel.clearAll()
                    navController.popBackStack()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accentColor,
                    contentColor = textColor
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(43.dp)
            ) {
                Text(stringResource(R.string.save), color = textColor, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun ReorderableChecklist(viewModel: ChecklistViewModel,modifier: Modifier) {
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        viewModel.moveItem(from.index, to.index)
    })

    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
            .fillMaxWidth()
    ) {
        items(viewModel.newChecklistItems.size, { it }) { index ->
            val item = viewModel.newChecklistItems[index]
            ReorderableItem(state, key = index) { isDragging ->
                DraggableChecklistItem(
                    text = item,
                    onValueChange = { viewModel.updateItem(index, it) },
                    onEditClick = { viewModel.startEditing(index) },
                    onConfirmEditClick = { viewModel.stopEditing() },
                    isEditing = viewModel.editingIndex.value == index,
                    onDeleteClick = { viewModel.removeItem(index) },
                    dragHandle = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Drag",
                            tint = Color.Gray,
                            modifier = Modifier.padding(8.dp)
                        )
                    },
                    modifier = Modifier.padding(vertical = 10.dp), viewModel = viewModel
                )

            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraggableChecklistItem(
    text: String,
    onValueChange: (String) -> Unit,
    onEditClick: () -> Unit,
    onConfirmEditClick: () -> Unit,
    isEditing: Boolean,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    dragHandle: @Composable () -> Unit,viewModel: ChecklistViewModel
) {
    val isDarkTheme by viewModel.themeDark.collectAsState()
    val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) WhiteColor else Color.Black
    val cardColor = if (isDarkTheme) ButtonColor else Color.White
    val accentColor = if (isDarkTheme) YellowColor else Color(0xFF6200EE)
    val topBarColor = if (isDarkTheme) ButtonColor else Color.White
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(cardColor)
            .border(1.dp, accentColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        dragHandle()

        Spacer(modifier = Modifier.width(8.dp))

        if (isEditing) {
            OutlinedTextField(
                value = text,
                onValueChange = onValueChange,
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = textColor,
                    unfocusedTextColor =textColor,
                    cursorColor = textColor,
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = accentColor
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            )

            IconButton(onClick = onConfirmEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.confirm_edit),
                    tint = accentColor
                )
            }

        } else {
            Text(
                text = text,
                modifier = Modifier.weight(1f),
                color =accentColor,
                fontWeight = FontWeight.SemiBold
            )

            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit),
                    tint = accentColor
                )
            }
        }

        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete),
                tint = accentColor
            )
        }
    }
}
