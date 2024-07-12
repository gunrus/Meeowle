package ru.tinkoff.fintech.meowle.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * @author Ruslan Ganeev
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _mode = MutableStateFlow(Mode.VIEWS)
    val mode = _mode.asStateFlow()

    fun loadMode() {
        viewModelScope.launch {
            _mode.value = settingsRepository.getLaunchMode()
        }
    }
}
