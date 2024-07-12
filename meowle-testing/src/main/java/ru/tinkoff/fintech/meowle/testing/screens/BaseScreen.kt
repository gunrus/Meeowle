package ru.tinkoff.fintech.meowle.testing.screens

import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.kaspersky.kaspresso.testcases.models.info.StepInfo

/**
 * @author Ruslan Ganeev
 */
abstract class BaseScreen(protected val testContext: TestContext<*>) : KScreen<BaseScreen>() {

    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    fun step(description: String, actions: (StepInfo) -> Unit) {
        testContext.step(description, actions)
    }
}
