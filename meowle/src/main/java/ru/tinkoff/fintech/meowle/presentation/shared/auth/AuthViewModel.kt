package ru.tinkoff.fintech.meowle.presentation.shared.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * @author Ruslan Ganeev
 */
@HiltViewModel
class AuthViewModel @Inject constructor(private val settingsRepository: SettingsRepository) : ViewModel() {

    val name = MutableStateFlow("")
    private val nameRegex = Regex("^([А-Я][а-я]{1,23})\$")
    val phone = MutableStateFlow("")
    val phone_error = MutableStateFlow("")
    val name_error = MutableStateFlow("")
    private val phoneRegex = Regex("^\\+7\\d{10}\$")
    private val _authState = MutableStateFlow(false)
    val authState = _authState.asStateFlow()


    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value = settingsRepository.getAuth()
        }
    }

    fun onPhoneTextChanged() {
        phone_error.value = ""
    }

    fun onNameTextChanged() {
        name_error.value = ""
    }

    fun onSubmitButtonClick() {
        if (validateInput()) {
            phone_error.value = ""
            name_error.value = ""
            viewModelScope.launch(Dispatchers.IO) {
                settingsRepository.setAuth(true)
                settingsRepository.setName(name.value)
                _authState.value = true
            }
        } else {
            if (!phone.value.startsWith("+7"))
                phone_error.value = "Номер должен начинаться с +7"
            else if (!phone.value.matches(phoneRegex))
                phone_error.value = "Номер должен соответствовать формату +79999999999"
            if (!name.value.matches(nameRegex))
                name_error.value = "Имя должно быть с большой буквы, не должно содержать латиницу, спецсимволы"
        }
    }

    private fun validateInput(): Boolean {
        return name.value.matches(nameRegex) and phone.value.matches(phoneRegex)
    }
}
