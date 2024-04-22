package ru.tinkoff.fintech.meowle.presentation.shared.favourites

import android.net.Uri
import ru.tinkoff.fintech.meowle.domain.cat.Cat

/**
 * @author Ruslan Ganeev
 */
data class FavouriteCat(
    val cat: Cat,
    val catPhoto: Uri?
)
