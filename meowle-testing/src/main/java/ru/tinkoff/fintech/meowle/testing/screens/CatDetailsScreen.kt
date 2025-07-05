package ru.tinkoff.fintech.meowle.testing.screens

import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.text.KTextView
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.view.fragments.DetailsFragment

/**
 * @author Ruslan Ganeev
 */
class CatDetailsScreen(testContext: TestContext<*>) : BaseScreen(testContext) {
    override val layoutId: Int = R.layout.details_fragment_redesign
    override val viewClass: Class<*> = DetailsFragment::class.java

    private val name = KTextView { withId(R.id.cat_name) }

    fun checkCatName(catName: String) {
        step("Проверяем имя котика") {
            name.hasText(catName)
        }
    }

    companion object {
        const val SCREEN_NAME = "Экран деталей котика"

        inline operator fun invoke(testContext: TestContext<*>, crossinline block: CatDetailsScreen.() -> Unit) {
            testContext.step(SCREEN_NAME) {
                CatDetailsScreen(testContext).apply {
                    block()
                }
            }
        }
    }
}
