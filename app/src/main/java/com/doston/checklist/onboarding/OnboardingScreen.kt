package com.doston.checklist.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doston.checklist.R
import com.doston.checklist.database.ChecklistViewModel
import com.doston.checklist.ui.theme.ButtonColor
import com.doston.checklist.ui.theme.MainColor
import com.doston.checklist.ui.theme.WhiteColor
import com.doston.checklist.ui.theme.YellowColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(onFinish: () -> Unit,viewModel: ChecklistViewModel) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { listData.count() }
    )
    val (selectedPage, setSelectedPage) = remember { mutableIntStateOf(0) }
    val checklists by viewModel.archivedChecklists.collectAsState()
    val isDarkTheme by viewModel.themeDark.collectAsState()

    val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) WhiteColor else Color.Black
    val cardColor = if (isDarkTheme) ButtonColor else Color.White
    val accentColor = if (isDarkTheme) YellowColor else Color(0xFF6200EE)
    val topBarColor = if (isDarkTheme) ButtonColor else Color.White
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            setSelectedPage(page)
        }
    }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .background(
                backgroundColor
            )
    ) {

        Text(text = "Skip", color = textColor, fontSize = 15.sp, modifier = Modifier.clickable {
            if (selectedPage == listData.size - 1) {
                onFinish()
            } else {
                scope.launch {

                    pagerState.animateScrollToPage(selectedPage + 1)

                }
            }
        }.align(Alignment.TopEnd).padding(16.dp))
        Column(
            modifier = Modifier

                .padding(16.dp), verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                beyondViewportPageCount = listData.size,
                state = pagerState,
                modifier = Modifier.weight(1f).padding(40.dp)
            ) { page ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier


                ) {
                    Image(
                        painter = painterResource(R.drawable.task_yellow),
                        contentDescription = listData[page].title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(80.dp)

                    )

                    Text(
                        text = listData[page].title,
                        color = textColor,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .align(Alignment.CenterHorizontally), lineHeight = 32.sp
                    )

                    Text(
                        text = listData[page].desc,
                        color = textColor,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(top = 10.dp)

                    )
                }
            }


        }
    }
}
