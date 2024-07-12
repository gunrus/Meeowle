package ru.tinkoff.fintech.meowle.testing.mock

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock

/**
 * @author Ruslan Ganeev
 */
class PhotosMock  : Mock() {
    override val matcher: MappingBuilder = WireMock.get(WireMock.urlPathMatching(".*/photos"))
}
