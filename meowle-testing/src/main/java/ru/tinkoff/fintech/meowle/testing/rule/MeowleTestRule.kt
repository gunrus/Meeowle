package ru.tinkoff.fintech.meowle.testing.rule

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.tinkoff.fintech.meowle.presentation.Mode
import ru.tinkoff.fintech.meowle.testing.prefs.MeowlePrefs

/**
 * @author Ruslan Ganeev
 */
class MeowleTestRule(
    val isAuthorized: Boolean = true,
    val isCompose: Boolean = false,
    val isLocalhost: Boolean = false
) : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                if (isAuthorized) {
                    MeowlePrefs.authorize()
                }

                if (isCompose) {
                    MeowlePrefs.changeUiMode(Mode.COMPOSE)
                }

                if (isLocalhost) {
                    MeowlePrefs.changeAppUrl()
                }

                base.evaluate()

                MeowlePrefs.clear()
            }
        }
    }
}
