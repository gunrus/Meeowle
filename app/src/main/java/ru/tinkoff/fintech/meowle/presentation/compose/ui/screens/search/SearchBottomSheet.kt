package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.elements.MeowleDropdownMenu
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing
import ru.tinkoff.fintech.meowle.presentation.shared.menu.GenderOption
import ru.tinkoff.fintech.meowle.presentation.shared.menu.OrderOption

/**
 * @author Ruslan Ganeev
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBottomSheet(
    searchOrder: OrderOption,
    searchGender: GenderOption,
    onSearchOrderChange: (OrderOption) -> Unit,
    onSearchGenderChange: (GenderOption) -> Unit,
    onClose: () -> Unit,
    onApply: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = state,
        containerColor = MaterialTheme.colorScheme.surfaceDim
    ) {
        SearchBottomSheetContent(
            searchOrder = searchOrder,
            searchGender = searchGender,
            onSearchOrderChange = onSearchOrderChange,
            onSearchGenderChange = onSearchGenderChange,
            onApply = {
                scope.launch {
                    state.hide()
                    onClose()
                }
                onApply()
            }
        )
    }
}

@Composable
private fun SearchBottomSheetContent(
    searchOrder: OrderOption,
    searchGender: GenderOption,
    onSearchOrderChange: (OrderOption) -> Unit,
    onSearchGenderChange: (GenderOption) -> Unit,
    onApply: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.spacing.large,
                end = MaterialTheme.spacing.large,
                bottom = MaterialTheme.spacing.large
            )
    ) {
        Text(
            text = stringResource(R.string.search_bottom_sheet_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = MaterialTheme.spacing.medium)
        )
        MeowleDropdownMenu(
            selectedOption = searchOrder,
            onOptionSelected = {
                onSearchOrderChange(it as OrderOption)
            }
        )
        HorizontalDivider()
        MeowleDropdownMenu(
            selectedOption = searchGender,
            onOptionSelected = {
                onSearchGenderChange(it as GenderOption)
            }
        )
        HorizontalDivider()
        Spacer(
            modifier = Modifier
                .height(175.dp)
        )
        Button(
            onClick = onApply,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.search_bottom_sheet_apply_button_title),
                style = MaterialTheme.typography.titleMedium
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
        SearchBottomSheetContent(
            searchOrder = OrderOption.ASC,
            searchGender = GenderOption.ALL,
            {},
            {},
            {}
        )
    }
}
