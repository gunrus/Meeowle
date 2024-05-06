package ru.tinkoff.fintech.meowle.elements

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher

/**
 * @author Ruslan Ganeev
 */
abstract class BaseElement(matcher: Matcher<View>) {

    protected val viewInteraction = Espresso.onView(matcher)

    fun click() {
        viewInteraction.perform(ViewActions.click())
    }

    fun checkIsDisplayed() {
        viewInteraction.check(matches(isDisplayed()))
    }
}