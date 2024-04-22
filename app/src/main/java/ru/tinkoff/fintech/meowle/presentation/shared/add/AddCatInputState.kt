package ru.tinkoff.fintech.meowle.presentation.shared.add

import android.net.Uri
import ru.tinkoff.fintech.meowle.domain.cat.Gender

/**
 * @author Ruslan Ganeev
 */
data class AddCatInputState(
    val name: String = "",
    val nameError: Boolean = false,
    val description: String = "",
    val descriptionError: Boolean = false,
    val gender: Gender = Gender.UNISEX,
    val photo: Uri? = null,
)
