package ru.tinkoff.fintech.meowle.kaspresso

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.components.alluresupport.interceptors.step.AllureMapperStepInterceptor
import com.kaspersky.kaspresso.interceptors.watcher.testcase.impl.views.DumpViewsInterceptor
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.FlakySafetyParams
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.tinkoff.fintech.meowle.PreferenceRule
import ru.tinkoff.fintech.meowle.presentation.MainActivity
import ru.tinkoff.fintech.meowle.screens.kaspresso.KaspressoSearchScreen

/**
 * @author Ruslan Ganeev
 */
class KaspressoRecyclerAdvancedTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.simple(
        customize = {
            flakySafetyParams = FlakySafetyParams.custom(timeoutMs = 6_000, intervalMs = 250)
        }
    ).apply {
        stepWatcherInterceptors.addAll(listOf(AllureMapperStepInterceptor()))
        testRunWatcherInterceptors.addAll(listOf(DumpViewsInterceptor(viewHierarchyDumper)))
    }
) {

    @get:Rule
    val prefs = PreferenceRule()

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun search() = run {
        val searchScreen = KaspressoSearchScreen()

        searchScreen.findCat("Саня")

        searchScreen.checkCatName("Саня", 0)
        searchScreen.checkCatsListSize(3)
    }
}