package ru.tinkoff.fintech.meowle.presentation.shared.menu

import android.content.Context
import ru.tinkoff.fintech.meowle.R

/**
 * @author Ruslan Ganeev
 */
enum class OrderOption(override val option: String) : MenuOption {
    ASC("asc") {
        override fun toTitle(context: Context): String {
            return context.getString(R.string.drop_down_menu_sort_option_asc)
        }
    },
    DESC("desc") {
        override fun toTitle(context: Context): String {
            return context.getString(R.string.drop_down_menu_sort_option_desc)
        }
    }
}
