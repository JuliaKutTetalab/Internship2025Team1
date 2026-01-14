package com.arathort.growbox.presentation.settings.components.cards

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.settings.components.CustomGradientSlider
import com.arathort.growbox.ui.theme.Green500
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Typography
import com.arathort.growbox.ui.theme.custom


@Composable
fun SettingCardWithList(
    value: Float,
    @DrawableRes icon: Int,
    name: String,
    valueRange: ClosedFloatingPointRange<Float>,
    unitsOfMeasurement: String,
    onChange: (Float) -> Unit,
    frequencyIndex: Int,
    onIndexChange: (Int) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(value) }

    LaunchedEffect(value) {
        sliderPosition = value
    }
    var expanded by remember { mutableStateOf(false) }

    val frequencyOptions = listOf("Every day", "Every 2 days", "Every 3 days", "Weekly")

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
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = Green800
                    )
                    Spacer(modifier = Modifier.width(Dimensions.micro))

                    Text(
                        text = name,
                        style = Typography.titleLarge,
                    )
                }

                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { expanded = true }
                    ) {
                        Text(
                            text = frequencyOptions.getOrElse(frequencyIndex) { "" },
                            style = Typography.labelMedium,
                            color = Green500
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Green500
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        frequencyOptions.forEachIndexed { index, label ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = label,
                                        style = Typography.labelMedium,
                                        color = Green500
                                    )
                                },
                                onClick = {
                                    onIndexChange(index)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }


            CustomGradientSlider(
                value = sliderPosition,
                valueRange = valueRange,
                unitsOfMeasurement = unitsOfMeasurement,
                onChangeFinished = { onChange(sliderPosition) },
                onValueChange = { sliderPosition = it }
            )
        }
    }
}