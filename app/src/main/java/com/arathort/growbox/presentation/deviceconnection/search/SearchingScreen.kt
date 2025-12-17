package com.arathort.growbox.presentation.deviceconnection.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.arathort.growbox.presentation.common.button.TransparentButton
import com.arathort.growbox.presentation.deviceconnection.components.PulsatingRadar
import com.arathort.growbox.presentation.navigation.Route
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Grey400
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun SearchingScreen(
    backStack: NavBackStack<NavKey>,
    viewModel: SearchingScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        delay(2000L)
        viewModel.onEvent(SearchingScreenUiEvent.DeviceFound)
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    SearchingPage(
        onBackClick = {
            backStack.remove(Route.Searching)
        },
        uiState = uiState
    )
}

@Composable
fun SearchingPage(
    onBackClick: () -> Unit,
    uiState: SearchingScreenUiState
) {
    Column {
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

        if (!uiState.isFound) {
            SearchingComponent(onBackClick)
        } else {
            ConnectedDevices(mockGrowBox = uiState.mockGrowBox)
        }
    }
}

@Composable
private fun SearchingComponent(onBackClick: () -> Unit) {
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
                text = stringResource(R.string.search_title)
            )

            Spacer(Modifier.height(Dimensions.medium))

            Text(
                modifier = Modifier,
                style = Typography.titleSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.search_subtitle)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            PulsatingRadar()
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
fun ConnectedDevices(mockGrowBox: MockGrowBox) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimensions.pagePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(Dimensions.medium))

        Text(
            modifier = Modifier,
            style = Typography.titleMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            text = stringResource(R.string.search_connect_device)
        )

        Spacer(Modifier.height(Dimensions.extraLarge))

        Card(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.cardHeight)
                .shadow(
                    elevation = Dimensions.micro,
                    shape = RoundedCornerShape(Dimensions.bigRadius),
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                ),
            shape = RoundedCornerShape(Dimensions.bigRadius),
            colors = CardDefaults.cardColors()
                .copy(containerColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Row(
                modifier = Modifier
                    .padding(Dimensions.medium)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.medium)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_groupbox),
                    contentDescription = stringResource(R.string.splash_image_content_description),
                    modifier = Modifier.size(Dimensions.mediumIconSize)
                )
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = mockGrowBox.name, style = Typography.bodyLarge)
                    Text(text = mockGrowBox.model, style = Typography.labelLarge, color = Green800)
                    Spacer(modifier = Modifier.height(Dimensions.small))
                    Text(
                        text = ("${stringResource(R.string.version)} ${mockGrowBox.version}"),
                        style = Typography.labelLarge,
                        color = Grey400
                    )
                }
            }
        }


    }
}

@Preview
@Composable
private fun SearchingPagePreview() {
    GrowBoxTheme {
        SearchingPage(
            onBackClick = {},
            uiState = SearchingScreenUiState(isFound = true)
        )
    }
}