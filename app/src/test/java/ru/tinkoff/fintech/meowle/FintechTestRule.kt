package ru.tinkoff.fintech.meowle

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class FintechTestRule : TestRule {
    override fun apply(base: Statement, p1: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                println("TestRule1 Before Test")
                base.evaluate() // our test works here 
                println("TestRule1 After Test")
            }
        }
    }
}

class FintechTestRule2 : TestRule {
    override fun apply(base: Statement, p1: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                println("TestRule2 Before Test")
                base.evaluate() // our test works here
                println("TestRule2 After Test")
            }
        }
    }
}


class FintechTestRule3 : TestRule {
    override fun apply(base: Statement, p1: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                println("TestRule3 Before Test")
                base.evaluate() // our test works here
                println("TestRule3 After Test")
            }
        }
    }
}