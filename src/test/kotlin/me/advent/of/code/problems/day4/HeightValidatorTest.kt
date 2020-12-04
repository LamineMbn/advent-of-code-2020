package me.advent.of.code.problems.day4

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class HeightValidatorTest{

    @ParameterizedTest(name = "{0}cm is valid")
    @ValueSource(strings = ["150", "160", "193"])
    internal fun `Height in cm should be valid`(height : String) {
        val heightValidator = HeightValidator(height + "cm")

        heightValidator.isValid() shouldBe  true
    }

    @ParameterizedTest(name = "{0}cm is not valid")
    @ValueSource(strings = ["149", "203"])
    internal fun `Height in cm should not be valid`(height : String) {
        val heightValidator = HeightValidator(height + "cm")

        heightValidator.isValid() shouldBe  false
    }

    @ParameterizedTest(name = "{0}in is valid")
    @ValueSource(strings = ["59", "62", "76"])
    internal fun `Height in inch should be valid`(height : String) {
        val heightValidator = HeightValidator(height + "in")

        heightValidator.isValid() shouldBe  true
    }

    @ParameterizedTest(name = "{0}in is not valid")
    @ValueSource(strings = ["49", "80"])
    internal fun `Height in inch should not be valid`(height : String) {
        val heightValidator = HeightValidator(height + "in")

        heightValidator.isValid() shouldBe  false
    }
}