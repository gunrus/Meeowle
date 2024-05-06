package ru.tinkoff.fintech.meowle.espresso

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description

/**
 * @author Ruslan Ganeev
 */
class ErrorTextMatcher(private val errorText: String) : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {

    override fun describeTo(description: Description) {
        description.appendText("")
    }

    override fun matchesSafely(textInputLayout: TextInputLayout): Boolean {
        return textInputLayout.error.toString() == errorText
    }

    override fun describeMismatch(item: Any, mismatchDescription: Description) {
        mismatchDescription.appendText(
            "textView.textInputLayout was ${((item as TextInputLayout).error)}"
        )
    }
}
