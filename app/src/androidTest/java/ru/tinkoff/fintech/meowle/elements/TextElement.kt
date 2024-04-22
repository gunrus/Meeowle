package ru.tinkoff.fintech.meowle.elements

import android.view.View
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher

/**
 * @author Ruslan Ganeev
 */
class TextElement(matcher: Matcher<View>) : BaseElement(matcher) {

    fun checkText(text: String) {
        viewInteraction.check(matches(withText(text)))
    }
}