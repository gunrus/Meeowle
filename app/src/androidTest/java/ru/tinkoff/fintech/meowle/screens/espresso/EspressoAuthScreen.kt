package ru.tinkoff.fintech.meowle.screens.espresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import ru.tinkoff.fintech.meowle.R

/**
 * @author Ruslan Ganeev
 */
class EspressoAuthScreen {

    private val nameField = onView(withId(R.id.et_name))
    private val phoneField = onView(withId(R.id.et_phone))
    private val submitButton = onView(withId(R.id.submit_button))

    fun enterName(name: String) {
        nameField.perform(replaceText(name))
    }

    fun checkName(name: String) {
        nameField.check(matches(withText(name)))
    }

    fun enterPhone(phone: String) {
        phoneField.perform(replaceText(phone))
    }

    fun checkPhone(phone: String) {
        phoneField.check(matches(withText(phone)))
    }

    fun clickSubmit() {
        submitButton.perform(click())
    }
}