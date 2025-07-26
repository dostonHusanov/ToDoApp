package com.doston.checklist

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.content.res.Configuration
import android.os.Build
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.doston.checklist.database.ChecklistViewModel
import com.doston.checklist.ui.theme.ButtonColor
import com.doston.checklist.ui.theme.MainColor
import com.doston.checklist.ui.theme.WhiteColor
import com.doston.checklist.ui.theme.YellowColor
import java.util.*

@Composable
fun LanguageSelector(context: Context, onLanguageChanged: (String) -> Unit,viewModel: ChecklistViewModel) {
    val languages = listOf("en", "ru", "uz")
    var expanded by remember { mutableStateOf(false) }
    var selectedLang by remember { mutableStateOf(LocaleManager.getLanguage(context)) }



    Box {
        Text(
            text = selectedLang.uppercase(),
            modifier = Modifier
                .clickable { expanded = true }
                .padding(16.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { lang ->
                DropdownMenuItem(
                    text = { Text(lang.uppercase()) },
                    onClick = {
                        selectedLang = lang
                        expanded = false
                        onLanguageChanged(lang)
                    }
                )
            }
        }
    }
}



