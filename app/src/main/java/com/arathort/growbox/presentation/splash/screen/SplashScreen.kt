package com.arathort.growbox.presentation.splash.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.navigation.Route
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    backStack: NavBackStack<NavKey>
) {
    LaunchedEffect(Unit) {
        splashViewModel.effect.collectLatest { effect ->
            when (effect) {
                is SplashEffect.NavigateToHome -> {
                    backStack.add(Route.Dashboard)
                    backStack.remove(Route.Splash)
                }
                is SplashEffect.NavigateToLogin -> {
                    backStack.add(Route.Login)
                    backStack.remove(Route.Splash)
                }

                SplashEffect.NavigateToConnection -> {
                    backStack.add(Route.DeviceConnection)
                    backStack.remove(Route.Splash)
                }
            }
        }
    }

    SplashPage()
}

@Composable
private fun SplashPage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(R.drawable.ic_logo_auth),
            contentDescription = stringResource(R.string.splash_image_content_description),
            modifier = Modifier.size(Dimensions.iconSize)
        )

        Spacer(modifier = Modifier.height(Dimensions.large))

        Text(
            style = Typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            text = stringResource(R.string.splash_screen_greeting)
        )

        Spacer(modifier = Modifier.weight(2f))
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPagePreview() {
    GrowBoxTheme {
        SplashPage()
    }
}