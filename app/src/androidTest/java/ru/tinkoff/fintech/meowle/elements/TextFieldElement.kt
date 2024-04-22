package ru.tinkoff.fintech.meowle.elements

import android.view.View
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

/**
 * @author Ruslan Ganeev
 */
class TextFieldElement(matcher: Matcher<View>) : BaseElement(matcher) {

    fun enterText(text: String) {
        viewInteraction.perform(replaceText(text))
    }

    fun checkText(text: String) {
        viewInteraction.check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }
}