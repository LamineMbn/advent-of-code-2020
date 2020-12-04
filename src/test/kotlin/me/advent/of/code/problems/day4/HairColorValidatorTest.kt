package me.advent.of.code.problems.day4

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class HairColorValidatorTest {
    @ParameterizedTest(name = "{0} is valid")
    @ValueSource(strings = ["#123abc"])
    internal fun `Hair color should be valid`(hairColor: String) {
        val hairColorValidator = HairColorValidator(hairColor)

        hairColorValidator.isValid() shouldBe true
    }

    @ParameterizedTest(name = "{0} is not valid")
    @ValueSource(strings = ["#123abz", "123abz", "1#23abz"])
    internal fun `Hair color should not be valid`(hairColor: String) {
        val hairColorValidator = HairColorValidator(hairColor)

        hairColorValidator.isValid() shouldBe false
    }
}