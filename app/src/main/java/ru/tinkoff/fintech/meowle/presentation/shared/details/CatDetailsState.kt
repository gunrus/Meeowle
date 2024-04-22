package ru.tinkoff.fintech.meowle.presentation.shared.details

import android.net.Uri

/**
 * @author Ruslan Ganeev
 */
data class CatDetailsState(
    val catAvatarUrl: Uri? = null,
    val catPhotoUrls: List<Uri?> = emptyList(),
    val vote: Boolean? = null,
    val isFavorite: Boolean = false
)
