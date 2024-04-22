package ru.tinkoff.fintech.meowle.domain.cat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Ruslan Ganeev
 */
@Parcelize
data class Cat(
    val id: Long,
    val name: String,
    val description: String,
    val gender: Gender,
    val likes: Int,
    val dislikes: Int
) : Parcelable
