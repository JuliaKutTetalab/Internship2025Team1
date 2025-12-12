package com.arathort.growbox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.arathort.growbox.presentation.auth.login.LoginScreen
import com.arathort.growbox.presentation.deviceconnection.DeviceConnectionScreen
import com.arathort.growbox.presentation.onboarding.OnBoardingScreen
import com.arathort.growbox.presentation.splash.screen.SplashScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Route.Splash)

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when(key){
                is Route.Splash ->{
                    NavEntry(key){
                        SplashScreen(onNavigateToLogin={
                            backStack.add(Route.Login)
                            backStack.remove(Route.Splash)
                        })
                    }
                }
                is Route.Login ->{
                    NavEntry(key){
                        LoginScreen()
                    }
                }
                is Route.SignUp ->{
                    NavEntry(key){

                    }
                }
                is Route.Onboarding ->{
                    NavEntry(key){
                        OnBoardingScreen(
                            onNavigateToConnection = {
                                backStack.add(Route.DeviceConnection)
                                backStack.remove(Route.Onboarding)
                            }
                        )
                    }
                }
                is Route.Home ->{
                    NavEntry(key){

                    }
                }
                is Route.DeviceConnection ->{
                    NavEntry(key){
                        DeviceConnectionScreen()
                    }
                }
                else -> error("Unknown NavKey: $key")
            }
        }
    )
}