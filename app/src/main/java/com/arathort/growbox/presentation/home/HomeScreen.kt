package com.arathort.growbox.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.common.card.ControlCard
import com.arathort.growbox.presentation.home.components.GradientProgressIndicator
import com.arathort.growbox.presentation.common.card.SensorCard
import com.arathort.growbox.ui.theme.*

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    HomePage(uiState, onVentChanged = { isEnabled ->
        homeViewModel.onEvent(HomeUiEvent.onVentToggle(isEnabled))
    }, onWateringChanged = { isEnabled ->
        homeViewModel.onEvent(HomeUiEvent.onWateringToggle(isEnabled))
    })
}

@Composable
fun HomePage(
    uiState: HomeUiState,
    onVentChanged: (Boolean) -> Unit,
    onWateringChanged: (Boolean) -> Unit,
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
                    ))
            }, style = Typography.labelMedium, color = MaterialTheme.custom.counterDay
        )

        Spacer(Modifier.height(Dimensions.large))

        Row(modifier = Modifier.fillMaxWidth()) {
            SensorCard(
                title = stringResource(R.string.light),
                value = stringResource(R.string.sensor_value_percent, uiState.lightLevel),
                iconRes = R.drawable.ic_light,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(Dimensions.medium))
            SensorCard(
                title = stringResource(R.string.temperature),
                value = stringResource(R.string.sensor_value_tempetarure, uiState.temperature),
                iconRes = R.drawable.ic_temperature,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(Dimensions.medium))

        Row(modifier = Modifier.fillMaxWidth()) {
            SensorCard(
                title = stringResource(R.string.humidity),
                value = stringResource(R.string.sensor_value_percent,uiState.humidity),
                iconRes = R.drawable.ic_humidity,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(Dimensions.medium))
            SensorCard(
                title = stringResource(R.string.nutrition),
                value = stringResource(R.string.sensor_value_percent,uiState.nutritionLevel),
                iconRes = R.drawable.ic_nutrion,
                onClick = {},
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

@Preview(
    name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun HomePagePreview() {
    GrowBoxTheme {
        HomePage(
            uiState = HomeUiState(
            isLoading = false,
            cropName = "Microgreens",
            daysGrown = 12,
            totalDays = 21,
            temperature = 24,
            humidity = 58,
            lightLevel = 60,
            nutritionLevel = 78,
            isVentOn = true,
            isWateringOn = true,
            progress = 0.57f,
            connectionStatus = ConnectionStatus.CONNECTED
        ), onVentChanged = {}, onWateringChanged = {})
    }
}