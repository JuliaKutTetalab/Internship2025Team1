package com.arathort.growbox.presentation.deviceconnection

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.common.button.GradientButton
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography

@Composable
fun DeviceConnectionScreen(onConnectClick: () -> Unit) {
    DeviceConnectionPage(onConnectClick)
}

@Composable
private fun DeviceConnectionPage(onConnectClick: () -> Unit) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.pagePadding)
                .padding(innerPadding),
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
                    text = stringResource(R.string.connection_title)
                )

                Spacer(Modifier.height(Dimensions.medium))

                Text(
                    modifier = Modifier,
                    style = Typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(R.string.connection_subtitle)
                )

                Spacer(Modifier.height(Dimensions.xxLarge))

                Image(
                    painter = painterResource(R.drawable.ic_groupbox),
                    contentDescription = stringResource(R.string.splash_image_content_description),
                    modifier = Modifier.size(Dimensions.largeIconSize)
                )

            }

            Column {
                GradientButton(
                    text = stringResource(R.string.connection_button_connect),
                    onClick = { onConnectClick() })
                Spacer(Modifier.height(Dimensions.medium))
            }
        }

    }
}

@Preview
@Composable
private fun DeviceConnectionPagePreview() {
    GrowBoxTheme {
        DeviceConnectionPage(onConnectClick = {})
    }
}