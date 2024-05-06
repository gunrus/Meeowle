package ru.tinkoff.fintech.meowle.presentation.compose.ui.bottom

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.generated.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme

/**
 * @author Ruslan Ganeev
 */
@Composable
fun MeowleBottomBar(
    currentDestination: DestinationSpec,
    navigateTo: (DirectionDestinationSpec) -> Unit,
) {
    if (BottomBarDestination.entries.any { it.direction == currentDestination }) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BottomBarDestination.entries.forEach { destination ->
                val isSelected = currentDestination == destination.direction

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navigateTo(destination.direction)
                    },
                    icon = {
                        if (isSelected) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.botton_tab_dot),
                                contentDescription = stringResource(destination.title)
                            )
                        } else {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = stringResource(destination.title)
                            )
                        }
                    },
                    label = {
                        if (isSelected) {
                            Text(
                                text = stringResource(destination.title),
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                    },
                )
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun MeowleBottomBarPreview() {
    MeowleTheme {
        MeowleBottomBar(
            currentDestination = SearchScreenDestination,
        ) {  }
    }
}
