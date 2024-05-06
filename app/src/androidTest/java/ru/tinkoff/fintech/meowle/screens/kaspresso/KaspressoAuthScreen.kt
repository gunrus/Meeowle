package ru.tinkoff.fintech.meowle.screens.kaspresso

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.text.KButton
import ru.tinkoff.fintech.meowle.R

/**
 * @author Ruslan Ganeev
 */
class KaspressoAuthScreen : KScreen<KaspressoAuthScreen>() {

    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    private val nameField = KTextInputLayout { withId(R.id.til_name) }
    private val phoneField = KTextInputLayout { withId(R.id.til_phone) }
    private val submitButton = KButton { withId(R.id.submit_button) }

    fun enterName(name: String) {
        nameField.edit.replaceText(name)
    }

    fun checkName(name: String) {
        nameField.edit.hasText(name)
    }

    fun enterPhone(phone: String) {
        phoneField.edit.replaceText(phone)
    }

    fun clickSubmit() {
        submitButton.click()
    }
}