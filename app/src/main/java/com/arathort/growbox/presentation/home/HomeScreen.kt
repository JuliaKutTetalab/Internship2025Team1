package com.arathort.growbox.presentation.home

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.common.card.ControlCard
import com.arathort.growbox.presentation.home.components.GradientProgressIndicator
import com.arathort.growbox.presentation.common.card.SensorCard
import com.arathort.growbox.presentation.navigation.Route
import com.arathort.growbox.ui.theme.*

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (SensorType) -> Unit
) {

    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        homeViewModel.effect.collect { effect ->
            when (effect) {
                is HomeUiEffect.NavigateToDetail -> onNavigateToDetail(effect.route)
                is HomeUiEffect.ShowToast -> {
                    Toast.makeText(context, "Failed to connect", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        HomePage(
            uiState = uiState,
            onVentChanged = { homeViewModel.onEvent(HomeUiEvent.onVentToggle(it)) },
            onWateringChanged = { homeViewModel.onEvent(HomeUiEvent.onWateringToggle(it)) },
            onSensorClick = { homeViewModel.onEvent(HomeUiEvent.onSensorDetailClick(it)) }
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
fun HomePage(
    uiState: HomeUiState,
    onVentChanged: (Boolean) -> Unit,
    onWateringChanged: (Boolean) -> Unit,
    onSensorClick: (SensorType) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = Dimensions.pagePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(Dimensions.xxLarge))

        val borderBrush = Brush.verticalGradient(
            colors = listOf(Green500, Green800)
        )

        Image(
            painter = painterResource(id = R.drawable.img_home_microgreens),
            contentDescription = stringResource(R.string.microgreens_image_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(Dimensions.onHomeImageSize)
                .clip(CircleShape)
                .border(
                    width = Dimensions.onHomeImageBorder, brush = borderBrush, shape = CircleShape
                )
        )

        Spacer(Modifier.height(Dimensions.medium))

        Text(
            text = uiState.cropName.ifEmpty { stringResource(R.string.unknown) },
            style = Typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(Dimensions.small))

        Box(modifier = Modifier.width(Dimensions.onHomeProgIndWidth)) {
            GradientProgressIndicator(
                progress = uiState.progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.micro),
                gradient = borderBrush,
                trackColor = MaterialTheme.custom.progressTrack
            )
        }

        Spacer(Modifier.height(Dimensions.small))

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.custom.sensorValue,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = Inter
                    )
                ) {
                    append("${uiState.daysGrown}")
                }
                append(
                    stringResource(
                        R.string.days_days_till_harvest,
                        uiState.totalDays,
                        uiState.daysRemaining
                    )
                )
            }, style = Typography.labelMedium, color = MaterialTheme.custom.counterDay
        )

        Spacer(Modifier.height(Dimensions.large))

        Row(modifier = Modifier.fillMaxWidth()) {
            SensorCard(
                title = stringResource(R.string.light),
                value = stringResource(R.string.sensor_value_percent, uiState.lightLevel),
                iconRes = R.drawable.ic_light,
                onClick = { onSensorClick(SensorType.LIGHT) },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(Dimensions.medium))
            SensorCard(
                title = stringResource(R.string.temperature),
                value = stringResource(R.string.sensor_value_tempetarure, uiState.temperature),
                iconRes = R.drawable.ic_temperature,
                onClick = { onSensorClick(SensorType.TEMPERATURE) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(Dimensions.medium))

        Row(modifier = Modifier.fillMaxWidth()) {
            SensorCard(
                title = stringResource(R.string.humidity),
                value = stringResource(R.string.sensor_value_percent, uiState.humidity),
                iconRes = R.drawable.ic_humidity,
                onClick = { onSensorClick(SensorType.HUMIDITY) },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(Dimensions.medium))
            SensorCard(
                title = stringResource(R.string.nutrition),
                value = stringResource(R.string.sensor_value_percent, uiState.nutritionLevel),
                iconRes = R.drawable.ic_nutrion,
                onClick = { onSensorClick(SensorType.NUTRITION) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(Dimensions.medium))

        Row(modifier = Modifier.fillMaxWidth()) {
            ControlCard(
                title = stringResource(R.string.vent),
                isChecked = uiState.isVentOn,
                iconRes = R.drawable.ic_vent,
                onCheckedChange = { onVentChanged(it) },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(Dimensions.medium))
            ControlCard(
                title = stringResource(R.string.watering),
                isChecked = uiState.isWateringOn,
                iconRes = R.drawable.ic_watering,
                onCheckedChange = { onWateringChanged(it) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(Dimensions.xLarge))
    }
}
