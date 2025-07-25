package com.doston.checklist

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
import java.util.*

@Composable
fun LanguageSelector(context: Context, onLanguageChanged: (Context) -> Unit) {
    val languages = listOf("en", "ru", "uz")
    var expanded by remember { mutableStateOf(false) }
    var selectedLang by remember { mutableStateOf("en") }

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
                        val newContext = context.setLocale(lang)
                        onLanguageChanged(newContext)
                    }
                )
            }
        }
    }
}


fun Context.setLocale(language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        createConfigurationContext(config)
    } else {
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
        this
    }
}

