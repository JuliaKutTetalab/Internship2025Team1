package com.arathort.growbox.presentation.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.size.Dimension
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography

@Composable
fun LoginScreen() {
    LoginPage()
}

@Composable
private fun LoginPage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.drawable.ic_logo_auth),
            contentDescription = stringResource(R.string.splash_image_content_description),
            modifier = Modifier.size(Dimensions.iconSize)
        )

        Spacer(Modifier.height(Dimensions.medium))

        Text(
            modifier = Modifier,
            style = Typography.titleMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            text = stringResource(R.string.login_greeting)
        )
        Text(
            modifier = Modifier,
            style = Typography.titleSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            text = stringResource(R.string.login_subtitle)
        )




    }
}

@Preview
@Composable
private fun LoginPagePreview() {
    GrowBoxTheme {
        LoginPage()
    }
}