package com.arathort.growbox.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Grey300
import com.arathort.growbox.ui.theme.Typography
import com.arathort.growbox.ui.theme.custom

@Composable
fun ControlCard(
    title: String,
    isChecked: Boolean,
    @DrawableRes iconRes: Int,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(Dimensions.onHomeCardHeight)
            .shadow(
                elevation = Dimensions.small,
                shape = RoundedCornerShape(Dimensions.small)
            ),
        shape = RoundedCornerShape(Dimensions.small),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.custom.cardBackground),
        onClick = { onCheckedChange(!isChecked) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.small),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "$title icon",
                modifier = Modifier.size(Dimensions.standardIconSize),
                tint = MaterialTheme.custom.sensorIcon
            )

            Column {
                Switch(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = White,
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = White,
                        uncheckedTrackColor = Grey300,
                        uncheckedBorderColor = Transparent
                    ),
                    modifier = Modifier
                        .scale(0.8f)
                        .align(Alignment.Start)
                        .offset(x = (-8).dp)
                )

                Text(
                    text = title,
                    style = Typography.titleLarge,
                    color = MaterialTheme.custom.sensorLabel,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}