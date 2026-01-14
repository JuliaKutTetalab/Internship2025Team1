package com.arathort.growbox.presentation.settings.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Green200
import com.arathort.growbox.ui.theme.Green300
import com.arathort.growbox.ui.theme.Green500
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomGradientSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    unitsOfMeasurement: String = "`C",
    onChangeFinished: () -> Unit,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }

    val trackHeight = Dimensions.micro
    val thumbSize = Dimensions.medium
    val textOffset = Dimensions.large

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            interactionSource = interactionSource,
            onValueChangeFinished = { onChangeFinished() },
            enabled = enabled,
            steps = (valueRange.endInclusive - valueRange.start).toInt() - 1,
            thumb = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .offset(y = Dimensions.thumbOffset)
                        .width(thumbSize)
                        .wrapContentWidth(unbounded = true)
                ) {
                    Box(
                        modifier = Modifier
                            .size(thumbSize)
                            .background(Color.White, CircleShape)
                            .border(Dimensions.nano, Green800, CircleShape)
                    )

                    Text(
                        text = "${value.toInt()}$unitsOfMeasurement",
                        style = Typography.labelMedium,
                        color = Green300,
                        modifier = Modifier.padding(top = Dimensions.superMicro),
                        maxLines = 1
                    )
                }
            },
            track = {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(trackHeight)
                ) {
                    val cornerRadius = CornerRadius(trackHeight.toPx() / 2)

                    drawRoundRect(
                        color = Green200,
                        size = size,
                        cornerRadius = cornerRadius
                    )

                    val fraction =
                        (value - valueRange.start) / (valueRange.endInclusive - valueRange.start)
                    val activeWidth = size.width * fraction

                    drawRoundRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Green500, Green800),
                            startY = 0f,
                            endY = activeWidth
                        ),
                        size = Size(width = activeWidth, height = size.height),
                        cornerRadius = cornerRadius
                    )
                }
            },
            modifier = Modifier.height(thumbSize + textOffset + Dimensions.mediumOffset)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = Dimensions.nano),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${valueRange.start.toInt()}$unitsOfMeasurement",
                style = Typography.labelMedium,
                color = Green300
            )
            Text(
                text = "${valueRange.endInclusive.toInt()}$unitsOfMeasurement",
                style = Typography.labelMedium,
                color = Green300
            )
        }
    }
}