package com.arathort.growbox.presentation.onboarding.components

import androidx.annotation.DrawableRes
import com.arathort.growbox.R

data class OnBoardingPageData(
    val title: Int,
    @param:DrawableRes val image: Int,
    val description: Int,
)

val pagesData = listOf(
    OnBoardingPageData(
        title = R.string.onboadring_first_page_title,
        image = R.drawable.img_onboarding_page_first,
        description = R.string.onboarding_first_page_description
    ),

    OnBoardingPageData(
        title = R.string.onboadring_second_page_title,
        image = R.drawable.img_onboarding_page_second,
        description = R.string.onboarding_second_page_description
    ),

    OnBoardingPageData(
        title = R.string.onboadring_third_page_title,
        image = R.drawable.img_onboarding_page_third,
        description = R.string.onboarding_third_page_description
    )
)