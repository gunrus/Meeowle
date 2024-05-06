package ru.tinkoff.fintech.meowle.screens.kaspresso

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import io.qameta.allure.kotlin.Allure
import org.hamcrest.Matcher
import ru.tinkoff.fintech.meowle.R

/**
 * @author Ruslan Ganeev
 */
class KaspressoSearchScreen : KScreen<KaspressoAuthScreen>() {

    override val layoutId: Int = R.layout.search_fragment
    override val viewClass: Class<*>? = null

    private val searchInput = KEditText { withId(R.id.et_search) }

    private val catsList = KRecyclerView(
        builder = { withId(R.id.rv_search_result_list) },
        itemTypeBuilder = { itemType(::CatCard) }
    )

    fun findCat(catName: String) {
        Allure.step("Поиск кота") {
            searchInput.replaceText(catName)
            searchInput.pressImeAction()
        }
    }

    fun checkCatName(catName: String, position: Int) {
        catsList.childAt<CatCard>(position) {
            this.catName.containsText(catName)
        }
    }

    fun checkCatsListSize(size: Int) {
        catsList.hasSize(size)
    }
}

private class CatCard(matcher: Matcher<View>) : KRecyclerItem<CatCard>(matcher) {
    val catName = KTextView(matcher) { withId(R.id.cat_name) }
}