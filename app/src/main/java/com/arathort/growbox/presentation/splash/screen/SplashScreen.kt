package com.arathort.growbox.presentation.splash.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1500)
        onNavigateToLogin()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                model = R.drawable.ic_logo_auth,
                contentDescription = stringResource(R.string.splash_image_content_description),
                modifier = Modifier.size(Dimensions.iconSize)
            )

            Spacer(modifier = Modifier.height(Dimensions.large))

            Text(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.splash_screen_greeting)
            )

            Spacer(modifier = Modifier.weight(2f))
        }

    }

}

@Composable
@Preview
private fun SplashScreenPreview() {
    SplashScreen(onNavigateToLogin = {})
}