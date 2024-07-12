package ru.tinkoff.fintech.meowle.presentation.shared.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.tinkoff.fintech.meowle.domain.MeowleResult
import ru.tinkoff.fintech.meowle.domain.cat.Vote
import ru.tinkoff.fintech.meowle.domain.repository.CatRepository
import javax.inject.Inject

/**
 * @author Ruslan Ganeev
 */
@HiltViewModel
class RatingViewModel @Inject constructor(
    private val catsRepository: CatRepository
) : ViewModel() {

    private val _catsRating = MutableStateFlow(RatingState())
    val catsRating = _catsRating.asStateFlow()

    fun loadRating(vote: Vote = Vote.LIKES) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = catsRepository.getRating()) {
                is MeowleResult.Error -> {}
                is MeowleResult.Success -> {
                    result.data[vote]?.let {
                        _catsRating.value = RatingState(vote, it)
                    }
                }
            }
        }
    }
}
