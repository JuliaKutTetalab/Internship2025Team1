package com.arathort.growbox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.arathort.growbox.presentation.auth.login.LoginScreen
import com.arathort.growbox.presentation.auth.signup.SignUpScreen
import com.arathort.growbox.presentation.deviceconnection.DeviceConnectionScreen
import com.arathort.growbox.presentation.deviceconnection.connecting.ConnectingScreen
import com.arathort.growbox.presentation.deviceconnection.search.SearchingScreen
import com.arathort.growbox.presentation.deviceconnection.selection.SelectCropTypeScreen
import com.arathort.growbox.presentation.main.MainScreen
import com.arathort.growbox.presentation.onboarding.OnBoardingScreen
import com.arathort.growbox.presentation.splash.screen.SplashScreen

@Composable
fun NavigationRoot() {
    val backStack = rememberNavBackStack(Route.Splash)

    NavDisplay(
        backStack = backStack,
        modifier = Modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.Onboarding> {
                OnBoardingScreen(
                    onNavigateToConnection = {
                        backStack.add(Route.DeviceConnection)
                        backStack.remove(Route.Onboarding)
                    },
                    onNavigateToHome = {
                        backStack.add(Route.Dashboard)
                        backStack.remove(Route.Onboarding)
                    }
                )
            }
            entry<Route.Splash> {
                SplashScreen(
                    backStack = backStack
                )
            }
            entry<Route.Login> {
                LoginScreen(
                    backStack = backStack
                )
            }
            entry<Route.SignUp> {
                SignUpScreen(
                    backStack = backStack
                )
            }
            entry<Route.Dashboard> {
                MainScreen(
                    backStack = backStack
                )
            }
            entry<Route.DeviceConnection> {
                DeviceConnectionScreen(
                    onConnectClick = {
                        backStack.add(Route.Searching)
                    }
                )
            }
            entry<Route.Searching> {
                SearchingScreen(
                    backStack = backStack
                )
            }
            entry<Route.Connecting> {
                ConnectingScreen(backStack = backStack)
            }

            entry<Route.CropTypeSelection> {
                SelectCropTypeScreen(backStack = backStack)
            }
        }
    )
}