package ru.tinkoff.fintech.meowle.testing.screens

import android.view.View
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.view.fragments.SearchFragment

/**
 * @author Ruslan Ganeev
 */
class SearchScreen(testContext: TestContext<*>) : BaseScreen(testContext) {

    override val layoutId: Int = R.layout.search_fragment
    override val viewClass: Class<*> = SearchFragment::class.java

    private val title = KTextView { withId(R.id.tw_search_title) }
    private val searchField = KTextInputLayout { withId(R.id.til_search) }
    private val catsList = KRecyclerView(
        builder = { withId(R.id.rv_search_result_list) },
        itemTypeBuilder = { itemType(::CatCard) }
    )

    fun enterSearchText(searchText: String) {
        step("Вводим текст: $searchText") {
            searchField.edit.replaceText(searchText)
        }
    }

    fun findCat(catName: String) {
        step("Поиск кота с именем $catName") {
            searchField.edit.replaceText(catName)
            searchField.edit.pressImeAction()
        }
    }

    fun checkCatName(catName: String, position: Int) {
        step("Проверяем имя кота на позиции $position") {
            catsList.childAt<CatCard>(position) {
                this.catName.containsText(catName)
            }
        }
    }

    fun checkCatsListSize(size: Int) {
        step("Проверяем размер списка найденных котов") {
            catsList.hasSize(size)
        }
    }

    fun openCatDetails(catName: String) {
        step("Открываем детали котика $catName") {
            catsList.childWith<CatCard> {
                withDescendant { containsText(catName) }
            } perform {
                click()
            }
        }
    }

    fun checkScreenOpened() {
        step("Проверяем что экран открылся") {
            title.isDisplayed()
        }
    }

    private class CatCard(matcher: Matcher<View>) : KRecyclerItem<CatCard>(matcher) {
        val catName = KTextView(matcher) { withId(R.id.cat_name) }
    }

    companion object {
        const val SCREEN_NAME = "Экран поиска"

        inline operator fun invoke(testContext: TestContext<*>, crossinline block: SearchScreen.() -> Unit) {
            testContext.step(SCREEN_NAME) {
                SearchScreen(testContext).apply {
                    block()
                }
            }
        }
    }
}
