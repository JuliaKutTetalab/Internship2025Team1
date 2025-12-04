package com.arathort.growbox.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route: NavKey {
    @Serializable
    data object Login: Route, NavKey

    @Serializable
    data object SignIn: Route, NavKey

    @Serializable
    data object Onboarding: Route, NavKey

    @Serializable
    data object Home: Route, NavKey
}