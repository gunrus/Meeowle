package ru.tinkoff.fintech.meowle.testing.mock

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.stubbing.Scenario

/**
 * @author Ruslan Ganeev
 */
object WireMockScenarioFactory {

    private var scenarioState = 0

    fun addMock(matcher: MappingBuilder, response: ResponseDefinitionBuilder) {
        stubFor(
            matcher
                .inScenario("scenario")
                .whenScenarioStateIs(if (scenarioState != 0) (scenarioState - 1).toString() else Scenario.STARTED)
                .willReturn(response)
                .willSetStateTo((scenarioState).toString())
        )
        scenarioState += 1
    }

    fun resetState() {
        scenarioState = 0
    }
}
