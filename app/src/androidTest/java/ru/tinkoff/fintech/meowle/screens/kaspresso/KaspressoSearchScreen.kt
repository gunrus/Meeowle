package ru.tinkoff.fintech.meowle.screens.kaspresso

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import ru.tinkoff.fintech.meowle.R

/**
 * @author Ruslan Ganeev
 */
class KaspressoSearchScreen : KScreen<KaspressoAuthScreen>() {

    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    private val searchInput = KEditText { withId(R.id.et_search) }
    private val searchButton = KButton { withId(R.id.search_button) }
    private val catsList = KRecyclerView(
        builder = { withId(R.id.rv_search_result_list) },
        itemTypeBuilder = { itemType(::CatCard) }
    )

    fun findCat(catName: String) {
        searchInput.replaceText(catName)
        searchButton.click()
    }

    fun checkCatName(catName: String, position: Int) {
        catsList.childAt<CatCard>(position) {
            this.catName.hasText(catName)
        }
    }

    fun checkCatsListSize(size: Int) {
        catsList.hasSize(size)
    }
}

private class CatCard(matcher: Matcher<View>) : KRecyclerItem<CatCard>(matcher) {
    val catName = KTextView(matcher) { withId(R.id.cat_name) }
}