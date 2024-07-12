package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing

/**
 * @author Ruslan Ganeev
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SettingsBottomSheet(
    userName: String,
    onClose: () -> Unit,
    onUserNameChanged: (String) -> Unit,
    onSaveUserName: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = state,
        containerColor = MaterialTheme.colorScheme.surfaceDim
    ) {
        SettingsBottomSheetContent(
            userName = userName,
            onUserNameChanged = onUserNameChanged,
            onSaveUserName = {
                onSaveUserName()
                scope.launch {
                    state.hide()
                    onClose()
                }
            }
        )
    }
}

@Composable
private fun SettingsBottomSheetContent(
    userName: String,
    onUserNameChanged: (String) -> Unit,
    onSaveUserName: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.settings_list_item_name_headline),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
        )
        OutlinedTextField(
            value = userName,
            onValueChange = onUserNameChanged,
            placeholder = {
                Text(
                    text = stringResource(R.string.settings_bottom_sheet_hint),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
        )
        Button(
            onClick = onSaveUserName,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium),
        ) {
            Text(
                text = stringResource(R.string.settings_bottom_sheet_save_button),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
fun SettingsBottomSheetPreview() {
    MeowleTheme {
        SettingsBottomSheetContent(
            userName = "Саня",
            {},
            {}
        )
    }
}
