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
import com.arathort.growbox.presentation.chart.ChartScreen
import com.arathort.growbox.presentation.home.HomeScreen
import com.arathort.growbox.presentation.navigation.TabRoute
import com.arathort.growbox.presentation.profile.ProfileScreen
import com.arathort.growbox.presentation.settings.SettingsScreen

@Composable
fun MainScreen() {
    val tabStack = rememberNavBackStack(TabRoute.Home)
    val currentRoute = tabStack.lastOrNull() ?: TabRoute.Home

    val activeTabForBottomBar = if (currentRoute is TabRoute.Chart) TabRoute.Home else currentRoute

    Scaffold(
        bottomBar = {
            GrowBoxBottomBar(
                currentTab = activeTabForBottomBar as TabRoute,
                onNavigate = { newRoute ->
                    if (activeTabForBottomBar != newRoute) {
                        tabStack.clear()
                        tabStack.add(newRoute)
                    } else if (newRoute == TabRoute.Home) {
                        while (tabStack.size > 1) tabStack.removeLast()
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
                    TabRoute.Home -> NavEntry(key) {
                        HomeScreen(
                            onNavigateToDetail = { sensorType ->
                                tabStack.add(TabRoute.Chart(sensorType.name))
                            }
                        )
                    }
                    TabRoute.Settings -> NavEntry(key) { SettingsScreen() }
                    TabRoute.Profile -> NavEntry(key) { ProfileScreen() }

                    is TabRoute.Chart -> NavEntry(key) {
                        ChartScreen(
                            sensorTypeName = key.sensorType,
                            onNavigateBack = {
                                tabStack.removeLast()
                            }
                        )
                    }
                    else -> error("Unknown Tab: $key")
                }
            }
        )
    }
}