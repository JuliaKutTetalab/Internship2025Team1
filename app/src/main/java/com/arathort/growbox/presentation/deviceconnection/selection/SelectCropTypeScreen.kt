package com.arathort.growbox.presentation.deviceconnection.selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.arathort.growbox.presentation.deviceconnection.components.CropWheelPicker
import com.arathort.growbox.presentation.navigation.Route
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography

@Composable
fun SelectCropTypeScreen(
    backStack: NavBackStack<NavKey>,
    viewModel: SelectCropTypeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(uiState.isSuccess) {
        if(uiState.isSuccess){
            backStack.clear()
            backStack.add(Route.Dashboard)
        }
    }
    SelectCropTypePage(
        uiState = uiState,
        onBackClick = { backStack.remove(Route.CropTypeSelection) },
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun SelectCropTypePage(
    uiState: SelectCropTypeUiState,
    onBackClick: () -> Unit,
    onEvent: (SelectCropUiEvent) -> Unit
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
                        text = stringResource(R.string.selection_title)
                    )

                    Spacer(Modifier.height(Dimensions.large))
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if(uiState.cropTypes.isNotEmpty()){
                        CropWheelPicker(
                            items = uiState.cropTypes,
                            onSelectionChanged = { newItem ->
                                onEvent(SelectCropUiEvent.OnCropChange(newItem))
                            }
                        )
                    }
                }


                Column {
                    GradientButton(
                        text = stringResource(R.string.selection_button_text),
                        onClick = { onEvent(SelectCropUiEvent.SaveDevice) }
                    )
                    Spacer(Modifier.height(Dimensions.large))
                }
            }
        }
    }
}

@Preview
@Composable
private fun SelectCropTypePagePreview() {
    GrowBoxTheme {
        SelectCropTypePage(
            uiState = SelectCropTypeUiState(),
            onBackClick = {},
            onEvent = {})
    }
}