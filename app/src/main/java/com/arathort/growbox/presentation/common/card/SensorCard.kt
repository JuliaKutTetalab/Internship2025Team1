package com.arathort.growbox.presentation.common.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Typography
import com.arathort.growbox.ui.theme.custom

@Composable
fun SensorCard(
    title: String,
    value: String,
    @DrawableRes iconRes: Int?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .height(Dimensions.onHomeCardHeight)
            .shadow(
                elevation = Dimensions.small,
                shape = RoundedCornerShape(Dimensions.small)
            ),
        shape = RoundedCornerShape(Dimensions.small),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.custom.cardBackground),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.small),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (iconRes != null && iconRes != 0) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = stringResource(R.string.icon, title),
                    modifier = Modifier.size(Dimensions.standardIconSize),
                    tint = MaterialTheme.custom.sensorIcon
                )
            }
            Column {
                Text(
                    text = value,
                    style = Typography.headlineSmall,
                    color = MaterialTheme.custom.sensorValue,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Visible
                )

                Spacer(Modifier.height(Dimensions.micro))

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