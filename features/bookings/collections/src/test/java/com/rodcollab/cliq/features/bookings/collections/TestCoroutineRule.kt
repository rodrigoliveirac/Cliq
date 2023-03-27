package com.rodcollab.cliq.features.bookings.collections

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule{

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    override fun apply(base: Statement, description: Description): Statement = object: Statement() {
        override fun evaluate() {
            Dispatchers.setMain(dispatcher)

            base.evaluate()

            Dispatchers.resetMain()
        }
    }

    fun runTest(block: suspend TestScope.() -> Unit) =
        scope.runTest {
            block()
        }
}
