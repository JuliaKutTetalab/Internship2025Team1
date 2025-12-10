package com.arathort.growbox.presentation.common.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Red
import com.arathort.growbox.ui.theme.Typography


@Composable
fun LoginPasswordField(
    password: String,
    isValid: Boolean,
    isVisible: Boolean,
    errorId: Int?,
    onValueChange: (String) -> Unit,
    onToggleVisibility: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = password,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.bodyMedium,
            isError = errorId != null,
            singleLine = true,
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(Dimensions.small),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Green800,
                unfocusedBorderColor = Green800,
                errorBorderColor = Red
            ),
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isValid) {
                        Icon(
                            painter = painterResource(R.drawable.ic_check),
                            contentDescription = "Valid Password",
                            tint = Green800,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                    IconButton(onClick = onToggleVisibility) {
                        Icon(
                            painter = painterResource(
                                if (isVisible) R.drawable.ic_eye else R.drawable.ic_eye_off
                            ),
                            contentDescription = "Toggle Visibility",
                            tint = Green800
                        )
                    }
                }
            }
        )

        if (errorId != null) {
            Text(
                text = stringResource(errorId),
                color = Red,
                style = Typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}