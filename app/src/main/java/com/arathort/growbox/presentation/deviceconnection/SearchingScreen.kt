package com.arathort.growbox.presentation.deviceconnection

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.common.button.TransparentButton
import com.arathort.growbox.presentation.deviceconnection.components.PulsatingRadar
import com.arathort.growbox.presentation.navigation.Route
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography

@Composable
fun SearchingScreen(
    backStack: NavBackStack<NavKey>
) {
    SearchingPage(
        onBackClick = {
            backStack.remove(Route.Searching)
        }
    )
}

@Composable
fun SearchingPage(
    onBackClick: () -> Unit
) {
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
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { onBackClick() }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }

            }

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

            PulsatingRadar()

        }

        Column {
            TransparentButton(
                text = stringResource(R.string.cancel),
                onClick = { onBackClick() }
            )
            Spacer(Modifier.height(Dimensions.medium))
        }
    }
}

@Preview
@Composable
private fun SearchingPagePreview() {
    GrowBoxTheme {
        SearchingPage(
            onBackClick = {}
        )
    }
}