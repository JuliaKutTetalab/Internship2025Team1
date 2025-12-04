package com.arathort.growbox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.arathort.growbox.presentation.auth.login.LoginScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Route.Login)

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when(key){
                is Route.Login ->{
                    NavEntry(key){
                        LoginScreen()
                    }
                }
                is Route.SignIn ->{
                    NavEntry(key){

                    }
                }
                is Route.Onboarding ->{
                    NavEntry(key){

                    }
                }
                is Route.Home ->{
                    NavEntry(key){

                    }
                }
                else -> error("Unknown NavKey: $key")
            }
        }
    )
}