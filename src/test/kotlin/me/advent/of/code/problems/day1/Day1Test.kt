package me.advent.of.code.problems.day1

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day1Test {

    private val inputExample = listOf(1721, 979, 366, 299, 675, 1456)
    
    private val sut = Day1()

    @Test
    internal fun `We should have a result of 514579 for the input example when the sum of two values give us 2020`() {
        val product = sut.fixReport(inputExample)
        
        product shouldBe 514579
    }

    @Test
    internal fun `We should have a result of 241861950 for the input example when the sum of three values give us 2020`() {
        val product = sut.fixReport(inputExample, 3)

        product shouldBe 241861950
    }
}