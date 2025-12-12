package com.arathort.growbox.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route: NavKey {
    @Serializable
    data object Splash: Route, NavKey
    @Serializable
    data object Login: Route, NavKey

    @Serializable
    data object SignUp: Route, NavKey

    @Serializable
    data object Onboarding: Route, NavKey
    data object DeviceConnection: Route, NavKey

    @Serializable
    data object Home: Route, NavKey
}