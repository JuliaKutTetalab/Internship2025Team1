package com.arathort.growbox.presentation.auth.signup

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
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    backStack: NavBackStack<NavKey>
) {
    val uiState = signUpViewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            backStack.add(Route.Login)
            backStack.remove(Route.SignUp)
        }
    }
    SignUpPage(
        uiState = uiState,
        onEvent = signUpViewModel::onEvent,
        onLoginClick = {
            backStack.add(Route.Login)
            backStack.remove(Route.SignUp)
        }
    )
}

@Composable
private fun SignUpPage(
    uiState: SignUpUiState,
    onEvent: (SignUpUiEvent) -> Unit,
    onLoginClick: () -> Unit
) {
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

        Spacer(Modifier.height(Dimensions.large))

        Text(
            modifier = Modifier,
            style = Typography.titleMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            text = stringResource(R.string.create_account)
        )
        Text(
            modifier = Modifier,
            style = Typography.titleSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            text = stringResource(R.string.sign_up_subtitle)
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
                onValueChange = { onEvent(SignUpUiEvent.OnEmailChanged(it)) }
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
                onValueChange = { onEvent(SignUpUiEvent.OnPasswordChanged(it)) },
                onToggleVisibility = { onEvent(SignUpUiEvent.OnTogglePasswordVisibilityClicked) }
            )

            Spacer(Modifier.height(Dimensions.medium))

            Text(
                modifier = Modifier,
                style = Typography.labelMedium,
                text = stringResource(R.string.confirm_password)
            )
            Spacer(Modifier.height(Dimensions.small))

            LoginPasswordField(
                password = uiState.confirmPassword,
                isVisible = uiState.isConfirmPasswordVisible,
                errorId = uiState.confirmPasswordError,
                onValueChange = { onEvent(SignUpUiEvent.OnConfirmPasswordChanged(it)) },
                onToggleVisibility = { onEvent(SignUpUiEvent.OnToggleConfirmPasswordVisibilityClicked) }
            )

            Spacer(Modifier.height(Dimensions.large))

            if (uiState.firebaseErrorMessage != null) {
                Text(
                    text = uiState.firebaseErrorMessage,
                    color = Red,
                    style = Typography.bodySmall
                )
            }

            if(!uiState.isSamePasswords){
                Text(
                    text = stringResource(R.string.error_passwords_different),
                    color = Red,
                    style = Typography.bodySmall
                )
            }

            Spacer(Modifier.height(Dimensions.large))

            GradientButton(
                text = stringResource(R.string.sign_up),
                onClick = { onEvent(SignUpUiEvent.OnSignUpClicked) },
                enabled = !uiState.isLoading
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.medium))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.have_account),
                style = Typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.login),
                style = Typography.bodyMedium,
                color = Green800,
                modifier = Modifier.clickable(onClick = { onLoginClick() })
            )

        }
    }
}

@Preview
@Composable
private fun SignUpPagePreview() {
    GrowBoxTheme {
        SignUpPage(
            uiState = SignUpUiState(),
            onEvent = {},
            onLoginClick = {}
        )
    }
}