package me.advent.of.code.problems.day4

import io.kotest.matchers.shouldBe
import me.advent.of.code.reader.readFile
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class PassportValidatorTest {

    private val input = readFile("src/test/resources/inputs/day4/input_AoC.txt")

    private val sut = PassportValidator(input)

    @Nested
    inner class ValidPassportTest {
        @ParameterizedTest
        @ValueSource(strings = ["ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm"])
        internal fun `Passport info should be valid when all required info are present`(passportInfo: String) {
            val isValid = sut.isPassportValid(passportInfo)

            isValid shouldBe true

        }

        @ParameterizedTest
        @ValueSource(strings = ["ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 hgt:183cm"])
        internal fun `Passport info should still be valid if cid is missing`(passportInfo: String) {
            val isValid = sut.isPassportValid(passportInfo)

            isValid shouldBe true

        }

        @ParameterizedTest(name = "{0} is valid according to new rule")
        @ValueSource(
            strings = ["pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f",
                "eyr:2029 ecl:blu cid:129 byr:1989 iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm",
                "hcl:#888785 hgt:164cm byr:2001 iyr:2015 cid:88 pid:545766238 ecl:hzl eyr:2022"]
        )
        internal fun `Passport info should be valid according to new rules`(passportInfo: String) {
            val isValid = sut.isPassportValid(passportInfo)

            isValid shouldBe true
        }
    }

    @Nested
    inner class InvalidPassportTest {

        @ParameterizedTest
        @ValueSource(strings = ["ecl:gry pid:860033327"])
        internal fun `Passport info should not be valid`(passportInfo: String) {
            val isValid = sut.isPassportValid(passportInfo)

            isValid shouldBe false
        }


        @ParameterizedTest(name = "{0} is not valid according to new rule")
        @ValueSource(
            strings = ["eyr:1972 cid:100 hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926",
                "iyr:2019 hcl:#602927 eyr:1967 hgt:170cm ecl:grn pid:012533040 byr:1946", 
                "hcl:dab227 iyr:2012 ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277",
                "hgt:59cm ecl:zzz eyr:2038 hcl:74454a iyr:2023 pid:3556412378 byr:2007"]
        )
        internal fun `Passport info should not be valid according to new rules`(passportInfo: String) {
            val isValid = sut.isPassportValid(passportInfo)

            isValid shouldBe false
        }
    }

    @Test
    internal fun `Should return 2 valid passports`() {
        val validPassportCount = sut.countValidPassports()

        validPassportCount shouldBe 2
    }

}