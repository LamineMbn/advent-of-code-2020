package me.advent.of.code.problems.day4

import me.advent.of.code.reader.readFile
import me.advent.of.code.reader.separateInputData

fun main() {
    val input = readFile("src/main/resources/inputs/day4/input_AoC.txt")
    val passportValidator = PassportValidator(input)

    val validPassportCount = passportValidator.countValidPassports()

    println("There is $validPassportCount valid passports")
}

class PassportValidator(private val input: List<String>) {

    companion object {
        val REQUIRED_FIELDS = PassportFieldEnum.values()
                .filterNot { it == PassportFieldEnum.COUNTRY_ID }
                .map { it.code }
    }

    fun countValidPassports(): Int {
        return separateInputData(input).count { isPassportValid(it) }
    }

    fun isPassportValid(passportInfo: String): Boolean {

        val convertedPassportInfo = convertToPassport(passportInfo)

        var passportIsValid = convertedPassportInfo.keys.containsAll(REQUIRED_FIELDS)

        PassportFieldEnum.values().forEach { field ->
            val valueToValidate = convertedPassportInfo[field.code] ?: ""
            passportIsValid = passportIsValid.and(field.isValid(valueToValidate))
        }


        return passportIsValid
    }

    private fun convertToPassport(passportInfo: String): Map<String, String> {
        return passportInfo.split(" ").map { it.split(":") }.map { it[0] to it[1] }.toMap()
    }


}

enum class PassportFieldEnum(val code: String) {
    BIRTH_YEAR("byr") {
        override fun isValid(data: String): Boolean {
            return BirthYearValidator(data).isValid()
        }
    },
    ISSUE_YEAR("iyr") {
        override fun isValid(data: String): Boolean {
            return IssueYearValidator(data).isValid()
        }
    },
    EXPIRATION_YEAR("eyr") {
        override fun isValid(data: String): Boolean {
            return ExpirationYearValidator(data).isValid()
        }
    },
    HEIGHT("hgt") {
        override fun isValid(data: String): Boolean {
            return HeightValidator(data).isValid()
        }
    },
    HAIR_COLOR("hcl") {
        override fun isValid(data: String): Boolean {
            return HairColorValidator(data).isValid()
        }
    },
    EYE_COLOR("ecl") {
        override fun isValid(data: String): Boolean {
            return EyeColorValidator(data).isValid()
        }
    },
    PASSPORT_ID("pid") {
        override fun isValid(data: String): Boolean {
            return PidValidator(data).isValid()
        }
    },
    COUNTRY_ID("cid") {
        override fun isValid(data: String): Boolean {
            return true
        }
    };

    abstract fun isValid(data: String): Boolean
}

interface Validator {
    fun isValid(): Boolean
}

class BirthYearValidator(private val birthYear: String) : Validator {

    override fun isValid(): Boolean {
        return birthYear.length == 4 && birthYear in "1920".."2002"
    }
}

class IssueYearValidator(private val issueYear: String) : Validator {

    override fun isValid(): Boolean {
        return issueYear.length == 4 && issueYear in "2010".."2020"
    }
}

class ExpirationYearValidator(private val expirationYear: String) : Validator {

    override fun isValid(): Boolean {
        return expirationYear.length == 4 && expirationYear in "2020".."2030"
    }
}

class HeightValidator(private val height: String) : Validator {

    override fun isValid(): Boolean {
        var isValid = false
        if (height.contains("cm")) isValid = height.removeSuffix("cm") in "150".."193"
        if (height.contains("in")) isValid = height.removeSuffix("in") in "59".."76"
        return isValid
    }
}

class HairColorValidator(private val hairColor: String) : Validator {

    override fun isValid(): Boolean {
        return hairColor.startsWith("#") && hairColor.substringAfter("#").matches(Regex("^[0-9a-f]{6}\$"))
    }
}

class EyeColorValidator(private val eyeColor: String) : Validator {

    companion object {
        val EYE_COLOR_ACCEPTED = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    }

    override fun isValid(): Boolean {
        return eyeColor in EYE_COLOR_ACCEPTED
    }
}

class PidValidator(private val pid: String) : Validator {
    override fun isValid(): Boolean {
        return pid.matches(Regex("^[0-9]{9}\$"))
    }
}