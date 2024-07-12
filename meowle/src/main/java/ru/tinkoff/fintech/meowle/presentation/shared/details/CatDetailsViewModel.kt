package ru.tinkoff.fintech.meowle.presentation.shared.details

import android.net.Uri
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
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.domain.repository.CatRepository
import ru.tinkoff.fintech.meowle.domain.repository.PhotosRepository
import javax.inject.Inject

/**
 * @author Ruslan Ganeev
 */
@HiltViewModel
class CatDetailsViewModel @Inject constructor(
    private val catsRepository: CatRepository,
    private val photosRepository: PhotosRepository
) : ViewModel() {

    private val _cat = MutableStateFlow(EMPTY_CAT)
    val cat = _cat.asStateFlow()

    private val _catDetailsState = MutableStateFlow(CatDetailsState())
    val catDetailsState = _catDetailsState.asStateFlow()

    private val _bottomSheetState = MutableStateFlow(CatDetailsBottomSheetState())
    val bottomSheetState = _bottomSheetState.asStateFlow()

    fun updateCat(cat: Cat) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = catsRepository.getCatById(cat.id)) {
                is MeowleResult.Error -> {
                    _cat.update { cat }
                }
                is MeowleResult.Success -> {
                    _cat.update { result.data }
                }
            }
        }
    }

    fun loadCatDetails(cat: Cat) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = when (val result = catsRepository.isFavourite(cat.id)) {
                is MeowleResult.Error -> false
                is MeowleResult.Success -> result.data
            }

            val photosUrls = when (val result = photosRepository.getCatPhotos(cat.id)) {
                is MeowleResult.Error -> emptyList()
                is MeowleResult.Success -> result.data
            }
            val vote = when (val result = catsRepository.isVoted(cat.id)) {
                is MeowleResult.Error -> null
                is MeowleResult.Success -> result.data
            }

            _catDetailsState.update {
                CatDetailsState(
                    catAvatarUrl = photosUrls.firstOrNull(),
                    catPhotoUrls = photosUrls,
                    vote = vote,
                    isFavorite = isFavorite
                )
            }

            _bottomSheetState.update {
                it.copy(catDescription = cat.description)
            }
        }
    }

    fun onEditCatDescriptionClicked() {
        _bottomSheetState.update { it.copy(isShown = true) }
    }

    fun onCloseBottomSheet() {
        _bottomSheetState.update { it.copy(isShown = false) }
    }

    fun onCatDescriptionChange(description: String) {
        _bottomSheetState.update { it.copy(catDescription = description) }
    }

    fun onSaveDescriptionClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = catsRepository.saveDescription(id = _cat.value.id, description = _bottomSheetState.value.catDescription)) {
                is MeowleResult.Error -> {}
                is MeowleResult.Success -> {
                    _cat.update { result.data }
                    updateCatInDatabase()
                }
            }
        }
    }

    fun onVoteClicked(vote: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = catsRepository.vote(_cat.value.id, vote)) {
                is MeowleResult.Error -> {}
                is MeowleResult.Success -> {
                    _cat.update { result.data }
                    _catDetailsState.update { it.copy(vote = vote) }
                    updateCatInDatabase()
                }
            }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_catDetailsState.value.isFavorite) {
                Log.i("Favourite", "Cat with name ${_cat.value.name} removed from favourites")
                removeCatFromDatabase()
                _catDetailsState.update { it.copy(isFavorite = false) }
            } else {
                Log.i("Favourite", "Cat with name ${_cat.value.name} added to favourites")
                saveCatToDatabase()
                _catDetailsState.update { it.copy(isFavorite = true) }
            }
        }
    }

    fun onNewCatPhotoSelected(photoUri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            photoUri?.let {
                val catId = _cat.value.id
                photosRepository.uploadCatPhoto(catId, photoUri)
                when (val result = photosRepository.getCatPhotos(catId)) {
                    is MeowleResult.Error -> {}
                    is MeowleResult.Success -> {
                        _catDetailsState.update {
                            it.copy(catPhotoUrls = result.data)
                        }
                        updateCatInDatabase()
                    }
                }
            }
        }
    }

    private fun saveCatToDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val cat = _cat.value
            val catPhoto = _catDetailsState.value.catPhotoUrls.firstOrNull()
            catsRepository.addToFavourites(cat)
            if (catPhoto != null) {
                photosRepository.saveCatPhoto(cat.id, catPhoto)
            }
        }
    }

    private fun removeCatFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val catId = _cat.value.id
            catsRepository.removeFromFavourites(catId)
            photosRepository.deleteCatPhoto(catId)
        }
    }

    private fun updateCatInDatabase() {
        if (_catDetailsState.value.isFavorite) {
            saveCatToDatabase()
        }
    }

    private companion object {
        val EMPTY_CAT = Cat(
            id = -1,
            name = "",
            description = "",
            gender = Gender.MALE,
            likes = 0,
            dislikes = 0
        )
    }
}
