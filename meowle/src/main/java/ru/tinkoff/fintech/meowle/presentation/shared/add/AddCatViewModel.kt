package ru.tinkoff.fintech.meowle.presentation.shared.add

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.domain.BackendError
import ru.tinkoff.fintech.meowle.domain.MeowleResult
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.domain.repository.CatRepository
import ru.tinkoff.fintech.meowle.domain.repository.PhotosRepository
import javax.inject.Inject

/**
 * @author Ruslan Ganeev
 */
@HiltViewModel
class AddCatViewModel @Inject constructor(
    private val catsRepository: CatRepository,
    private val photosRepository: PhotosRepository
) : ViewModel() {

    private val nameRegex = Regex("[-А-яЁё]+")
    private val _addCatInputState = MutableStateFlow(AddCatInputState())
    val addCatInputState = _addCatInputState.asStateFlow()

    private val _addCatState = MutableStateFlow<AddCatState>(AddCatState.Initial())
    val addCatState = _addCatState.asStateFlow()

    private val _result = Channel<MeowleResult<Unit, BackendError.Business>>()
    val result = _result.receiveAsFlow()

    fun onNameChange(name: String) {
        _addCatInputState.value = _addCatInputState.value.copy(
            name = name
        )
        validateName()
    }

    fun onDescriptionChange(description: String) {
        _addCatInputState.value = _addCatInputState.value.copy(description = description)
    }

    fun onClearDescription() {
        _addCatInputState.value = _addCatInputState.value.copy(description = "")
    }

    fun onGenderChange(gender: Gender) {
        _addCatInputState.value = _addCatInputState.value.copy(gender = gender)
    }

    fun onPhotoSelected(uri: Uri?) {
        _addCatInputState.value = _addCatInputState.value.copy(photo = uri)
    }

    fun onAddCat() {
        validateName()
        if (!_addCatInputState.value.nameError) {
            viewModelScope.launch(Dispatchers.IO) {
                when (val result = catsRepository.addCat(
                    name = _addCatInputState.value.name,
                    gender = _addCatInputState.value.gender.gender,
                    description = _addCatInputState.value.description)
                ) {
                    is MeowleResult.Success -> {
                        _result.send(MeowleResult.Success(Unit))
                        _addCatInputState.value.photo?.let {
                            photosRepository.uploadCatPhoto(result.data.id, it)
                        }
                    }
                    is MeowleResult.Error -> {
                        val message = when (result.error) {
                            is BackendError.Business.Unknown -> result.error.message
                            BackendError.Network.UNKNOWN -> result.error.toString()
                        }

                        _result.send(MeowleResult.Error(BackendError.Business.Unknown(message)))
                    }
                }
            }
        } else {
            Log.w("AddCat", "Trying add cat with invalid name: ${_addCatInputState.value.name}")
        }
    }

    private fun validateName() {
        _addCatState.value = AddCatState.Initial()
        if (_addCatInputState.value.name.matches(nameRegex)) {
            _addCatInputState.value = _addCatInputState.value.copy(
                nameError = false
            )
        } else {
            _addCatInputState.value = _addCatInputState.value.copy(
                nameError = true
            )
        }
    }
}
