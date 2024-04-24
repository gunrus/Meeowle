package ru.tinkoff.fintech.meowle

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Before
    fun beforeTest() {
        println("Before")
    }

    @After
    fun afterTest() {
        println("After")
    }

//    @get:Rule
    val rule = FintechTestRule()

//    @get:Rule
    val rule3 = FintechTestRule3()

//    @get:Rule
    val rule2 = FintechTestRule2()

    @get:Rule
    val ruleChain = RuleChain
        .outerRule(rule)
        .around(rule2)
        .around(rule3)

    @Test
    fun addition_isCorrect() {
        println("Test1 is here")
    }
}