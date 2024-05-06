package ru.tinkoff.fintech.meowle.espresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity

/**
 * @author Ruslan Ganeev
 */
class EspressoAdvancedExample {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<AuthActivity>()

    @Test
    fun negativeAuth() {

        onView(withId(R.id.et_name))
            .perform(replaceText("WrongName"))

        onView(withId(R.id.submit_button))
            .perform(ViewActions.click())

        onView(withId(R.id.til_name))
            .check(matches(ErrorTextMatcher("Имя должно быть с большой буквы, не должно содержать латиницу, спецсимволы")))
    }
}