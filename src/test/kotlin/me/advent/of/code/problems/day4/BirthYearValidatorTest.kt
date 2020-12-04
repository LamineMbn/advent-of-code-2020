package me.advent.of.code.problems.day4

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class BirthYearValidatorTest {

    @ParameterizedTest(name = "{0} is valid")
    @ValueSource(strings = ["1920", "1937", "2002"])
    internal fun `Should birth year be valid`(birthYear: String) {
        val birthValidator = BirthYearValidator(birthYear)

        birthValidator.isValid() shouldBe true
    }

    @ParameterizedTest(name = "{0} is not valid")
    @ValueSource(strings = ["1919", "193", "2003"])
    internal fun `Should birth year not be valid`(birthYear: String) {
        val birthValidator = BirthYearValidator(birthYear)

        birthValidator.isValid() shouldBe false
    }
}