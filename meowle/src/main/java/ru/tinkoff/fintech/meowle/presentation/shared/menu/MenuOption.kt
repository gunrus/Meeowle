package ru.tinkoff.fintech.meowle.presentation.shared.menu

import android.content.Context

/**
 * @author Ruslan Ganeev
 */
sealed interface MenuOption {
    val option: String?

    fun toTitle(context: Context): String
}