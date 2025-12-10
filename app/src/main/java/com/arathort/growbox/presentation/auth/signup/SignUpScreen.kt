package com.arathort.growbox.presentation.auth.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arathort.growbox.ui.theme.GrowBoxTheme

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState = signUpViewModel.uiState.collectAsStateWithLifecycle().value
    SignUpPage(
        uiState = uiState,
        onEvent = signUpViewModel::onEvent
    )
}

@Composable
private fun SignUpPage(uiState: SignUpUiState, onEvent: (SignUpUiEvent) -> Unit) {

}

@Preview
@Composable
private fun SignUpPagePreview() {
    GrowBoxTheme {
        SignUpPage(
            uiState = SignUpUiState(),
            onEvent = {}
        )
    }
}