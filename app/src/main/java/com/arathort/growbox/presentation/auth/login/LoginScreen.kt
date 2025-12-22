package com.arathort.growbox.presentation.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.arathort.growbox.presentation.common.textfield.LoginEmailField
import com.arathort.growbox.presentation.common.textfield.LoginPasswordField
import com.arathort.growbox.presentation.navigation.Route
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Red
import com.arathort.growbox.ui.theme.Typography

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel(),
    backStack: NavBackStack<NavKey>
) {
    val uiState = loginScreenViewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            backStack.add(Route.Onboarding)
            backStack.remove(Route.Login)
        }
    }
    LoginPage(
        uiState = uiState,
        onEvent = loginScreenViewModel::onEvent,
        onSignUpClick = {
            backStack.add(Route.SignUp)
            backStack.remove(Route.Login)
        }
    )
}

@Composable
private fun LoginPage(
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    onSignUpClick: () -> Unit
) {
    Scaffold {innerPadding->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.ic_logo_auth),
                contentDescription = stringResource(R.string.splash_image_content_description),
                modifier = Modifier.size(Dimensions.iconSize)
            )

            Spacer(Modifier.height(Dimensions.large))

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

            Spacer(Modifier.height(Dimensions.large))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimensions.medium)
            ) {
                Text(
                    modifier = Modifier,
                    style = Typography.labelMedium,
                    text = stringResource(R.string.email)
                )

                Spacer(Modifier.height(Dimensions.small))

                LoginEmailField(
                    email = uiState.email,
                    isValid = uiState.isEmailValid,
                    errorId = uiState.emailError,
                    onValueChange = { onEvent(LoginUiEvent.OnEmailChanged(it)) }
                )

                Spacer(Modifier.height(Dimensions.medium))

                Text(
                    modifier = Modifier,
                    style = Typography.labelMedium,
                    text = stringResource(R.string.password)
                )
                Spacer(Modifier.height(Dimensions.small))

                LoginPasswordField(
                    password = uiState.password,
                    isVisible = uiState.isPasswordVisible,
                    errorId = uiState.passwordError,
                    onValueChange = { onEvent(LoginUiEvent.OnPasswordChanged(it)) },
                    onToggleVisibility = { onEvent(LoginUiEvent.OnTogglePasswordVisibilityClicked) }
                )

                Spacer(Modifier.height(Dimensions.large))

                if (uiState.firebaseErrorMessage != null) {
                    Spacer(modifier = Modifier.height(Dimensions.small))
                    Text(
                        text = uiState.firebaseErrorMessage,
                        color = Red,
                        style = Typography.bodySmall
                    )
                }

                GradientButton(
                    text = stringResource(R.string.sign_in),
                    onClick = { onEvent(LoginUiEvent.OnSignInClicked) },
                    enabled = !uiState.isLoading
                )


            }

            Spacer(modifier = Modifier.height(Dimensions.medium))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.login_no_account),
                    style = Typography.bodyMedium
                )
                Text(
                    text = stringResource(R.string.sign_up),
                    style = Typography.bodyMedium,
                    color = Green800,
                    modifier = Modifier.clickable(onClick = { onSignUpClick() })
                )

            }
        }

    }


}


@Preview
@Composable
private fun LoginPagePreview() {
    GrowBoxTheme {
        LoginPage(
            uiState = LoginUiState(),
            onEvent = {},
            onSignUpClick = {}
        )
    }
}