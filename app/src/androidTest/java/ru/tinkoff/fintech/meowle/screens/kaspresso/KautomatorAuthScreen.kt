package ru.tinkoff.fintech.meowle.screens.kaspresso

import com.kaspersky.components.kautomator.component.edit.UiEditText
import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.screen.UiScreen
import ru.tinkoff.fintech.meowle.R

/**
 * @author Ruslan Ganeev
 */
class KautomatorAuthScreen : UiScreen<KautomatorAuthScreen>() {

    override val packageName: String = "ru.tinkoff.fintech.meowle"

    private val nameField = UiEditText { withId(this@KautomatorAuthScreen.packageName, "et_name") }
    private val phoneField = UiEditText { withId(R.id.et_phone) }
    private val submitButton = UiButton { withId(R.id.submit_button) }

    fun enterName(name: String) {
        nameField.replaceText(name)
    }

    fun checkName(name: String) {
        nameField.hasText(name)
    }

    fun enterPhone(phone: String) {
        phoneField.replaceText(phone)
    }

    fun clickSubmit() {
        submitButton.click()
    }
}