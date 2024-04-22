package ru.tinkoff.fintech.meowle.presentation.shared.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.domain.MeowleResult
import ru.tinkoff.fintech.meowle.domain.repository.CatRepository
import ru.tinkoff.fintech.meowle.presentation.shared.menu.GenderOption
import ru.tinkoff.fintech.meowle.presentation.shared.menu.OrderOption
import javax.inject.Inject

/**
 * @author Ruslan Ganeev
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val catsRepository: CatRepository
) : ViewModel() {

    private val searchRegex = Regex("[-А-яЁё]+")
    private val _inputsState = MutableStateFlow(SearchInputsState())
    val inputState = _inputsState.asStateFlow()

    private val _screenState = MutableStateFlow(SearchScreenState())
    val screenState = _screenState.asStateFlow()

    fun onSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            val inputs = _inputsState.value

            Log.d("Search", "Searching cat with name ${inputs.searchText}")

            _screenState.update { it.copy(status = Status.LOADING) }

            val catsResult = catsRepository.getCats(
                name = inputs.searchText,
                order = inputs.searchOrder.option,
                gender = inputs.searchGender.option
            )
            when (catsResult) {
                is MeowleResult.Success -> {
                    if (catsResult.data.isEmpty()) {
                        _screenState.update {
                            it.copy(
                                status = Status.EMPTY,
                                cats = catsResult.data
                            )
                        }
                        return@launch
                    }
                    _screenState.update {
                        it.copy(
                            status = Status.SUCCESS,
                            cats = catsResult.data
                        )
                    }
                }
                is MeowleResult.Error -> {
                    _screenState.update {
                        it.copy(
                            status = Status.ERROR,
                            cats = emptyList()
                        )
                    }
                }
            }
        }
    }

    fun onSearchTextChange(searchText: String) {
        _inputsState.update {
            it.copy(
                searchText = searchText,
                isSearchTextValid = searchText.matches(searchRegex)
            )
        }
    }

    fun onSearchOrderChange(searchOrder: OrderOption) {
        _inputsState.update { it.copy(searchOrder = searchOrder) }
    }

    fun onSearchGenderChange(searchGender: GenderOption) {
        _inputsState.update { it.copy(searchGender = searchGender) }
    }

    fun onBottomSheetExpand(isExpanded: Boolean) {
        _screenState.update { it.copy(isBottomSheetExpanded = isExpanded) }
    }
}
