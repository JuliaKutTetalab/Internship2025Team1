package com.arathort.growbox.presentation.harvest

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Green500
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography
import com.arathort.growbox.ui.theme.custom

@Composable
fun MyHarvestScreen(
    viewModel: MyHarvestScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    MyHarvestPage(
        uiState = uiState,
        onBackClick = onBackClick
    )
}

@Composable
private fun MyHarvestPage(uiState: MyHarvestScreenUiState, onBackClick: () -> Unit) {
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

            Text(text = stringResource(R.string.harvest_title), style = Typography.headlineSmall)
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
            if (uiState.crops.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                ) {
                    items(uiState.crops.size) {
                        Card(
                            shape = RoundedCornerShape(Dimensions.mediumRadius),
                            modifier = Modifier
                                .padding(Dimensions.micro)
                                .shadow(
                                    elevation = Dimensions.small,
                                    shape = RoundedCornerShape(Dimensions.mediumRadius),
                                ),
                            colors = CardDefaults.cardColors()
                                .copy(containerColor = MaterialTheme.custom.cardBackground)

                        ) {
                            Column(
                                modifier = Modifier.padding(Dimensions.small)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_home_microgreens),
                                    contentDescription = stringResource(R.string.microgreens_image_description),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(Dimensions.xxLarge)
                                        .clip(CircleShape)

                                )

                                Spacer(modifier = Modifier.height(Dimensions.micro))

                                Text(
                                    text = uiState.crops[it].name,
                                    style = Typography.bodyMedium,
                                    color = Green500,
                                    fontWeight = FontWeight.W700
                                )

                                Spacer(modifier = Modifier.height(Dimensions.micro))

                                Row {
                                    Text(
                                        text = stringResource(R.string.harvests),
                                        style = Typography.bodyMedium,
                                        fontWeight = FontWeight.W400
                                    )
                                    Spacer(modifier = Modifier.width(Dimensions.superMicro))
                                    Text(
                                        text = uiState.crops[it].id,
                                        style = Typography.bodyMedium,
                                        fontWeight = FontWeight.W700,
                                        color = Green800
                                    )

                                }

                                Spacer(modifier = Modifier.height(Dimensions.micro))

                                Row {
                                    Text(
                                        text = stringResource(R.string.total_days_number),
                                        style = Typography.bodyMedium,
                                        fontWeight = FontWeight.W400
                                    )
                                    Spacer(modifier = Modifier.width(Dimensions.superMicro))
                                    Text(
                                        text = uiState.crops[it].totalCycleDays.toString(),
                                        style = Typography.bodyMedium,
                                        fontWeight = FontWeight.W700,
                                        color = Green800
                                    )
                                }
                            }
                        }
                    }
                }

            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(R.string.no_harvests))
                }
            }

        }
    }
}

@Preview
@Composable
private fun MyHarvestPagePreview() {
    GrowBoxTheme {
        MyHarvestPage(
            uiState = MyHarvestScreenUiState(),
            onBackClick = {}
        )
    }
}