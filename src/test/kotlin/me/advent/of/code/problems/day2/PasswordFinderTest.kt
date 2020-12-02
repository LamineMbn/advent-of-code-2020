package me.advent.of.code.problems.day2

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PasswordFinderTest {
    
    private val sut = PasswordFinder()

    @Test
    internal fun `We should have two valid passwords`() {
        val passwordsWithCorporatePolicy = listOf("1-3 a: abcde", "1-3 b: cdefg", "2-9 c: ccccccccc")
        val numberOfValidPasswords = sut.retrieveValidPasswordCount(passwordsWithCorporatePolicy)

        numberOfValidPasswords shouldBe 2
    }

    @Test
    internal fun `We should have one valid passwords according to Toboggan policy`() {
        val passwordsWithCorporatePolicy = listOf("1-3 a: abcde", "1-3 b: cdefg", "2-9 c: ccccccccc")
        val numberOfValidPasswords = sut.retrieveTobogganValidPasswordCount(passwordsWithCorporatePolicy)

        numberOfValidPasswords shouldBe 1
    }

    @ParameterizedTest(name = "Password {1} respects policy {0}")
    @CsvSource(value = ["1-3 a: abcde", "2-9 c: ccccccccc"], delimiter = ':')
    internal fun `Passwords should be valid`(rule : String, password: String) {
        
        val isValid = sut.passwordIsValid(rule, password)
        
        isValid shouldBe true
    }

    @ParameterizedTest(name = "Password {1} does not respect policy {0}")
    @CsvSource(value = ["1-3 b: cdefg"], delimiter = ':')
    internal fun `Passwords should not be valid`(rule : String, password: String) {
        
        val isValid = sut.passwordIsValid(rule, password)

        isValid shouldBe false
    }

    @ParameterizedTest(name = "Password {1} does not respects Toboggan policy {0}")
    @CsvSource(value = ["1-3 b: cdefg", "2-9 c: ccccccccc"], delimiter = ':')
    internal fun `Passwords should not be valid according to Toboggan policy`(rule : String, password: String) {

        val isValid = sut.passwordIsValidAccordingToTobogganPolicy(rule, password)

        isValid shouldBe false
    }

    @ParameterizedTest(name = "Password {1} respect Toboggan policy {0}")
    @CsvSource(value = ["1-3 a: abcde", "1-3 a: cbade"], delimiter = ':')
    internal fun `Passwords should be valid according to Toboggan policy`(rule : String, password: String) {

        val isValid = sut.passwordIsValidAccordingToTobogganPolicy(rule, password)

        isValid shouldBe true
    }

    @Test
    internal fun `Extract password from policy with success`() {
        val passwordWithCorporatePolicy = "1-3 b: cdefg"
        val databaseInfo : Pair<String, String> = sut.extractPolicyFromPassword(passwordWithCorporatePolicy)

        databaseInfo.first shouldBe "1-3 b"
        databaseInfo.second shouldBe "cdefg"
    }
    

    @Test
    internal fun `Extract rule information with success`() {
        val rule = "1-3 a"
        val ruleInfo : Pair<IntRange, String> = sut.extractRuleInfo(rule)
        
        ruleInfo.first shouldBe 1..3
        ruleInfo.second shouldBe "a"
    }
}