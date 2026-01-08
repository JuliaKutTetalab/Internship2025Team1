package com.arathort.growbox.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Black
import coil.compose.AsyncImage
import com.arathort.growbox.R
import com.arathort.growbox.ui.theme.Typography

@Composable
fun OnBoardingItem(
    page: OnBoardingPageData
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(page.title),
            style = Typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(page.image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(Dimensions.onBoardingImageHeight)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Dimensions.onBoardingImageSpacer))

        Text(
            text = stringResource(page.description),
            fontSize = Dimensions.textSizeBody,
            fontWeight = FontWeight.Medium,
            lineHeight = Dimensions.lineHeightBody,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(Dimensions.extraLarge))
    }
}