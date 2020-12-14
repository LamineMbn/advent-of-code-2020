package me.advent.of.code.problems.day8.operations

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AccumulationTest{

    @BeforeEach
    internal fun setUp() {
        Operation.accumulator = 0
    }

    @Test
    internal fun `The accumulation operation should increase the accumulator`() {
        val operation = Accumulation(+5)
        
        operation.execute(0)

        Operation.accumulator shouldBe 5
    }

    @Test
    internal fun `The accumulation operation should decrease the accumulator`() {
        val operation = Accumulation(-5)

        operation.execute(0)

        Operation.accumulator shouldBe -5
    }

    @Test
    internal fun `Successive operations should modify the accumulator`() {
        val increaseOperation = Accumulation(+13)
        val decreaseOperation = Accumulation(-5)

        increaseOperation.execute(0)
        decreaseOperation.execute(0)

        Operation.accumulator shouldBe 8
    }

    @Test
    internal fun `Should increment by one the index for next operation`() {
        val increaseOperation = Accumulation(+13)

        val nextIndex = increaseOperation.execute(5)

        nextIndex shouldBe 6
    }
}