package me.advent.of.code.problems.day6

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveLength
import jdk.nashorn.internal.ir.annotations.Ignore
import me.advent.of.code.reader.readFile
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class DeclarationFormCheckerTest {
    private val initialInput = readFile("src/test/resources/inputs/day6/input_AoC.txt")

    private val sut = DeclarationFormChecker(initialInput)

    @ParameterizedTest(name = "{0} contains three yes")
    @ValueSource(strings = ["abc", "aabc"])
    internal fun `Should count 3 yes`(question: String) {
        val affirmativeAnswers = sut.countAffirmativeResponse(question)
        
        print(sut.input)
        
        affirmativeAnswers shouldBe 3
    }

    @ParameterizedTest(name = "{0} intersection gives us a")
    @ValueSource(strings = ["ab ac", "ab ac ad"])
    internal fun `Should intersection gives us a`(question: String) {
        val commonAnswer = sut.commonResponse(question)

        commonAnswer shouldBe "a"
    }

    @ParameterizedTest(name = "{0} intersection gives us empty string")
    @ValueSource(strings = ["a b c"])
    internal fun `Should intersection gives us empty string`(question: String) {
        val commonAnswer = sut.commonResponse(question)

        commonAnswer shouldBe ""
    }

    @Test
    internal fun `Input should be correctly formatted`() {
        val input = sut.input
        
        input.shouldContainExactly("abc", "a b c", "ab ac", "a a a a", "b")
    }

    @Test
    internal fun `The sum of all the affirmative response should be 6`() {
        val affirmativeAnswers = sut.countAllAffirmativeResponse()

        affirmativeAnswers shouldBe 6
    }
}