package com.arathort.growbox.presentation.changeCrop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Grey400
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography

@Composable
fun ChangeCropTypeScreen(
    viewModel: ChangeCropScreenViewModel = hiltViewModel(),
    onChangeClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    ChangeCropPage(
        uiState = uiState,
        onChangeClick = onChangeClick,
        onBackClick = onBackClick
    )
}

@Composable
private fun ChangeCropPage(
    uiState: ChangeCropScreenUiState,
    onChangeClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimensions.pagePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackClick() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }

            Text(
                text = stringResource(R.string.change_crop_title),
                style = Typography.headlineSmall
            )
        }

        if (uiState.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Text(
                text = stringResource(R.string.change_crop_subtitle),
                style = Typography.bodyMedium.copy(fontWeight = FontWeight.W400)
            )

            Spacer(modifier = Modifier.height(Dimensions.mediumLarge))

            Text(
                text = stringResource(R.string.current_crop),
                style = Typography.titleLarge.copy(color = Grey400, fontWeight = FontWeight.W400)
            )
        }
    }
}

@Preview
@Composable
private fun ChangeCropPagePreview() {
    GrowBoxTheme {
        ChangeCropPage(
            uiState = ChangeCropScreenUiState(),
            onChangeClick = {},
            onBackClick = {}
        )
    }
}