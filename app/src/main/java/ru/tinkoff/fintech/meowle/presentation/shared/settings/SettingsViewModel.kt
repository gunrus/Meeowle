package ru.tinkoff.fintech.meowle.presentation.shared.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.domain.repository.SettingsRepository
import ru.tinkoff.fintech.meowle.presentation.Mode
import javax.inject.Inject

/**
 * @author Ruslan Ganeev
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val _applySettings = Channel<Unit>()
    val applySettings = _applySettings.receiveAsFlow()

    private val _logout = Channel<Unit>()
    val logout = _logout.receiveAsFlow()

    private val _bottomSheetState = MutableStateFlow(SettingsBottomSheetState())
    val bottomSheetState = _bottomSheetState.asStateFlow()

    fun loadSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            val name = settingsRepository.getName()
            val isCompose = when (settingsRepository.getLaunchMode()) {
                Mode.COMPOSE ->  true
                Mode.VIEWS ->  false
            }
            _state.value = _state.value.copy(name = name, isCompose = isCompose)
            _bottomSheetState.update {
                it.copy(userName = name)
            }
        }
    }

    fun onComposeSwitchClicked() {
        _state.value = _state.value.copy(isCompose = _state.value.isCompose.not())
        applySettings()
    }

    fun onNameClicked() {
        _bottomSheetState.update {
            it.copy(isShown = true)
        }
    }

    fun onCloseBottomSheet() {
        _bottomSheetState.update {
            it.copy(isShown = false)
        }
    }

    fun onUserNameChanged(userName: String) {
        _bottomSheetState.update {
            it.copy(userName = userName)
        }
    }

    fun onSaveUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(name = _bottomSheetState.value.userName)
            }
            settingsRepository.setName(_bottomSheetState.value.userName)
        }
    }

    fun applySettings() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setLaunchMode(if (_state.value.isCompose) Mode.COMPOSE else Mode.VIEWS)
            _applySettings.send(Unit)
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setAuth(false)
            _logout.send(Unit)
        }
    }
}
