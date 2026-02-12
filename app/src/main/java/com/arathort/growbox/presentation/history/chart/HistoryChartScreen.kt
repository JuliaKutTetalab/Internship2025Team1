package com.arathort.growbox.presentation.history.chart

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arathort.growbox.R
import com.arathort.growbox.presentation.chart.GrowBoxChart
import com.arathort.growbox.presentation.chart.PeriodSelector
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Green500
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Typography
import com.arathort.growbox.ui.theme.custom

@Composable
fun HistoryChartScreen(
    sensorTypeName: String,
    onNavigateBack: () -> Unit,
    historyChartViewModel: HistoryChartViewModel = hiltViewModel()
) {
    val uiState by historyChartViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(sensorTypeName) {
        historyChartViewModel.initSensorType(sensorTypeName)
    }
    LaunchedEffect(Unit) {
        historyChartViewModel.effect.collect { effect ->
            when (effect) {
                is HistoryChartUiEffect.OnNavigateToBack -> onNavigateBack()
                is HistoryChartUiEffect.ShowToast -> {
                    Toast.makeText(context, "Failed to connect", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        HistoryChartPage(
            uiState = uiState,
            onDailyGraphicSelected = { historyChartViewModel.onEvent(HistoryChartUiEvent.OnDailyGraphicSelected) },
            onWeekGraphicSelected = { historyChartViewModel.onEvent(HistoryChartUiEvent.OnWeekGraphicSelected) },
            onMonthlyGraphicSelected = { historyChartViewModel.onEvent(HistoryChartUiEvent.OnMonthlyGraphicSelected) },
            onReturnButtonClick = { historyChartViewModel.onEvent(HistoryChartUiEvent.OnReturnButtonClick) }
        )

        AnimatedVisibility(
            visible = uiState.isLoading,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Green500,
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}


@Composable
private fun HistoryChartPage(
    uiState: HistoryChartUiState,
    onDailyGraphicSelected: () -> Unit,
    onWeekGraphicSelected: () -> Unit,
    onMonthlyGraphicSelected: () -> Unit,
    onReturnButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.pagePadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(Dimensions.iconSizeMiddle)
                    .clip(CircleShape)
                    .clickable(onClick = onReturnButtonClick),
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null,
                )
            }

            Text(
                text = if (uiState.screenTitle != 0) stringResource(id = uiState.screenTitle) else "",
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = Dimensions.superMicro),
                style = Typography.headlineSmall,
                textAlign = TextAlign.Start
            )
        }

        Spacer(Modifier.height(Dimensions.pagePadding))

        PeriodSelector(
            selectedPeriod = uiState.selectedPeriod,
            onDaySelected = onDailyGraphicSelected,
            onWeekSelected = onWeekGraphicSelected,
            onMonthSelected = onMonthlyGraphicSelected
        )

        Spacer(Modifier.height(Dimensions.medium))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(183.dp)
                .padding(horizontal = 35.dp)
        ) {
            GrowBoxChart(
                points = uiState.chartData,
                lineColor = Green500
            )
        }

        Spacer(Modifier.height(Dimensions.extraLarge))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.pagePadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimensions.medium)
        ) {
            StatCard(
                title = "Lowest",
                value = uiState.statLowestValue,
                unit = if (uiState.unit != 0) stringResource(uiState.unit) else "",
                date = uiState.statLowestDate,
                iconRes = R.drawable.ic_low,
                modifier = Modifier.weight(1f)
            )

            StatCard(
                title = "Highest",
                value = uiState.statHighestValue,
                unit = if (uiState.unit != 0) stringResource(uiState.unit) else "",
                date = uiState.statHighestDate,
                iconRes = R.drawable.ic_high,
                modifier = Modifier.weight(1f)
            )
        }

    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    unit: String,
    date: String,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(Dimensions.cardHeight)
            .shadow(
                elevation = Dimensions.superMicro,
                shape = RoundedCornerShape(Dimensions.mediumRadius),
                spotColor = Color.Black.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(Dimensions.mediumRadius),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.custom.cardBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.medium),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = Green800
                )
                Spacer(modifier = Modifier.width(Dimensions.superMicro))
                Text(
                    text = title,
                    style = Typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                text = "$value$unit",
                style = Typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Green500
            )

            Text(
                text = date,
                style = Typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}