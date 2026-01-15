package com.arathort.growbox.presentation.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.arathort.growbox.R
import kotlinx.serialization.Serializable

sealed interface HomeUiEvent {
    data class onVentToggle(val isEnabled: Boolean) : HomeUiEvent
    data class onWateringToggle(val isEnabled: Boolean) : HomeUiEvent
    data class onSensorDetailClick(val sensorType: SensorType) : HomeUiEvent
}

@Serializable
enum class SensorType(
    @param:StringRes val title: Int,
    @param:DrawableRes val icon: Int,
    @param:StringRes val unit: Int,
    @param:StringRes val unitCalculate: Int
) {
    LIGHT(
        title = R.string.light,
        icon = R.drawable.ic_light,
        unit = R.string.unit_percent,
        unitCalculate = R.string.unit_kw
    ),
    TEMPERATURE(
        title = R.string.humidity,
        icon = R.drawable.ic_humidity,
        unit = R.string.unit_percent,
        unitCalculate = R.string.unit_ml
    ),
    HUMIDITY(
        title = R.string.temperature,
        icon = R.drawable.ic_temperature,
        unit = R.string.unit_celsius,
        unitCalculate = R.string.unit_kw
    ),
    NUTRITION(
        title = R.string.nutrition,
        icon = R.drawable.ic_nutrion,
        unit = R.string.unit_percent,
        unitCalculate = R.string.unit_mg
    )
}
