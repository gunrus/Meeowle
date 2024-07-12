package ru.tinkoff.fintech.meowle.presentation.shared.favourites

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
import ru.tinkoff.fintech.meowle.domain.MeowleResult
import ru.tinkoff.fintech.meowle.domain.repository.CatRepository
import ru.tinkoff.fintech.meowle.domain.repository.PhotosRepository
import javax.inject.Inject

/**
 * @author Ruslan Ganeev
 */
@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val catsRepository: CatRepository,
    private val photosRepository: PhotosRepository
) : ViewModel() {

    private val _favouriteCats = MutableStateFlow<List<FavouriteCat>>(emptyList())
    val favouriteCats = _favouriteCats.asStateFlow()

    private val _isCatsLoaded = Channel<Unit>()
    val isCatsLoaded = _isCatsLoaded.receiveAsFlow()

    fun loadFavouriteCats() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = catsRepository.getFavoriteCats()) {
                is MeowleResult.Error -> {}
                is MeowleResult.Success -> {
                    _favouriteCats.value = result.data.map {
                        FavouriteCat(it, null)
                    }
                    _isCatsLoaded.send(Unit)
                }
            }
        }
    }

    fun loadPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteCats.update { favouriteCats ->
                favouriteCats.map {
                    val catPhoto = when (val result = photosRepository.getCatPhoto(it.cat.id)) {
                        is MeowleResult.Error -> { null }
                        is MeowleResult.Success -> {
                            result.data
                        }
                    }
                    it.copy(catPhoto = catPhoto)
                }
            }
        }
    }
}
