package com.arathort.growbox.presentation.settings.components.cards

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.settings.components.CustomGradientSlider
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Typography
import com.arathort.growbox.ui.theme.custom

@Composable
fun SettingCard(
    value: Float,
    @DrawableRes icon: Int,
    name: String,
    valueRange: ClosedFloatingPointRange<Float>,
    unitsOfMeasurement: String,
    onChange: (Float) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(value) }

    LaunchedEffect(value) {
        sliderPosition = value
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = Dimensions.small,
                shape = RoundedCornerShape(Dimensions.mediumRadius),
            )
            .height(Dimensions.mediumCardHeight),
        shape = RoundedCornerShape(Dimensions.mediumRadius),
        colors = CardDefaults.cardColors()
            .copy(containerColor = MaterialTheme.custom.cardBackground)
    ) {
        Column(
            modifier = Modifier.padding(Dimensions.small)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = Green800
                )
                Spacer(modifier = Modifier.width(Dimensions.micro))
                Text(
                    text = name, style = Typography.titleLarge,
                )
            }

            CustomGradientSlider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = valueRange,
                unitsOfMeasurement = unitsOfMeasurement,
                onChangeFinished = { onChange(sliderPosition) },

            )

        }
    }
}