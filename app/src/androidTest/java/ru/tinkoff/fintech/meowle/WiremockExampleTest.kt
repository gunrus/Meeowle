package ru.tinkoff.fintech.meowle

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomakehurst.wiremock.stubbing.Scenario
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.fintech.meowle.presentation.MainActivity
import ru.tinkoff.fintech.meowle.testing.rule.MeowleTestRule
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Thread.sleep
import java.nio.charset.StandardCharsets

/**
 * @author Ruslan Ganeev
 */
class WiremockExampleTest {

    @get:Rule
    val testRule = MeowleTestRule(isLocalhost = true)

    @get: Rule
    val mock = WireMockRule(5000)

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun simpleStub() {
        stubFor(
            get("/api/likes/cats/rating")
                .willReturn(
                    aResponse()
                        .withStatus(500)
                        .withBody("Hello there!")
                )
        )

        onView(withId(R.id.tab_btn_rating))
            .perform(click())

        sleep(10_000)
    }

    @Test
    fun scenario() {
        val scenario = "Rating"

        stubFor(
            get("/api/likes/cats/rating")
                .inScenario(scenario)
                .whenScenarioStateIs(Scenario.STARTED)
                .willSetStateTo("Second")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(fileToString("rating_response.json"))
                )
        )

        stubFor(
            get("/api/likes/cats/rating")
                .inScenario(scenario)
                .whenScenarioStateIs("Second")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(fileToString("rating_empty_response.json"))
                )
        )

        onView(withId(R.id.tab_btn_rating))
            .perform(click())

        sleep(3_000)

        onView(withContentDescription(R.string.rating_tab_dislikes_title))
            .perform(click())

        sleep(3_000)
    }

    @Test
    fun verifyTest() {
        stubFor(
            get("/api/likes/cats/rating")
                .willReturn(
                    aResponse()
                        .withStatus(500)
                        .withBody("Hello there!")
                )
        )

        onView(withId(R.id.tab_btn_rating))
            .perform(click())

        verify(
            getRequestedFor(urlPathMatching(".*/rating"))
                .withHeader("Accept-Encoding", equalTo("gzip"))
        )
    }

    fun fileToString(path: String, context: Context = InstrumentationRegistry.getInstrumentation().context): String {
        return BufferedReader(InputStreamReader(context.assets.open(path), StandardCharsets.UTF_8)).readText()
    }
}
