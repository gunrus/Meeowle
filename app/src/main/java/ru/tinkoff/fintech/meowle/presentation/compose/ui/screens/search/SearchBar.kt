package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing

/**
 * @author Ruslan Ganeev
 */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    isError: Boolean,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onFilter: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = searchText,
        placeholder = { Text(text = stringResource(R.string.search_text_placeholder)) },
        textStyle = MaterialTheme.typography.titleLarge,
        onValueChange = onSearchTextChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_magnifying_glass_icon_content_description)
            )
        },
        trailingIcon = {
            IconButton(
                onClick = onFilter,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.tune),
                    contentDescription = stringResource(R.string.search_tune_icon_content_description),
                    modifier = Modifier
                        .testTag("searchOptions")
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceBright,
            errorContainerColor = MaterialTheme.colorScheme.surfaceBright,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = stringResource(R.string.error_message),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.medium)
                )
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                if (!isError) {
                    keyboardController?.hide()
                }
            }
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .testTag("search")
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SearchDarkPreview() {
    MeowleTheme {
        SearchBar(
            searchText = "",
            isError = false,
            onSearch = {},
            onSearchTextChange = {},
            onFilter = {}
        )
    }
}

@Composable
@Preview
private fun SearchLightPreview() {
    MeowleTheme {
        SearchBar(
            searchText = "",
            isError = false,
            onSearch = {},
            onSearchTextChange = {},
            onFilter = {}
        )
    }
}
