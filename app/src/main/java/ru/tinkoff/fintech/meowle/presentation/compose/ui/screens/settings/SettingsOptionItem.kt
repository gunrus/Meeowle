package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.dropShadow
import ru.tinkoff.fintech.meowle.presentation.compose.ui.innerShadow
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing

/**
 * @author Ruslan Ganeev
 */
@Composable
fun SettingsOptionItem(
    modifier: Modifier = Modifier,
    headlineText: String,
    supportingText: String,
    iconContent: @Composable () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .padding(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium,
                bottom = MaterialTheme.spacing.medium
            )
            .dropShadow(
                shape = MaterialTheme.shapes.medium,
                offsetX = 2.dp,
                offsetY = 4.dp,
                blur = 4.dp,
            )
            .innerShadow(
                shape = MaterialTheme.shapes.medium,
                offsetX = 2.dp,
                offsetY = 4.dp,
                blur = 4.dp,
                spread = 0.dp
            )


    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = headlineText,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            supportingContent = {
                Text(
                    text = supportingText,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            trailingContent = iconContent,
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceBright
            )
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun SettingsSwitcherDarkPreview() {
    MeowleTheme {
        SettingsOptionItem(
            headlineText = stringResource(R.string.settings_list_item_compose_headline),
            supportingText = stringResource(R.string.settings_list_item_compose_supporting),
        ) {
            Switch(
                checked = false,
                onCheckedChange = {  }
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun SettingsSwitcherLightPreview() {
    MeowleTheme {
        SettingsOptionItem(
            headlineText = stringResource(R.string.settings_list_item_compose_headline),
            supportingText = stringResource(R.string.settings_list_item_compose_supporting),
        ) {
            Switch(
                checked = false,
                onCheckedChange = {  },
                colors = SwitchDefaults.colors(

                )
            )
        }
    }
}
