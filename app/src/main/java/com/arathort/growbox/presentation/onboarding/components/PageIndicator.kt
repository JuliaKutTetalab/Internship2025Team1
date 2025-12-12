package com.arathort.growbox.presentation.onboarding.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Green200
import com.arathort.growbox.ui.theme.Green500

@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    selectedColor: Color = Green500,
    unselectedColor: Color = Green200
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimensions.micro),
    ) {
        repeat(pageCount) { iteration ->
            val isSelected = currentPage == iteration

            val indicatorWidth by animateDpAsState(
                targetValue = if (isSelected) Dimensions.onBoardingActInd else Dimensions.micro,
                animationSpec = tween(durationMillis = 300),
            )

            val color by animateColorAsState(
                targetValue = if (isSelected) selectedColor else unselectedColor,
                animationSpec = tween(durationMillis = 300)
            )

            Box(
                modifier = Modifier
                    .height(Dimensions.micro)
                    .width(indicatorWidth)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}