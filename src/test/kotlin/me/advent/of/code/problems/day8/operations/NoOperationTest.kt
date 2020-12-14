package me.advent.of.code.problems.day8.operations

import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class NoOperationTest {

    @Test
    internal fun `Should increment by one the index for next operation`() {
        val noOperation = NoOperation()

        val nextIndex = noOperation.execute(-1)

        nextIndex shouldBe 0
    }

    @Test
    internal fun `Should check if index already exist before executing`() {
        val accumulatorValue = 5
        Operation.accumulator = accumulatorValue
        Operation.executedInstructionsIndex = mutableListOf(0)
        
        val noOperation = NoOperation()

        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            noOperation.execute(-1)
        }
        
        exception.shouldHaveMessage("$accumulatorValue")
        
    }
}