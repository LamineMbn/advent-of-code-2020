package me.advent.of.code.problems.day8.operations

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class JumpTest {

    @Test
    internal fun `Should jump to next operation`() {
        val jumpOperation = Jump(-4)

        val nextIndex = jumpOperation.execute(7)

        nextIndex shouldBe 3
    }
}