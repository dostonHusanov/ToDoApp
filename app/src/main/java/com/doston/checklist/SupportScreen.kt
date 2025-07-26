package com.doston.checklist

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doston.checklist.data.FAQItem
import com.doston.checklist.database.ChecklistViewModel
import com.doston.checklist.ui.theme.ButtonColor
import com.doston.checklist.ui.theme.MainColor
import com.doston.checklist.ui.theme.WhiteColor
import com.doston.checklist.ui.theme.YellowColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(viewModel: ChecklistViewModel) {
    val isDarkTheme by viewModel.themeDark.collectAsState()
    val context = LocalContext.current

    val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) WhiteColor else Color.Black
    val cardColor = if (isDarkTheme) ButtonColor else Color.White
    val accentColor = if (isDarkTheme) YellowColor else Color(0xFF6200EE)
    val dividerColor = if (isDarkTheme) ButtonColor else Color(0xFFE0E0E0)

    var fullName by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var expandedFAQ by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            Column {
                Text(
                    stringResource(R.string.support_help),
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = stringResource(R.string.faq_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val faqItems = listOf(
                FAQItem(stringResource(R.string.faq_q1), stringResource(R.string.faq_a1)),
                FAQItem(stringResource(R.string.faq_q2), stringResource(R.string.faq_a2)),
                FAQItem(stringResource(R.string.faq_q3), stringResource(R.string.faq_a3)),
                FAQItem(stringResource(R.string.faq_q4), stringResource(R.string.faq_a4)),
                FAQItem(stringResource(R.string.faq_q5), stringResource(R.string.faq_a5)),
                FAQItem(stringResource(R.string.faq_q6), stringResource(R.string.faq_a6)),
                FAQItem(stringResource(R.string.faq_q7), stringResource(R.string.faq_a7))
            )

            faqItems.forEachIndexed { index, faq ->
                FAQCard(
                    faq = faq,
                    isExpanded = expandedFAQ == index,
                    onToggle = { expandedFAQ = if (expandedFAQ == index) null else index },
                    cardColor = cardColor,
                    textColor = textColor,
                    accentColor = accentColor
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.still_need_help),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(R.string.send_us_message),
                fontSize = 14.sp,
                color = textColor.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.contact_form),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text(stringResource(R.string.full_name), color = textColor.copy(alpha = 0.7f)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = textColor.copy(alpha = 0.3f),
                            focusedTextColor = textColor,
                            unfocusedTextColor = textColor,
                            cursorColor = accentColor
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text(stringResource(R.string.your_message), color = textColor.copy(alpha = 0.7f)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = textColor.copy(alpha = 0.3f),
                            focusedTextColor = textColor,
                            unfocusedTextColor = textColor,
                            cursorColor = accentColor
                        ),
                        maxLines = 5
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (fullName.isNotBlank() && message.isNotBlank()) {
                                sendToTelegram(context, fullName, message)
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.fill_all_fields),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                        shape = RoundedCornerShape(12.dp),
                        enabled = fullName.isNotBlank() && message.isNotBlank()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_send),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                stringResource(R.string.send_message),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = stringResource(R.string.message_clipboard_hint),
                        fontSize = 12.sp,
                        color = textColor.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.other_ways),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ContactMethodItem(
                        icon = android.R.drawable.ic_dialog_email,
                        title = stringResource(R.string.email_support),
                        subtitle = "husanovdostonbek1010@gmail.com",
                        textColor = textColor,
                        accentColor = accentColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ContactMethodItem(
                        icon = android.R.drawable.ic_menu_call,
                        title = stringResource(R.string.telegram),
                        subtitle = "Dostonbek_Husanov",
                        textColor = textColor,
                        accentColor = accentColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FAQCard(
    faq: FAQItem,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    cardColor: Color,
    textColor: Color,
    accentColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onToggle() },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = faq.question,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (isExpanded) "−" else "+",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = textColor.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = faq.answer,
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.8f),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
private fun ContactMethodItem(
    icon: Int,
    title: String,
    subtitle: String,
    textColor: Color,
    accentColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = accentColor
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = textColor.copy(alpha = 0.7f)
            )
        }
    }
}

private fun sendToTelegram(context: Context, fullName: String, message: String) {
    val formattedMessage = """
Name: $fullName
Message: $message
    """.trimIndent()

    val encodedMessage = Uri.encode(formattedMessage)

    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Support Message", formattedMessage)
    clipboard.setPrimaryClip(clip)

    try {

        val telegramUrl = "https://t.me/Dostonbek_Husanov?text=$encodedMessage"
        val telegramIntent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl))

        telegramIntent.setPackage("org.telegram.messenger")
        context.startActivity(telegramIntent)

        Toast.makeText(context, "Opening Telegram with your message...", Toast.LENGTH_SHORT).show()

    } catch (e: Exception) {

        try {
            val telegramUrl = "https://t.me/Dostonbek_Husanov?text=$encodedMessage"
            val telegramXIntent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl))
            telegramXIntent.setPackage("org.thunderdog.challegram")
            context.startActivity(telegramXIntent)

            Toast.makeText(context, "Opening Telegram with your message...", Toast.LENGTH_SHORT)
                .show()

        } catch (e2: Exception) {

            try {
                val telegramUrl = "https://t.me/Dostonbek_Husanov?text=$encodedMessage"
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl))
                context.startActivity(browserIntent)

                Toast.makeText(
                    context,
                    "Opening Telegram Web with your message...",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e3: Exception) {
                Toast.makeText(
                    context,
                    "Message copied to clipboard. Please visit t.me/Dostonbek_Husanov and paste it manually.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

