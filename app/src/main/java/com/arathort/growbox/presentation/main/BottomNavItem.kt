package com.arathort.growbox.presentation.main

import androidx.annotation.DrawableRes
import com.arathort.growbox.R // Переконайся, що імпортуєш свій R
import com.arathort.growbox.presentation.navigation.TabRoute

sealed class BottomNavItem(
    val route: TabRoute,
    @DrawableRes val iconRes: Int,
    val label: String
) {
    data object Home : BottomNavItem(TabRoute.Home, R.drawable.ic_home, "Home")
    data object Settings : BottomNavItem(TabRoute.Settings, R.drawable.ic_settings, "Settings")
    data object Profile : BottomNavItem(TabRoute.Profile, R.drawable.ic_profile, "Profile")
}