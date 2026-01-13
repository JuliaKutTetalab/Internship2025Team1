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
    SettingsPage(
        onEvent = viewModel::onEvent,
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value
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

        SettingCardWithToogle(
            value = uiState.deviceSettings.ventDurationHours.toFloat(),
            icon = R.drawable.ic_vent,
            name = stringResource(R.string.vent),
            valueRange = 1f..24f,
            unitsOfMeasurement = "h",
            onChange = {},
            checked = uiState.deviceSettings.isVentAutomationEnabled,
            onCheckedChange = {},
        )

        Spacer(Modifier.height(Dimensions.medium))

        SettingCardWithToogle(
            value = uiState.deviceSettings.lightDurationHours.toFloat(),
            icon = R.drawable.ic_light,
            name = stringResource(R.string.light),
            valueRange = 1f..16f,
            unitsOfMeasurement = "h",
            onChange = {},
            checked = uiState.deviceSettings.isLightAutomationEnabled,
            onCheckedChange = {},
        )

        Spacer(Modifier.height(Dimensions.medium))

        SettingCard(
            value = uiState.deviceSettings.targetTemperature.toFloat(),
            icon = R.drawable.ic_temperature,
            name = stringResource(R.string.temperature),
            valueRange = 10f..36f,
            unitsOfMeasurement = "`C",
            onChange = {},
        )

        Spacer(Modifier.height(Dimensions.medium))

        SettingCard(
            value = uiState.deviceSettings.targetHumidity.toFloat(),
            icon = R.drawable.ic_humidity,
            name = stringResource(R.string.humidity),
            valueRange = 0f..100f,
            unitsOfMeasurement = "%",
            onChange = {},
        )

        Spacer(Modifier.height(Dimensions.medium))

        SettingCardWithList(
            value = uiState.deviceSettings.nutritionTargetAmount.toFloat(),
            icon = R.drawable.ic_nutrion,
            name = stringResource(R.string.nutrition),
            valueRange = 0f..500f,
            unitsOfMeasurement = "mg",
            onChange = {},
            frequencyIndex = uiState.deviceSettings.nutritionFrequencyIndex,
            onIndexChange = {},
        )

        Spacer(Modifier.height(Dimensions.medium))

        SettingCardWithList(
            value = uiState.deviceSettings.wateringTargetAmount.toFloat(),
            icon = R.drawable.ic_watering,
            name = stringResource(R.string.watering),
            valueRange = 0f..500f,
            unitsOfMeasurement = "mg",
            onChange = {},
            frequencyIndex = uiState.deviceSettings.wateringFrequencyIndex,
            onIndexChange = {},
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