package com.arathort.growbox.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route : NavKey {
    @Serializable
    data object Splash: Route

    @Serializable
    data object Login: Route

    @Serializable
    data object SignUp: Route

    @Serializable
    data object Onboarding: Route

    @Serializable
    data object DeviceConnection: Route

    @Serializable
    data object Searching: Route

    @Serializable
    data object Connecting: Route

    @Serializable
    data object CropTypeSelection: Route

    @Serializable
    data object Dashboard : Route


}
sealed interface TabRoute: NavKey{
    @Serializable
    data object Home: TabRoute

    @Serializable
    data object Settings: TabRoute

    @Serializable
    data object Profile: TabRoute
    @Serializable
    data class Chart(
        val sensorType: String
    ): TabRoute
}