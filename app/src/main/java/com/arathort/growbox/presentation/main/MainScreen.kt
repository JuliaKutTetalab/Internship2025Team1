package com.arathort.growbox.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.arathort.growbox.presentation.home.HomeScreen
import com.arathort.growbox.presentation.navigation.TabRoute
import com.arathort.growbox.presentation.profile.ProfileScreen
import com.arathort.growbox.presentation.settings.SettingsScreen

@Composable
fun MainScreen() {
    val tabStack = rememberNavBackStack(TabRoute.Home)

    val currentTab = tabStack.lastOrNull() ?: TabRoute.Home

    Scaffold(
        bottomBar = {
            GrowBoxBottomBar(
                currentTab = currentTab as TabRoute,
                onNavigate = { newRoute ->
                    if (currentTab != newRoute) {
                        tabStack[tabStack.size - 1] = newRoute
                    }
                }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = tabStack,
            modifier = Modifier.padding(innerPadding),
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = { key ->
                when(key) {
                    TabRoute.Home -> NavEntry(key) { HomeScreen() }
                    TabRoute.Settings -> NavEntry(key) { SettingsScreen() }
                    TabRoute.Profile -> NavEntry(key) { ProfileScreen() }
                    else -> error("Unknown Tab: $key")
                }
            }
        )
    }
}
