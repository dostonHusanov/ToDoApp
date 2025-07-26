package com.doston.checklist.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.doston.checklist.R

class OnboardingData(
    @DrawableRes val image: Int,
    val title: String,
    val desc: String
)

@Composable
fun getOnboardingData(): List<OnboardingData> {
    return listOf(
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
}
