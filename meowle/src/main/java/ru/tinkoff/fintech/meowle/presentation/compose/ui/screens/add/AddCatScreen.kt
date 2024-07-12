package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.add

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import kotlinx.coroutines.flow.collectLatest
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.BackendError
import ru.tinkoff.fintech.meowle.domain.MeowleResult
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.compose.ui.elements.MeowleDropdownMenu
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing
import ru.tinkoff.fintech.meowle.presentation.shared.add.AddCatInputState
import ru.tinkoff.fintech.meowle.presentation.shared.add.AddCatViewModel
import ru.tinkoff.fintech.meowle.presentation.shared.menu.AddGenderOption

/**
 * @author Ruslan Ganeev
 */
@Composable
@Destination<RootGraph>
fun AddCatScreen(
    viewModel: AddCatViewModel = hiltViewModel()
) {
    val addCatInputState = viewModel.addCatInputState.collectAsStateWithLifecycle().value

    var snackbarMessage by remember { mutableStateOf("") }
    var snackbarState by remember { mutableStateOf(false) }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.result.collectLatest {
            snackbarMessage = when (it) {
                is MeowleResult.Error -> {
                    when (it.error) {
                        is BackendError.Business.Unknown -> it.error.message
                    }
                }
                is MeowleResult.Success -> context.getString(R.string.add_snackbar_success_message)
            }
            snackbarState = true
        }
    }

    AddCatScreenContent(
        inputState = addCatInputState,
        onPhotoSelect = viewModel::onPhotoSelected,
        onNameChange = viewModel::onNameChange,
        onGenderChange = viewModel::onGenderChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onDescriptionClear = viewModel::onClearDescription,
        onAddCat = viewModel::onAddCat,
    )

    AddCatSnackbar(
        isShown = snackbarState,
        message = snackbarMessage
    ) {
        snackbarState = false
    }
}

@Composable
private fun AddCatScreenContent(
    inputState: AddCatInputState,
    onPhotoSelect: (Uri?) -> Unit,
    onNameChange: (String) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDescriptionClear: () -> Unit,
    onAddCat: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium
            )
    ) {

        val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                onPhotoSelect(it)
            }
        )

        AddCatImage(
            catImageUri = inputState.photo,
            modifier = Modifier
                .padding(
                    top = MaterialTheme.spacing.large,
                    bottom = MaterialTheme.spacing.large
                )
                .clickable {
                    val mimeType = "image/jpeg"
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType))
                    )
                }
        )

        val keyboardController = LocalSoftwareKeyboardController.current

        TextField(
            value = inputState.name,
            onValueChange = onNameChange,
            placeholder = {
                Text(
                    text = stringResource(R.string.add_name_placeholder),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            isError = inputState.nameError,
            supportingText = {
                if (inputState.nameError) {
                    Text(
                        text = stringResource(R.string.error_message),
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.medium)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (!inputState.nameError) {
                        keyboardController?.hide()
                    }
                }
            ),
            shape = RectangleShape,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceBright,
                errorContainerColor = MaterialTheme.colorScheme.surfaceBright,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.small)
        )

        MeowleDropdownMenu(
            selectedOption = inputState.gender.toOption(),
        ) {
            onGenderChange((it as AddGenderOption).toActual())
        }

        TextField(
            value = inputState.description,
            onValueChange = onDescriptionChange,
            placeholder = {
                Text(
                    text = stringResource(R.string.add_description_placeholder),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.add_description_clear_content_description),
                    modifier = Modifier
                        .clickable { onDescriptionClear() }
                )
            },
            maxLines = 2,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            shape = RectangleShape,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceBright,
                errorContainerColor = MaterialTheme.colorScheme.surfaceBright,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.medium)
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Button(
            onClick = onAddCat,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.large)
        ) {
            Text(
                text = stringResource(R.string.add_button_title),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

private fun AddGenderOption.toActual(): Gender {
    return when (this) {
        AddGenderOption.MALE -> Gender.MALE
        AddGenderOption.FEMALE -> Gender.FEMALE
        AddGenderOption.UNISEX -> Gender.UNISEX
    }
}

private fun Gender.toOption(): AddGenderOption {
    return when (this) {
        Gender.MALE -> AddGenderOption.MALE
        Gender.FEMALE -> AddGenderOption.FEMALE
        Gender.UNISEX -> AddGenderOption.UNISEX
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun AddCatScreenPreview() {
    MeowleTheme {
        AddCatScreenContent(
            inputState = AddCatInputState(),
            onPhotoSelect = {},
            onNameChange = {},
            onGenderChange = {},
            onDescriptionChange = {},
            onDescriptionClear = {},
            onAddCat = {},
        )
    }
}
