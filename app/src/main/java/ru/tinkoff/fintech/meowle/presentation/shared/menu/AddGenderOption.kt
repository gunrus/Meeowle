package ru.tinkoff.fintech.meowle.presentation.shared.menu

import android.content.Context
import ru.tinkoff.fintech.meowle.R

/**
 * @author Ruslan Ganeev
 */
enum class AddGenderOption(override val option: String?) : MenuOption {
    MALE("male") {
        override fun toTitle(context: Context): String {
            return context.getString(R.string.drop_down_menu_gender_option_male)
        }
    },
    FEMALE("female") {
        override fun toTitle(context: Context): String {
            return context.getString(R.string.drop_down_menu_gender_option_female)
        }
    },
    UNISEX("unisex") {
        override fun toTitle(context: Context): String {
            return context.getString(R.string.drop_down_menu_gender_option_unisex)
        }
    }
}
