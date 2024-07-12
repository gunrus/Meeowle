package ru.tinkoff.fintech.meowle.presentation.compose.ui.elements

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing
import ru.tinkoff.fintech.meowle.presentation.shared.menu.AddGenderOption
import ru.tinkoff.fintech.meowle.presentation.shared.menu.GenderOption
import ru.tinkoff.fintech.meowle.presentation.shared.menu.MenuOption
import ru.tinkoff.fintech.meowle.presentation.shared.menu.OrderOption

/**
 * @author Ruslan Ganeev
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MeowleDropdownMenu(
    selectedOption: MenuOption,
    onOptionSelected: (MenuOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceDim)
    ) {
        val title = when (selectedOption) {
            is GenderOption -> stringResource(R.string.drop_down_menu_gender_title)
            is OrderOption -> stringResource(R.string.drop_down_menu_sort_title)
            is AddGenderOption -> stringResource(R.string.add_gender_title)
        }
        ListItem(
            overlineContent = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            headlineContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = selectedOption.toImageVector(),
                        contentDescription = stringResource(R.string.drop_down_menu_icon_content_description),
                        modifier = Modifier
                            .padding(end = MaterialTheme.spacing.small)
                            .size(MaterialTheme.spacing.medium)
                    )
                    Text(
                        text = selectedOption.toTitle(LocalContext.current),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

            },
            trailingContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = title
                )
            },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceBright
            ),
            modifier = Modifier
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceBright)
        ) {
            val options: List<MenuOption>  = when (selectedOption) {
                is GenderOption -> GenderOption.entries
                is OrderOption -> OrderOption.entries
                is AddGenderOption -> AddGenderOption.entries
            }

            options.forEach { option ->
                val background = if (option == selectedOption) {
                    MaterialTheme.colorScheme.surfaceDim
                } else {
                    MaterialTheme.colorScheme.surfaceBright
                }
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = option.toImageVector(),
                            contentDescription = stringResource(R.string.drop_down_menu_icon_content_description)
                        )
                    },
                    text = {
                        Text(text = option.toTitle(LocalContext.current))
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    modifier = Modifier
                        .background(background)
                )
            }
        }
    }
}

@Composable
fun MenuOption.toImageVector(): ImageVector {
    return when (this) {
        GenderOption.MALE, AddGenderOption.MALE -> ImageVector.vectorResource(R.drawable.gender_male)
        GenderOption.FEMALE, AddGenderOption.FEMALE -> ImageVector.vectorResource(R.drawable.gender_female)
        GenderOption.UNISEX, AddGenderOption.UNISEX -> ImageVector.vectorResource(R.drawable.gender_unisex)
        GenderOption.ALL -> ImageVector.vectorResource(R.drawable.gender_all)
        OrderOption.ASC -> ImageVector.vectorResource(R.drawable.order_asc)
        OrderOption.DESC -> ImageVector.vectorResource(R.drawable.order_desc)
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DropdownMenuPreview() {
    MeowleTheme {
        MeowleDropdownMenu(selectedOption = GenderOption.ALL) {

        }
    }
}
