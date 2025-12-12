package com.arathort.growbox.presentation.common.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arathort.growbox.R
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Red
import com.arathort.growbox.ui.theme.Typography


@Composable
fun LoginEmailField(
    email: String,
    isValid: Boolean,
    errorId: Int?,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = email,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography.bodyMedium,
            isError = errorId != null,
            singleLine = true,
            shape = RoundedCornerShape(Dimensions.small),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Green800,
                unfocusedBorderColor = Green800,
                errorBorderColor = Red
            ),
            trailingIcon = {
                if (isValid) {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = "Valid Email",
                        tint = Green800
                    )
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