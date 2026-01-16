package com.arathort.growbox.presentation.profile

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.arathort.growbox.presentation.deviceconnection.search.MockGrowBox
import com.arathort.growbox.presentation.navigation.Route
import com.arathort.growbox.ui.theme.Green500
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Red
import com.arathort.growbox.ui.theme.Typography
import com.arathort.growbox.ui.theme.custom

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    backStack: NavBackStack<NavKey>
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    ProfilePage(
        uiState,
        navigateToChangeCropType = { backStack.add(Route.ChangeCropType) },
        navigateToMyHarvest = { backStack.add(Route.MyHarvest) },
        navigateToHistoricData = { backStack.add(Route.HistoricData) },
        onLogout = {
            backStack.add(Route.Login)
        }
    )
}

@Composable
private fun ProfilePage(
    uiState: ProfileScreenUiState,
    navigateToChangeCropType: () -> Unit,
    navigateToMyHarvest: () -> Unit,
    navigateToHistoricData: () -> Unit,
    onLogout: () -> Unit
) {
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
            text = stringResource(R.string.profile)
        )

        Spacer(Modifier.height(Dimensions.mediumLarge))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = Dimensions.small,
                    shape = RoundedCornerShape(Dimensions.mediumRadius),
                )
                .height(Dimensions.bigCardHeight),
            shape = RoundedCornerShape(Dimensions.mediumRadius),
            colors = CardDefaults.cardColors()
                .copy(containerColor = MaterialTheme.custom.cardBackground)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimensions.medium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_logo_auth),
                    contentDescription = null,
                    modifier = Modifier.size(
                        Dimensions.smallIconSize
                    ),
                    tint = Green800
                )

                Spacer(Modifier.height(Dimensions.superMicro))

                Text(
                    text = MockGrowBox().model,
                    style = Typography.bodyMedium,
                    color = Green800,
                    fontWeight = FontWeight.W700
                )

                Spacer(Modifier.height(Dimensions.superMicro))

                Text(
                    text = uiState.userProfile.email,
                    style = Typography.titleSmall,
                    fontWeight = FontWeight.W400
                )

                Spacer(Modifier.height(Dimensions.large))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.total_harvest),
                            style = Typography.labelSmall,
                        )
                        Spacer(Modifier.height(Dimensions.superMicro))

                        Text(
                            text = uiState.userProfile.totalHarvestsCount.toString(),
                            style = Typography.bodyMedium,
                            fontWeight = FontWeight.W700,
                            color = Green500
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.crop_type),
                            style = Typography.labelSmall,
                        )
                        Spacer(Modifier.height(Dimensions.superMicro))

                        Text(
                            text = uiState.deviceState.activeCropName,
                            style = Typography.bodyMedium,
                            fontWeight = FontWeight.W700,
                            color = Green500
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.total_days),
                            style = Typography.labelSmall,
                        )
                        Spacer(Modifier.width(Dimensions.superMicro))

                        Text(
                            text = uiState.userProfile.totalDaysActive.toString(),
                            style = Typography.bodyMedium,
                            fontWeight = FontWeight.W700,
                            color = Green500
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.extraLarge))

        NavigationCard(
            onClick = navigateToChangeCropType,
            icon = R.drawable.ic_change,
            text = stringResource(R.string.change_crop_type)
        )

        Spacer(modifier = Modifier.height(Dimensions.medium))

        NavigationCard(
            onClick = navigateToMyHarvest,
            icon = R.drawable.ic_harvest,
            text = stringResource(R.string.my_harvest)
        )

        Spacer(modifier = Modifier.height(Dimensions.medium))

        NavigationCard(
            onClick = navigateToHistoricData,
            icon = R.drawable.ic_history,
            text = stringResource(R.string.historic_data)
        )

        Spacer(modifier = Modifier.height(Dimensions.medium))

        NavigationCard(
            onClick = onLogout,
            icon = R.drawable.ic_logout,
            text = stringResource(R.string.logout),
            isLogOut = true
        )
    }
}

@Composable
private fun NavigationCard(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    text: String,
    isLogOut: Boolean = false
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = Dimensions.small,
                shape = RoundedCornerShape(Dimensions.mediumRadius),
            )
            .height(Dimensions.smallCardHeight),
        shape = RoundedCornerShape(Dimensions.mediumRadius),
        colors = CardDefaults.cardColors()
            .copy(containerColor = MaterialTheme.custom.cardBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.micro),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = if (isLogOut) Red else Green800
                )

                Spacer(Modifier.size(Dimensions.micro))

                Text(
                    text = text,
                    style = Typography.bodyMedium,
                    fontWeight = FontWeight.W400,
                    color = if (isLogOut) Red else Color.Unspecified
                )
            }

            Icon(
                painter = painterResource(R.drawable.ic_arrow),
                contentDescription = null,
                tint = Green800
            )

        }

    }
}

@Preview
@Composable
private fun ProfilePagePreview() {
    GrowBoxTheme {
        ProfilePage(
            uiState = ProfileScreenUiState(), navigateToChangeCropType = {},
            navigateToMyHarvest = {},
            navigateToHistoricData = {},
            onLogout = {}
        )
    }
}