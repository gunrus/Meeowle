package ru.tinkoff.fintech.meowle.testing.mock

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching

/**
 * @author Ruslan Ganeev
 */
class SearchMock : Mock() {
    override val matcher: MappingBuilder = post(urlPathMatching(".*/search"))
}
