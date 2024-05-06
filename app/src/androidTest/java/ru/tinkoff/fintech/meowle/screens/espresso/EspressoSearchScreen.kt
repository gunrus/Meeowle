package ru.tinkoff.fintech.meowle.screens.espresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import ru.tinkoff.fintech.meowle.R

/**
 * @author Ruslan Ganeev
 */
class EspressoSearchScreen {

    private val searchTitle = onView(withId(R.id.tw_search_title))

    fun checkTitle(titleText: String) {
        searchTitle.check(matches(withText(titleText)))
    }
}