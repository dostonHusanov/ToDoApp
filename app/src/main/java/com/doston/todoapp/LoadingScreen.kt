package com.doston.todoapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.doston.todoapp.ui.theme.MainColor
import com.doston.todoapp.ui.theme.ToDoAppTheme

@Composable
fun LoadingScreen(){
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize().background(
        MainColor)) {

        Image(painter = painterResource(id=R.drawable.task_white), contentDescription = "task white", modifier = Modifier.size(130.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview(){
   ToDoAppTheme { LoadingScreen()  }
}