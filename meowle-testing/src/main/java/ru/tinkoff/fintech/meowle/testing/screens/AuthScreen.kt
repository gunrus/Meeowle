package ru.tinkoff.fintech.meowle.testing.screens

import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.text.KButton
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity

/**
 * @author Ruslan Ganeev
 */
class AuthScreen(testContext: TestContext<*>) : BaseScreen(testContext) {

    override val layoutId: Int = R.layout.auth_activity
    override val viewClass: Class<*> = AuthActivity::class.java

    private val nameField = KTextInputLayout { withId(R.id.til_name) }
    private val phoneField = KTextInputLayout { withId(R.id.til_phone) }
    private val loginButton = KButton { withId(R.id.submit_button) }

    fun enterName(name: String) {
        step("Вводим имя: $name") {
            nameField.edit.replaceText(name)
        }
    }

    fun enterPhone(phone: String) {
        step("Вводим телефон: $phone") {
            phoneField.edit.replaceText(phone)
        }
    }

    fun clickLogin() {
        step("Нажимаем кнопку входа") {
            loginButton.click()
        }
    }

    companion object {
        const val SCREEN_NAME = "Экран авторизации"

        inline operator fun invoke(testContext: TestContext<*>, crossinline block: AuthScreen.() -> Unit) {
            testContext.step(SCREEN_NAME) {
                AuthScreen(testContext).apply {
                    block()
                }
            }
        }
    }
}
