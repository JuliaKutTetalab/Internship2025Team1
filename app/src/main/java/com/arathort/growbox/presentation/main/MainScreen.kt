package com.arathort.growbox.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.arathort.growbox.presentation.changeCrop.ChangeCropTypeScreen
import com.arathort.growbox.presentation.chart.ChartScreen
import com.arathort.growbox.presentation.harvest.MyHarvestScreen
import com.arathort.growbox.presentation.history.HistoryScreen
import com.arathort.growbox.presentation.home.HomeScreen
import com.arathort.growbox.presentation.navigation.TabRoute
import com.arathort.growbox.presentation.profile.ProfileScreen
import com.arathort.growbox.presentation.settings.SettingsScreen

@Composable
fun MainScreen(backStack: NavBackStack<NavKey>) {
    val tabStack = rememberNavBackStack(TabRoute.Home)

    val currentRoute = tabStack.lastOrNull() ?: TabRoute.Home

    val activeTabForBottomBar = if (currentRoute is TabRoute.Chart) TabRoute.Home else currentRoute

    Scaffold(
        bottomBar = {
            GrowBoxBottomBar(
                currentTab = activeTabForBottomBar as TabRoute,
                onNavigate = { newRoute ->
                    if (activeTabForBottomBar != newRoute) {
                        tabStack.add(newRoute)
                    } else if (newRoute == TabRoute.Home) {
                        while (tabStack.size > 1) tabStack.removeAt(tabStack.lastIndex)
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
            entryProvider = entryProvider {

                entry<TabRoute.Home> {
                    HomeScreen(
                        onNavigateToDetail = { sensorType ->
                            tabStack.add(TabRoute.Chart(sensorType.name))
                        }
                    )
                }

                entry<TabRoute.Settings> { SettingsScreen() }

                entry<TabRoute.Profile> {
                    ProfileScreen(
                        tabStack = tabStack,
                        backStack = backStack
                    )
                }

                entry<TabRoute.Chart> { key ->
                    ChartScreen(
                        sensorTypeName = key.sensorType,
                        onNavigateBack = {
                            tabStack.removeAt(tabStack.lastIndex)
                        }
                    )
                }

                entry<TabRoute.ChangeCropType> {
                    ChangeCropTypeScreen()
                }

                entry<TabRoute.MyHarvest> {
                    MyHarvestScreen(onBackClick = { tabStack.remove(TabRoute.MyHarvest) })
                }

                entry<TabRoute.HistoricData> {
                    HistoryScreen()
                }
            }
        )
    }
}