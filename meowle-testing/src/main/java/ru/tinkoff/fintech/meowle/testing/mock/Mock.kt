package ru.tinkoff.fintech.meowle.testing.mock

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import io.qameta.allure.kotlin.Allure.step
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**
 * @author Ruslan Ganeev
 */
abstract class Mock {

    abstract val matcher: MappingBuilder

    inline fun <reified T> respondWith(response: T) {
        respondWith(200, response)
    }

    inline fun <reified T> respondWith(code: Int, response: T) {
        val body = if (response is String) response else Json.encodeToString(serializer(), response)
        val responseDefinition = WireMock.aResponse().withStatus(code).withBody(body)
        stubFor(matcher.willReturn(responseDefinition)).also {
            step("Мокируем запрос  ${it.request} \n ${it.response} ") {}
        }
    }
}
