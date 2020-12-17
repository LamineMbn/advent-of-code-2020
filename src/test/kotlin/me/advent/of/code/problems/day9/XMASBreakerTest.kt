package me.advent.of.code.problems.day9

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import me.advent.of.code.reader.readFile
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class XMASBreakerTest {
    private val input = readFile("src/test/resources/inputs/day9/input_AoC.txt")

    private val xmasBreaker = XMASBreaker(input.map { it.toInt() })
    
    @Test
    internal fun `Should sum up values two by two`() {
        val data = listOf(1,2,3,4,5)
        val sumTwoByTwo = xmasBreaker.cartesianSum(data)
        
        sumTwoByTwo.shouldContainExactlyInAnyOrder(3,4,5,6,7,8,9)
    }

    @Test
    internal fun `Should return n number before current index`() {
        val data = listOf(1,2,3,4,5)
        val preambleList = xmasBreaker.retrievePreambleNumbers(data, currentIndex = 3, preamble= 2)

        preambleList.shouldContainExactlyInAnyOrder(2,3)
    }

    @Test
    internal fun `Should find 127 as the only number that is not the sum of any of the 5 number before it`() {
        val wrongNumber = xmasBreaker.findXMASWeakness(preamble = 5)
        
        wrongNumber shouldBe 127
    }
}