package com.arathort.growbox.presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Green500
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Typography
import com.arathort.growbox.ui.theme.White


@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val gradientColors = listOf(Green500, Green800)
    val buttonShape = RoundedCornerShape(Dimensions.mediumRadius)

    Button(
        onClick = onClick,
        modifier = Modifier
            .height(Dimensions.onBoardingButtonHeight)
            .width(Dimensions.onBoardingButtonWidth),
        shape = buttonShape,
        contentPadding = PaddingValues(),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(colors = gradientColors),
                    shape = buttonShape
                )
                .clip(buttonShape),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                color = White,
                style = Typography.headlineMedium
            )
            Spacer(Modifier.width(Dimensions.micro))
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null,
                tint = Green800,
                modifier = Modifier
                    .size(Dimensions.standardIconSize)
            )
        }
    }
}