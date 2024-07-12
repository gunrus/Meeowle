package ru.tinkoff.fintech.meowle.presentation.compose.ui.top

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.generated.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.spec.DestinationSpec
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme

/**
 * @author Ruslan Ganeev
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MeowleTopAppBar(
    currentDestination: DestinationSpec,
    onNavigateUp: () -> Unit,
) {

    val topAppBarDestination = TopAppBarDestination.entries.first { it.direction == currentDestination }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            Text(
                text = stringResource(topAppBarDestination.title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            if (topAppBarDestination.isNavigateUp) {
                IconButton(
                    onClick = {
                        onNavigateUp()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.top_app_bar_navigate_back_content_description)
                    )
                }
            }
        }
    )
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun CatDetailsTopAppBarPreview() {
    MeowleTheme {
        MeowleTopAppBar(SearchScreenDestination) {

        }
    }
}