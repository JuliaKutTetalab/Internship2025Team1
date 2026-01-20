package com.arathort.growbox.presentation.chart

import android.graphics.Paint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.common.card.SensorCard
import com.arathort.growbox.ui.theme.Green500
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Grey400
import com.arathort.growbox.ui.theme.Typography
import kotlin.math.max

@Composable
fun ChartScreen(
    sensorTypeName: String,
    onNavigateBack: () -> Unit,
    chartViewModel: ChartViewModel = hiltViewModel()
) {
    val uiState by chartViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(sensorTypeName) {
        chartViewModel.initSensorType(sensorTypeName)
    }
    LaunchedEffect(Unit) {
        chartViewModel.effect.collect { effect ->
            when (effect) {
                is ChartUiEffect.onNavigateToBack -> onNavigateBack()
                is ChartUiEffect.ShowToast -> {
                    Toast.makeText(context, "Failed to connect", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        ChartPage(
            uiState = uiState,
            OnDailyGraphicSelected = { chartViewModel.onEvent(ChartUiEvent.OnDailyGraphicSelected) },
            OnWeekGraphicSelected = { chartViewModel.onEvent(ChartUiEvent.OnWeekGraphicSelected) },
            OnMonthlyGraphicSelected = { chartViewModel.onEvent(ChartUiEvent.OnMonthlyGraphicSelected) },
            OnReturnButtonClick = { chartViewModel.onEvent(ChartUiEvent.OnReturnButtonClick) }
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
fun ChartPage(
    uiState: ChartUiState,
    OnDailyGraphicSelected: () -> Unit,
    OnWeekGraphicSelected: () -> Unit,
    OnMonthlyGraphicSelected: () -> Unit,
    OnReturnButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                    .clickable(onClick = OnReturnButtonClick),
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Text(
                text = if (uiState.screenTitle != 0) stringResource(id = uiState.screenTitle) else "",
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = Dimensions.superMicro),
                style = Typography.headlineSmall,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }

        Spacer(Modifier.height(Dimensions.pagePadding))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.pagePadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (uiState.sensorIcon != 0) {
                Icon(
                    painter = painterResource(uiState.sensorIcon),
                    contentDescription = null,
                    modifier = Modifier.size(42.dp),
                    tint = Green800
                )
            }
            Text(
                text = stringResource(R.string.description_for_chart),
                color = Grey400,
                style = Typography.labelMedium,
                modifier = Modifier
                    .padding(horizontal = Dimensions.pagePadding)
                    .weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                text = "${uiState.currentValue} ${if (uiState.unit != 0) stringResource(id = uiState.unit) else ""}",
                style = Typography.headlineSmall,
                color = Green500,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(start = Dimensions.small)
            )
        }

        Spacer(Modifier.height(Dimensions.pagePadding))
        PeriodSelector(
            selectedPeriod = uiState.selectedPeriod,
            onDaySelected = OnDailyGraphicSelected,
            onWeekSelected = OnWeekGraphicSelected,
            onMonthSelected = OnMonthlyGraphicSelected
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
            SensorCard(
                "Current",
                "${uiState.currentValue} ${if (uiState.unit != 0) stringResource(id = uiState.unit) else ""}",
                null,
                {},
                Modifier
                    .height(80.dp)
                    .weight(1f)
            )
            SensorCard(
                "Recommended",
                "${uiState.statRecommended} ${if (uiState.unit != 0) stringResource(id = uiState.unit) else ""}",
                null,
                {},
                Modifier
                    .height(80.dp)
                    .weight(1f)
            )
        }
        Spacer(Modifier.height(Dimensions.medium))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.pagePadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimensions.medium)
        ) {
            SensorCard(
                "Week",
                "${uiState.statPeriodValue} ${if (uiState.unitCalculate != 0) stringResource(id = uiState.unitCalculate) else ""}",
                null,
                {},
                Modifier
                    .height(80.dp)
                    .weight(1f)
            )

            SensorCard(
                "Total",
                "${uiState.statTotalValue} ${if (uiState.unitCalculate != 0) stringResource(id = uiState.unitCalculate) else ""}",
                null,
                {},
                Modifier
                    .height(80.dp)
                    .weight(1f)
            )
        }
    }
}

@Composable
fun PeriodSelector(
    selectedPeriod: StatisticPeriod,
    onDaySelected: () -> Unit,
    onWeekSelected: () -> Unit,
    onMonthSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.pagePadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PeriodTabItem(
            text = "Day",
            isSelected = selectedPeriod == StatisticPeriod.DAY,
            onClick = onDaySelected,
            modifier = Modifier.weight(1f)
        )
        PeriodTabItem(
            text = "Week",
            isSelected = selectedPeriod == StatisticPeriod.WEEK,
            onClick = onWeekSelected,
            modifier = Modifier.weight(1f)
        )
        PeriodTabItem(
            text = "Month",
            isSelected = selectedPeriod == StatisticPeriod.MONTH,
            onClick = onMonthSelected,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PeriodTabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = Typography.labelLarge,
            color = if (isSelected) Green500 else Grey400,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(Green500, CircleShape)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFE0E0E0))
            )
        }
    }
}

@Composable
fun GrowBoxChart(
    points: List<ChartPoint>,
    lineColor: Color
) {
    if (points.isEmpty()) return

    val density = LocalContext.current.resources.displayMetrics.density

    val textPaintTop = remember {
        Paint().apply {
            color = Green800.toArgb()
            textSize = 14f * density
            textAlign = Paint.Align.CENTER
        }
    }

    val textPaintBottom = remember {
        Paint().apply {
            color = Grey400.toArgb()
            textSize = 12f * density
            textAlign = Paint.Align.CENTER
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val topAreaHeight = 80f
        val bottomAreaHeight = 80f

        val chartHeight = height - topAreaHeight - bottomAreaHeight

        val maxVal = points.maxOfOrNull { it.value } ?: 100f
        val yMax = max(maxVal, 1f) * 1.2f

        val stepX = width / (points.size - 1).coerceAtLeast(1)

        val labelSkip = if (points.size > 10) 5 else 1

        val path = Path()

        points.forEachIndexed { index, point ->
            val x = index * stepX
            val y = topAreaHeight + chartHeight - ((point.value / yMax) * chartHeight)

            val showLabel = index % labelSkip == 0 || index == points.lastIndex

            if (showLabel) {
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.4f),
                    start = Offset(x, topAreaHeight - 30f),
                    end = Offset(x, height - 30f),
                    strokeWidth = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )

                drawContext.canvas.nativeCanvas.drawText(
                    "${point.value.toInt()}%",
                    x,
                    topAreaHeight - 50f,
                    textPaintTop
                )

                val labels = point.labelTop.split(" ")
                var textY = height - 40f
                labels.forEach { line ->
                    drawContext.canvas.nativeCanvas.drawText(
                        line,
                        x,
                        textY,
                        textPaintBottom
                    )
                    textY += 35f
                }
            }

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(
                width = 5f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        val circleRadius = if (points.size > 10) 6f else 12f
        val strokeWidth = if (points.size > 10) 4f else 8f

        points.forEachIndexed { index, point ->
            val x = index * stepX
            val y = topAreaHeight + chartHeight - ((point.value / yMax) * chartHeight)

            drawCircle(
                color = Color.White,
                radius = circleRadius,
                center = Offset(x, y)
            )
            drawCircle(
                color = lineColor,
                radius = circleRadius,
                center = Offset(x, y),
                style = Stroke(width = strokeWidth)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewChartPage() {
    val mockPoints = listOf(
        ChartPoint(20f, "Mon 08", "08"),
        ChartPoint(36f, "Tue 09", "09"),
        ChartPoint(18f, "Wed 10", "10"),
        ChartPoint(60f, "Thu 11", "11"),
        ChartPoint(40f, "Fri 12", "12"),
        ChartPoint(25f, "Sat 13", "13"),
        ChartPoint(18f, "Sun 14", "14")
    )

    val mockState = ChartUiState(
        isLoading = false,
        screenTitle = R.string.light,
        sensorIcon = R.drawable.ic_nutrion,
        currentValue = "58",
        unit = R.string.unit_percent,
        unitCalculate = R.string.unit_kw,
        statRecommended = "80",
        statCurrent = "58",
        statPeriodValue = "60",
        statTotalValue = "100",
        chartData = mockPoints,
        selectedPeriod = StatisticPeriod.WEEK
    )

    MaterialTheme {
        ChartPage(
            uiState = mockState,
            OnDailyGraphicSelected = {},
            OnWeekGraphicSelected = {},
            OnMonthlyGraphicSelected = {},
            OnReturnButtonClick = {}
        )
    }
}