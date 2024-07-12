package ru.tinkoff.fintech.meowle

import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.kaspersky.components.alluresupport.interceptors.step.AllureMapperStepInterceptor
import com.kaspersky.kaspresso.interceptors.watcher.testcase.impl.views.DumpViewsInterceptor
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.FlakySafetyParams
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule

/**
 * @author Ruslan Ganeev
 */
abstract class BaseTest(timeoutInMillis: Long = 10_000, intervalInMillis: Long = 500) : TestCase(
    kaspressoBuilder = Kaspresso.Builder.simple(
        customize = {
            flakySafetyParams = FlakySafetyParams.custom(timeoutMs = timeoutInMillis, intervalMs = intervalInMillis)
        }
    ).apply {
        stepWatcherInterceptors.addAll(listOf(AllureMapperStepInterceptor()))
    }
) {

    @get: Rule
    val wireMockRule = WireMockRule(5000)
}
