package com.arathort.growbox.presentation.history

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography
import com.arathort.growbox.ui.theme.custom

@Composable
fun HistoryScreen(backStack: NavBackStack<NavKey>) {
    HistoryPage(onBackClick = { backStack.removeAt(backStack.lastIndex) })
}

@Composable
private fun HistoryPage(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimensions.pagePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackClick() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }

            Text(text = stringResource(R.string.historic_data), style = Typography.headlineSmall)
        }

        val items = listOf(
            HistoricItemData(R.string.light, R.drawable.ic_light),
            HistoricItemData(R.string.temperature, R.drawable.ic_temperature),
            HistoricItemData(R.string.humidity, R.drawable.ic_humidity),
            HistoricItemData(R.string.nutrition, R.drawable.ic_nutrion)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(items) { item ->
                HistoricCard(
                    text = stringResource(item.titleRes),
                    icon = item.iconRes,

                    )
            }
        }

    }
}

data class HistoricItemData(val titleRes: Int, val iconRes: Int)

@Composable
private fun HistoricCard(text: String, @DrawableRes icon: Int) {
    Card(
        shape = RoundedCornerShape(Dimensions.mediumRadius),
        modifier = Modifier
            .padding(Dimensions.micro)
            .shadow(
                elevation = Dimensions.small,
                shape = RoundedCornerShape(Dimensions.mediumRadius),
            ),
        colors = CardDefaults.cardColors()
            .copy(containerColor = MaterialTheme.custom.cardBackground)

    ) {
        Column(
            modifier = Modifier.padding(Dimensions.small)
        ) {

            Icon(painter = painterResource(icon), contentDescription = null, tint = Green800)
            Spacer(modifier = Modifier.height(Dimensions.micro))
            Text(
                text = text,
                style = Typography.titleLarge,
                fontWeight = FontWeight.W600
            )


        }
    }
}

@Preview
@Composable
private fun HistoryPagePreview() {
    GrowBoxTheme {
        HistoryPage(onBackClick = {})
    }
}