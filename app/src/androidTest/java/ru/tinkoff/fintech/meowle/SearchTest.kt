package ru.tinkoff.fintech.meowle

import androidx.test.core.app.ActivityScenario
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.tinkoff.fintech.meowle.presentation.view.AuthActivity
import ru.tinkoff.fintech.meowle.testing.mock.MeowleMock.meowleMock
import ru.tinkoff.fintech.meowle.testing.mock.response.DetailsResponseFactory.catDetails
import ru.tinkoff.fintech.meowle.testing.mock.response.PhotosResponseFactory
import ru.tinkoff.fintech.meowle.testing.mock.response.SearchResponseFactory.cats
import ru.tinkoff.fintech.meowle.testing.prefs.MeowlePrefs
import ru.tinkoff.fintech.meowle.testing.screens.CatDetailsScreen
import ru.tinkoff.fintech.meowle.testing.screens.SearchScreen

/**
 * @author Ruslan Ganeev
 */
class SearchTest : BaseTest() {

    @Before
    fun setUp() {
        MeowlePrefs.authorize()
        MeowlePrefs.changeAppUrl()

        meowleMock {
            search.respondWith(cats())
            details.respondWith(catDetails())
            photos.respondWith(PhotosResponseFactory.photos())
        }
    }

    @After
    fun tearDown() {
        MeowlePrefs.clear()
    }

    @Test
    fun searchTest() = run {
        ActivityScenario.launch(AuthActivity::class.java)

        SearchScreen(this) {
            checkScreenOpened()
            findCat("Пушок")
            openCatDetails("Пушок")
        }

        CatDetailsScreen(this) {
            checkCatName("Пушок")
        }
    }
}
