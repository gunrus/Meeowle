package ru.tinkoff.fintech.meowle.uiautomator

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.fintech.meowle.presentation.MainActivity

/**
 * @author Ruslan Ganeev
 */
@RunWith(AndroidJUnit4::class)
class UiAutomatorAdvancedExample {

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val meowlePrefs = appContext.getSharedPreferences("meowle", Context.MODE_PRIVATE)

    @Before
    fun setUp() {
        meowlePrefs.edit().putBoolean("auth", true).commit()
    }

    @Test
    fun settings() {
        ActivityScenario.launch(MainActivity::class.java)

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val uiDevice = UiDevice.getInstance(instrumentation)

        val searchTitleSelector = By.res("ru.tinkoff.fintech.meowle", "tw_search_title")
        val searchTitleText = uiDevice.findObject(searchTitleSelector).text

        val tabsSelector = By.res("ru.tinkoff.fintech.meowle","navigation_bar_item_icon_view")
        val settingsTab = uiDevice.findObjects(tabsSelector)[4]
        settingsTab.click()

        val settingsTitleSelector = By.res("ru.tinkoff.fintech.meowle","tw_settings_title")
        val settingsTitleText = uiDevice.findObject(settingsTitleSelector).text
        Assert.assertEquals("Настройки", settingsTitleText)
    }
}