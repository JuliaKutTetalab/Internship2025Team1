package com.arathort.growbox.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.arathort.growbox.presentation.common.Ranges
import com.arathort.growbox.presentation.settings.components.cards.SettingCard
import com.arathort.growbox.presentation.settings.components.cards.SettingCardWithList
import com.arathort.growbox.presentation.settings.components.cards.SettingCardWithToggle
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
        if (uiState.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimensions.pagePadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            SettingsList(uiState, onEvent)
        }

        Spacer(Modifier.height(Dimensions.medium))
    }

}

@Composable
fun SettingsList(uiState: SettingsScreenUiState, onEvent: (SettingsScreenUiEvent) -> Unit) {
    SettingCardWithToggle(
        value = uiState.deviceSettings.ventDurationHours.toFloat(),
        icon = R.drawable.ic_vent,
        name = stringResource(R.string.vent),
        valueRange = Ranges.vent,
        unitsOfMeasurement = stringResource(R.string.unit_hours),
        onChange = { onEvent(SettingsScreenUiEvent.OnVentChange(it.toDouble())) },
        checked = uiState.deviceSettings.isVentAutomationEnabled,
        onCheckedChange = { onEvent(SettingsScreenUiEvent.TurnVent(it)) },
    )

    Spacer(Modifier.height(Dimensions.medium))

    SettingCardWithToggle(
        value = uiState.deviceSettings.lightDurationHours.toFloat(),
        icon = R.drawable.ic_light,
        name = stringResource(R.string.light),
        valueRange = Ranges.lightening,
        unitsOfMeasurement = stringResource(R.string.unit_hours),
        onChange = { onEvent(SettingsScreenUiEvent.OnLighteningChange(it.toDouble())) },
        checked = uiState.deviceSettings.isLightAutomationEnabled,
        onCheckedChange = { onEvent(SettingsScreenUiEvent.TurnLightening(it)) },
    )

    Spacer(Modifier.height(Dimensions.medium))

    SettingCard(
        value = uiState.deviceSettings.targetTemperature.toFloat(),
        icon = R.drawable.ic_temperature,
        name = stringResource(R.string.temperature),
        valueRange = Ranges.temperature,
        unitsOfMeasurement = stringResource(R.string.unit_celsius),
        onChange = { onEvent(SettingsScreenUiEvent.OnTemperatureChange(it.toDouble())) },
    )

    Spacer(Modifier.height(Dimensions.medium))

    SettingCard(
        value = uiState.deviceSettings.targetHumidity.toFloat(),
        icon = R.drawable.ic_humidity,
        name = stringResource(R.string.humidity),
        valueRange = Ranges.humidity,
        unitsOfMeasurement = stringResource(R.string.unit_percent),
        onChange = { onEvent(SettingsScreenUiEvent.OnHumidityChange(it.toDouble())) },
    )

    Spacer(Modifier.height(Dimensions.medium))

    SettingCardWithList(
        value = uiState.deviceSettings.nutritionTargetAmount.toFloat(),
        icon = R.drawable.ic_nutrion,
        name = stringResource(R.string.nutrition),
        valueRange = Ranges.nutrition,
        unitsOfMeasurement = stringResource(R.string.unit_mg),
        onChange = { onEvent(SettingsScreenUiEvent.OnNutritionChange(it.toDouble())) },
        frequencyIndex = uiState.deviceSettings.nutritionFrequencyIndex,
        onIndexChange = { onEvent(SettingsScreenUiEvent.OnNutritionFrequencyChange(it)) },
    )

    Spacer(Modifier.height(Dimensions.medium))

    SettingCardWithList(
        value = uiState.deviceSettings.wateringTargetAmount.toFloat(),
        icon = R.drawable.ic_watering,
        name = stringResource(R.string.watering),
        valueRange = Ranges.watering,
        unitsOfMeasurement = stringResource(R.string.unit_mg),
        onChange = { onEvent(SettingsScreenUiEvent.OnWateringChange(it.toDouble())) },
        frequencyIndex = uiState.deviceSettings.wateringFrequencyIndex,
        onIndexChange = { onEvent(SettingsScreenUiEvent.OnWateringFrequencyChange(it)) },
    )

}

@Preview
@Composable
private fun SettingsPagePreview() {
    GrowBoxTheme {
        SettingsPage(onEvent = {}, uiState = SettingsScreenUiState())
    }
}