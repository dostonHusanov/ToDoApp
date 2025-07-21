package com.doston.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.doston.todoapp.ui.theme.MainColor
import com.doston.todoapp.ui.theme.WhiteColor

@Composable
fun AboutScreen(){
    Column(modifier = Modifier.fillMaxSize().background(MainColor), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "Support Screen", color = WhiteColor)
    }
}