package ru.tinkoff.fintech.meowle.testing.mock

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import ru.tinkoff.fintech.meowle.testing.mock.response.DetailsResponseFactory.catDetails
import ru.tinkoff.fintech.meowle.testing.mock.response.PhotosResponseFactory.photos
import ru.tinkoff.fintech.meowle.testing.mock.response.SearchResponseFactory.cats

/**
 * @author Ruslan Ganeev
 */
class MockScenario {

    private inline infix fun <reified T>  Mock.respondInScenarioWith(response: T) {
        this.respondInScenarioWith(200, response)
    }

    private inline fun <reified T>  Mock.respondInScenarioWith(code: Int, response: T) {
        val body = if (response is String) response else Json.encodeToString(serializer(), response)
        val responseDefinition = aResponse().withStatus(code).withBody(body)
        WireMockScenarioFactory.addMock(this.matcher, responseDefinition)
    }

    fun searchAndOpenDetails() {
        SearchMock() respondInScenarioWith cats()
        DetailsMock() respondInScenarioWith catDetails()
        PhotosMock() respondInScenarioWith photos()
    }
}
