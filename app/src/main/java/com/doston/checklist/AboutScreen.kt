package com.doston.checklist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doston.checklist.database.ChecklistViewModel
import com.doston.checklist.ui.theme.ButtonColor
import com.doston.checklist.ui.theme.MainColor
import com.doston.checklist.ui.theme.WhiteColor
import com.doston.checklist.ui.theme.YellowColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(viewModel: ChecklistViewModel) {
    val isDarkTheme by viewModel.themeDark.collectAsState()

    val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) WhiteColor else Color.Black
    val cardColor = if (isDarkTheme) ButtonColor else Color.White
    val accentColor = if (isDarkTheme) YellowColor else Color(0xFF6200EE)
    val dividerColor = if (isDarkTheme) ButtonColor else Color(0xFFE0E0E0)

    Scaffold(
        topBar = {
            Column {
                Text(
                    stringResource(R.string.about),
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = dividerColor
                )
            }
        },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(accentColor)
                    .border(3.dp, textColor.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier.size(100.dp),
                    tint = if (isDarkTheme) WhiteColor else Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.app_name),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(R.string.version),
                fontSize = 16.sp,
                color = textColor.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            InfoCard(cardColor, accentColor) {
                Text(
                    text = stringResource(R.string.about_this_app),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.about_description),
                    fontSize = 14.sp,
                    color = textColor,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            InfoCard(cardColor, accentColor) {
                Text(
                    text = stringResource(R.string.key_features),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                FeatureItem("✓", stringResource(R.string.feature_1), textColor, accentColor)
                FeatureItem("✓", stringResource(R.string.feature_2), textColor, accentColor)
                FeatureItem("✓", stringResource(R.string.feature_3), textColor, accentColor)
                FeatureItem("✓", stringResource(R.string.feature_4), textColor, accentColor)
                FeatureItem("✓", stringResource(R.string.feature_5), textColor, accentColor)
                FeatureItem("✓", stringResource(R.string.feature_6), textColor, accentColor)
            }

            Spacer(modifier = Modifier.height(20.dp))

            InfoCard(cardColor, accentColor) {
                Text(
                    text = stringResource(R.string.developer),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.developer_name),
                    fontSize = 16.sp,
                    color = textColor,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.technology),
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            InfoCard(cardColor, accentColor) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.current_theme),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )

                    Text(
                        text = if (isDarkTheme) stringResource(R.string.dark) else stringResource(R.string.light),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        modifier = Modifier
                            .background(
                                accentColor.copy(alpha = 0.1f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.theme_info),
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.copyright),
                fontSize = 12.sp,
                color = textColor.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FeatureItem(
    bullet: String,
    text: String,
    textColor: Color,
    accentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = bullet,
            color = accentColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun InfoCard(
    cardColor: Color,
    accentColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp), content = content)
    }
}
