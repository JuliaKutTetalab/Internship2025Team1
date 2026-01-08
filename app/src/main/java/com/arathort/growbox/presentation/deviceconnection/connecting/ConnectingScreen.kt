package com.arathort.growbox.presentation.deviceconnection.connecting

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.common.button.GradientButton
import com.arathort.growbox.presentation.common.button.TransparentButton
import com.arathort.growbox.presentation.deviceconnection.search.MockGrowBox
import com.arathort.growbox.presentation.navigation.Route
import com.arathort.growbox.ui.theme.Green200
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Grey400
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography

@Composable
fun ConnectingScreen(
    viewModel: ConnectingScreenViewModel = hiltViewModel(),
    backStack: NavBackStack<NavKey>
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
        )
        viewModel.onEvent(ConnectingScreenUiEvent.DeviceConnected)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ConnectingPage(
        uiState,
        onBackClick = { backStack.remove(Route.Connecting) },
        progress = progress.value,
        onNextClick = { backStack.add(Route.CropTypeSelection) }
    )
}

@Composable
private fun ConnectingPage(
    uiState: ConnectingScreenUiState,
    onBackClick: () -> Unit,
    progress: Float,
    onNextClick: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimensions.pagePadding)
            ) {
                IconButton(
                    onClick = { onBackClick() }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }

            }

            if (!uiState.isConnected) {
                ConnectingProcessPart(onBackClick, growBox = uiState.growBox, progress = progress)
            } else {
                ConnectedDevice(growBox = uiState.growBox, onNextClick = onNextClick)
            }
        }
    }
}

@Composable
private fun ConnectingProcessPart(onBackClick: () -> Unit, growBox: MockGrowBox, progress: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimensions.pagePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(Modifier.height(Dimensions.medium))

            Text(
                modifier = Modifier,
                style = Typography.titleMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.connecting)
            )

            Spacer(Modifier.height(Dimensions.medium))

            Text(
                modifier = Modifier,
                style = Typography.titleSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.search_subtitle)
            )

            Spacer(Modifier.height(Dimensions.large))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.progressBarHeight)
                    .clip(RoundedCornerShape(Dimensions.mediumRadius)),
                color = Green800,
                trackColor = Green200,
            )

            Spacer(Modifier.height(Dimensions.large))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = growBox.name,
                    style = Typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = growBox.model,
                    style = Typography.labelLarge,
                    color = Green800,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(Dimensions.small))
                Text(
                    text = ("${stringResource(R.string.version)} ${growBox.version}"),
                    style = Typography.labelLarge,
                    color = Grey400,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(Dimensions.large))

            Image(
                painter = painterResource(R.drawable.ic_groupbox),
                contentDescription = stringResource(R.string.splash_image_content_description),
                modifier = Modifier.size(Dimensions.largeIconSize)
            )

        }


        Column {
            TransparentButton(
                text = stringResource(R.string.cancel),
                onClick = { onBackClick() }
            )
            Spacer(Modifier.height(Dimensions.large))
        }
    }
}

@Composable
private fun ConnectedDevice(growBox: MockGrowBox, onNextClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimensions.pagePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(Modifier.height(Dimensions.medium))

            Text(
                modifier = Modifier,
                style = Typography.titleMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.connecting_connected)
            )

            Spacer(Modifier.height(Dimensions.large))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = growBox.name,
                    style = Typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = growBox.model,
                    style = Typography.labelLarge,
                    color = Green800,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(Dimensions.small))
                Text(
                    text = ("${stringResource(R.string.version)} ${growBox.version}"),
                    style = Typography.labelLarge,
                    color = Grey400,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(Dimensions.large))

            Image(
                painter = painterResource(R.drawable.ic_groupbox),
                contentDescription = stringResource(R.string.splash_image_content_description),
                modifier = Modifier.size(Dimensions.largeIconSize)
            )

        }


        Column {
            GradientButton(
                text = stringResource(R.string.connecting_next),
                onClick = { onNextClick() }
            )
            Spacer(Modifier.height(Dimensions.large))
        }
    }
}

@Preview
@Composable
private fun ConnectingPagePreview() {
    GrowBoxTheme {
        ConnectingPage(
            uiState = ConnectingScreenUiState(),
            onBackClick = {},
            progress = 0f,
            onNextClick = {}
        )
    }
}