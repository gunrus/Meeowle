package ru.tinkoff.fintech.meowle.espresso

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity

/**
 * @author Ruslan Ganeev
 */
@RunWith(AndroidJUnit4::class)
class EspressoSimpleExample {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<AuthActivity>()

    @Test
    fun auth() {
        ActivityScenario.launch(AuthActivity::class.java)

        val nameFieldMatcher = ViewMatchers.withId(R.id.et_name)
        val nameFieldViewInteraction = Espresso.onView(nameFieldMatcher)

        val enterNameTextViewAction = ViewActions.replaceText("Алексей")
        nameFieldViewInteraction.perform(enterNameTextViewAction)

        val nameFieldViewMatcher = ViewMatchers.withText("Алексей")
        val nameFieldViewAssertion = ViewAssertions.matches(nameFieldViewMatcher)
        nameFieldViewInteraction.check(nameFieldViewAssertion)

        Espresso.onView(ViewMatchers.withId(R.id.et_phone))
            .perform(ViewActions.replaceText("+79999999999"))
            .check(ViewAssertions.matches(ViewMatchers.withText("+79999999999")))


        onView(withId(R.id.submit_button)).check(matches(isDisplayed())).perform(click())

        val searchTitleText = InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.search_title)
        onView(withId(R.id.tw_search_title)).check(matches(withText(searchTitleText)))
    }
}