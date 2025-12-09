package com.arathort.growbox.presentation.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Green500
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Grey300
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography
import com.arathort.growbox.ui.theme.White

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel()
) {
    val uiState = loginScreenViewModel.uiState.collectAsStateWithLifecycle().value
    LoginPage(uiState = uiState, onEvent = loginScreenViewModel::onEvent)
}

@Composable
private fun LoginPage(uiState: LoginUiState, onEvent: (LoginUiEvent) -> Unit) {
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

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onEvent(LoginUiEvent.OnEmailChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.textFieldHeight),
                textStyle = Typography.bodyMedium,
                shape = RoundedCornerShape(Dimensions.small),
                colors = OutlinedTextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Green800,
                    unfocusedIndicatorColor = Green800
                ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = "check",
                        tint = Green800
                    )
                }
            )

            Spacer(Modifier.height(Dimensions.medium))

            Text(
                modifier = Modifier,
                style = Typography.labelMedium,
                text = stringResource(R.string.password)
            )
            Spacer(Modifier.height(Dimensions.small))


            OutlinedTextField(
                value = uiState.password,
                onValueChange = { onEvent(LoginUiEvent.OnPasswordChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.textFieldHeight),
                textStyle = Typography.bodyMedium,
                shape = RoundedCornerShape(Dimensions.small),
                colors = OutlinedTextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Green800,
                    unfocusedIndicatorColor = Green800
                ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_eye_off),
                        contentDescription = "check",
                        tint = Green800
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(Modifier.height(Dimensions.large))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.buttonHeight),
                shape = RoundedCornerShape(Dimensions.mediumRadius),
                contentPadding = PaddingValues(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Green500, Green800)
                            ),
                            shape = RoundedCornerShape(Dimensions.mediumRadius)
                        )
                        .clip(RoundedCornerShape(Dimensions.mediumRadius)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.sign_in),
                        color = Color.White
                    )
                }


                GradientButton(
                    text = stringResource(R.string.sign_in),
                    onClick = {},
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
                    color = Green800
                )

            }
        }


    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val gradientColors = when {
        !enabled -> {
            listOf(Grey300)
        }

        isPressed -> {
            listOf(Green800)
        }

        else -> {
            listOf(Green500, Green800)
        }
    }

    val buttonShape = RoundedCornerShape(Dimensions.mediumRadius)

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.buttonHeight),
        enabled = enabled,
        shape = buttonShape,
        contentPadding = PaddingValues(),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = gradientColors
                    ),
                    shape = buttonShape
                )
                .clip(buttonShape),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = text,
                color = White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun LoginPagePreview() {
    GrowBoxTheme {
        LoginPage(
            uiState = LoginUiState(),
            onEvent = {}
        )
    }
}