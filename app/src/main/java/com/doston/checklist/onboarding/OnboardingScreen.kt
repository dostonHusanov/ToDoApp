package com.doston.checklist.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
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
fun OnBoardingScreen(onFinish: () -> Unit, viewModel: ChecklistViewModel) {
    val pagerState = rememberPagerState(initialPage = 0) { 2 } // pageCount = 2
    val (selectedPage, setSelectedPage) = remember { mutableIntStateOf(0) }
    val isDarkTheme by viewModel.themeDark.collectAsState()

    val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) WhiteColor else Color.Black
    val accentColor = if (isDarkTheme) YellowColor else Color(0xFF6200EE)

    val listData = listOf(
        OnboardingData(
            image = R.drawable.task_yellow,
            title = stringResource(R.string.onboard_title_1),
            desc = stringResource(R.string.onboard_desc_1)
        ),
        OnboardingData(
            image = R.drawable.settings,
            title = stringResource(R.string.onboard_title_2),
            desc = stringResource(R.string.onboard_desc_2)
        )
    )

    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            setSelectedPage(page)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .background(backgroundColor)
    ) {
        Text(
            text = stringResource(R.string.skip),
            color = YellowColor,
            fontSize = 15.sp,
            modifier = Modifier
                .clickable {
                    if (selectedPage == listData.lastIndex) {
                        onFinish()
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(selectedPage + 1)
                        }
                    }
                }
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.padding(vertical = 40.dp).fillMaxWidth()
                    .weight(1f),
                beyondViewportPageCount = listData.size
            ) { page ->
                val item = listData[page]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize().padding(horizontal = 15.dp)
                ) {
                    Image(
                        painter = painterResource(item.image),
                        contentDescription = item.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(150.dp)
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = item.title,
                        color = textColor,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding()
                            .align(Alignment.CenterHorizontally),
                        lineHeight = 32.sp
                    )
Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = item.desc,
                        color = textColor,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}
