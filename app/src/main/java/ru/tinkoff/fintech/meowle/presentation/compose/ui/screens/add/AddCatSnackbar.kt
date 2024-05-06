package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.add

import android.content.res.Configuration
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing

/**
 * @author Ruslan Ganeev
 */
@Composable
fun AddCatSnackbar(
    isShown: Boolean,
    message: String,
    onClose: () -> Unit
) {
    if (isShown) {
        Snackbar(
            action = {
                Button(
                    onClick = onClose,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.extraSmall)
                ) {
                    Text(
                        text = stringResource(R.string.add_snackbar_button_title),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.surfaceDim,
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .padding(WindowInsets.ime.asPaddingValues()),
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun AddCatSnackbarPreview() {
    MeowleTheme {
        AddCatSnackbar(
            isShown = true,
            message = stringResource(R.string.add_snackbar_success_message)
        ) {}
    }
}