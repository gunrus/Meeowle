package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.settings

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import kotlinx.coroutines.flow.collectLatest
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing
import ru.tinkoff.fintech.meowle.presentation.shared.settings.SettingsViewModel
import ru.tinkoff.fintech.meowle.presentation.shared.settings.launchAuthActivity
import ru.tinkoff.fintech.meowle.presentation.shared.settings.launchMainActivity

/**
 * @author Ruslan Ganeev
 */
@Composable
@Destination<RootGraph>
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val settingsState = viewModel.state.collectAsStateWithLifecycle().value
    val bottomSheetState = viewModel.bottomSheetState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.loadSettings()
        viewModel.applySettings.collectLatest {
            if (!settingsState.isCompose) {
                launchMainActivity(context)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.logout.collectLatest {
            launchAuthActivity(context)
        }
    }

    SettingsScreenContent(
        userName = settingsState.name,
        isCompose = settingsState.isCompose,
        onComposeSwitchClicked = viewModel::onComposeSwitchClicked,
        onApplySettingsClicked = viewModel::applySettings,
        onNameClicked = viewModel::onNameClicked,
        onLogoutClicked = viewModel::logout
    )
    if (bottomSheetState.isShown) {
        SettingsBottomSheet(
            userName = bottomSheetState.userName,
            onClose = viewModel::onCloseBottomSheet,
            onUserNameChanged = viewModel::onUserNameChanged,
            onSaveUserName = viewModel::onSaveUserName
        )
    }
}

@Composable
fun SettingsScreenContent(
    userName: String,
    isCompose: Boolean,
    onComposeSwitchClicked: () -> Unit,
    onNameClicked: () -> Unit,
    onApplySettingsClicked: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        SettingsOptionItem(
            headlineText = stringResource(R.string.settings_list_item_compose_headline),
            supportingText = stringResource(R.string.settings_list_item_compose_supporting),
        ) {
            Switch(
                checked = isCompose,
                onCheckedChange = {
                    onComposeSwitchClicked()
                }
            )
        }
        SettingsOptionItem(
            headlineText = stringResource(R.string.settings_list_item_inactive_headline),
            supportingText = stringResource(R.string.settings_list_item_inactive_supporting),
        ) {
            Switch(
                checked = false,
                onCheckedChange = {  }
            )
        }
        SettingsOptionItem(
            headlineText = stringResource(R.string.settings_list_item_name_headline),
            supportingText = userName,
            modifier = Modifier
                .clickable { onNameClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.settings_list_item_name_content_description)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onLogoutClicked,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
        ) {
            Text(
                text = stringResource(R.string.settings_logout_button),
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
fun SettingsScreenContentPreview() {
    MeowleTheme {
        SettingsScreenContent(
            "Саня",
            true,
            {},
            {},
            {},
            {}
        )
    }
}
