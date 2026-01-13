package com.arathort.growbox.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.settings.components.cards.SettingCard
import com.arathort.growbox.presentation.settings.components.cards.SettingCardWithList
import com.arathort.growbox.presentation.settings.components.cards.SettingCardWithToogle
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    SettingsPage(
        onEvent = viewModel::onEvent,
        uiState = uiState
    )
}

@Composable
private fun SettingsPage(onEvent: (SettingsScreenUiEvent) -> Unit, uiState: SettingsScreenUiState) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = Dimensions.pagePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(Dimensions.medium))

        Text(
            modifier = Modifier,
            style = Typography.titleMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            text = stringResource(R.string.settings)
        )

        Spacer(Modifier.height(Dimensions.mediumLarge))

        var sliderValueVent by remember { mutableFloatStateOf(uiState.deviceSettings.ventDurationHours.toFloat()) }
        var switchValueVent by remember { mutableStateOf(uiState.deviceSettings.isVentAutomationEnabled) }

        SettingCardWithToogle(
            value = sliderValueVent,
            icon = R.drawable.ic_vent,
            name = stringResource(R.string.vent),
            valueRange = 1f..24f,
            unitsOfMeasurement = "h",
            onChange = { onEvent(SettingsScreenUiEvent.OnVentChange(it.toDouble())) },
            checked = switchValueVent,
            onCheckedChange = { onEvent(SettingsScreenUiEvent.TurnVent(it)) },
        )

        Spacer(Modifier.height(Dimensions.medium))

        var sliderValueLight by remember { mutableFloatStateOf(uiState.deviceSettings.lightDurationHours.toFloat()) }
        var switchValueLight by remember { mutableStateOf(uiState.deviceSettings.isLightAutomationEnabled) }

        SettingCardWithToogle(
            value = sliderValueLight,
            icon = R.drawable.ic_light,
            name = stringResource(R.string.light),
            valueRange = 1f..16f,
            unitsOfMeasurement = "h",
            onChange = { onEvent(SettingsScreenUiEvent.OnLighteningChange(it.toDouble())) },
            checked = switchValueLight,
            onCheckedChange = { onEvent(SettingsScreenUiEvent.TurnLightening(it)) },
        )

        Spacer(Modifier.height(Dimensions.medium))

        var sliderValueTemperature by remember { mutableFloatStateOf(uiState.deviceSettings.targetTemperature.toFloat()) }

        SettingCard(
            value = sliderValueTemperature,
            icon = R.drawable.ic_temperature,
            name = stringResource(R.string.temperature),
            valueRange = 10f..36f,
            unitsOfMeasurement = "`C",
            onChange = { onEvent(SettingsScreenUiEvent.OnTemperatureChange(it.toDouble())) },
        )

        Spacer(Modifier.height(Dimensions.medium))

        var sliderValueHumidity by remember { mutableFloatStateOf(uiState.deviceSettings.targetHumidity.toFloat()) }

        SettingCard(
            value = sliderValueHumidity,
            icon = R.drawable.ic_humidity,
            name = stringResource(R.string.humidity),
            valueRange = 0f..100f,
            unitsOfMeasurement = "%",
            onChange = { onEvent(SettingsScreenUiEvent.OnHumidityChange(it.toDouble())) },
        )

        Spacer(Modifier.height(Dimensions.medium))

        var sliderValueNutrition by remember { mutableFloatStateOf(uiState.deviceSettings.nutritionTargetAmount.toFloat()) }
        var currentFrequencyIndexNutrition by remember { mutableIntStateOf(uiState.deviceSettings.nutritionFrequencyIndex) }

        SettingCardWithList(
            value = sliderValueNutrition,
            icon = R.drawable.ic_nutrion,
            name = stringResource(R.string.nutrition),
            valueRange = 0f..500f,
            unitsOfMeasurement = "mg",
            onChange = { onEvent(SettingsScreenUiEvent.OnNutritionChange(it.toDouble())) },
            frequencyIndex = currentFrequencyIndexNutrition,
            onIndexChange = { onEvent(SettingsScreenUiEvent.OnNutritionFrequencyChange(it)) },
        )

        Spacer(Modifier.height(Dimensions.medium))

        var sliderValueWatering by remember { mutableFloatStateOf(uiState.deviceSettings.wateringTargetAmount.toFloat()) }
        var currentFrequencyIndexWatering by remember { mutableIntStateOf(uiState.deviceSettings.wateringFrequencyIndex) }

        SettingCardWithList(
            value = sliderValueWatering,
            icon = R.drawable.ic_watering,
            name = stringResource(R.string.watering),
            valueRange = 0f..500f,
            unitsOfMeasurement = "mg",
            onChange = { onEvent(SettingsScreenUiEvent.OnWateringChange(it.toDouble())) },
            frequencyIndex = currentFrequencyIndexWatering,
            onIndexChange = { onEvent(SettingsScreenUiEvent.OnWateringFrequencyChange(it)) },
        )

    }

}

@Preview
@Composable
private fun SettingsPagePreview() {
    GrowBoxTheme {
        SettingsPage(onEvent = {}, uiState = SettingsScreenUiState())
    }
}