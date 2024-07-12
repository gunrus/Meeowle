package ru.tinkoff.fintech.meowle.testing.mock

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching

/**
 * @author Ruslan Ganeev
 */
class DetailsMock : Mock() {
    override val matcher: MappingBuilder = get(urlPathMatching(".*/get-by-id"))
}