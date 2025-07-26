package com.doston.checklist.onboarding


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doston.checklist.ui.theme.MainColor
import com.doston.checklist.ui.theme.WhiteColor
import com.doston.checklist.ui.theme.YellowColor

@Composable
fun LanguageScreen(
    onLanguageSelected: (String) -> Unit,
    onNextClicked: () -> Unit
) {
    var selectedLang by remember { mutableStateOf<String?>(null) }

    val languages = listOf(
        "en" to "English",
        "uz" to "O'zbek",
        "ru" to "Русский"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Language",
            fontSize = 24.sp,
            color = WhiteColor,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        languages.forEach { (code, label) ->
            val isSelected = selectedLang == code
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) YellowColor else Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        color = if (isSelected) YellowColor.copy(alpha = 0.1f) else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        selectedLang = code
                    }
                    .padding(vertical = 16.dp, horizontal = 20.dp)
            ) {
                Text(
                    text = label,
                    color = WhiteColor,
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                selectedLang?.let { onLanguageSelected(it) }
                onNextClicked()
            },
            enabled = selectedLang != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedLang != null) YellowColor else Color.Gray
            )
        ) {
            Text(text = "Next", fontSize = 16.sp, color = Color.Black)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LanguageSelectionPreview() {

    LanguageScreen(
        onLanguageSelected = {},
        onNextClicked = {}
    )

}

