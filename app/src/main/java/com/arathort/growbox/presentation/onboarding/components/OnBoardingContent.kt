package com.arathort.growbox.presentation.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.onboarding.OnBoardingUiEvent
import com.arathort.growbox.ui.theme.Green800
import kotlinx.coroutines.launch

@Composable
fun OnBoardingContent(
    onEvent: (OnBoardingUiEvent) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pagesData.size })
    val scope = rememberCoroutineScope()

    val isLastPage by remember {
        derivedStateOf { pagerState.currentPage == pagesData.size - 1 }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = Dimensions.pagePadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(Dimensions.extraLarge))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.Top
        ) { index ->
            OnBoardingItem(page = pagesData[index])
        }

        Spacer(modifier = Modifier.height(Dimensions.extraLarge))

        PageIndicator(
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage,
        )

        Spacer(modifier = Modifier.height(Dimensions.xxLarge))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimensions.mediumLarge),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TextButton(onClick = {
                onEvent(OnBoardingUiEvent.FinishOnBoarding)
            }) {
                Text(
                    text = stringResource(R.string.onboadring_button_skip),
                    color = Green800,
                    fontSize = Dimensions.textSizeBody,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            GradientButton(
                text = stringResource(R.string.onboadring_button_next),
                onClick = {
                    scope.launch {
                        if (isLastPage) {
                            onEvent(OnBoardingUiEvent.FinishOnBoarding)
                        } else {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
            )
        }
    }
}