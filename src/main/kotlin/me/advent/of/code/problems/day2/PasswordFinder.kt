package me.advent.of.code.problems.day2

import me.advent.of.code.reader.readFile

fun main(){
    val passwordDatabase = readFile("src/main/resources/inputs/day2/input_AoC.txt")
    
    val passwordFinder = PasswordFinder()
    
    val numberOfValidPasswords = passwordFinder.retrieveValidPasswordCount(passwordDatabase)
    val numberOfTobogganValidPasswords = passwordFinder.retrieveTobogganValidPasswordCount(passwordDatabase)

    println("There is $numberOfValidPasswords correct passwords")
    println("There is $numberOfTobogganValidPasswords correct passwords according to Toboggan policy")
}

class PasswordFinder {

    fun retrieveValidPasswordCount(passwordsWithCorporatePolicy: List<String>) =
        passwordsWithCorporatePolicy.map { extractPolicyFromPassword(it) }
                .count { passwordIsValid(it.policy, it.password) }

    fun retrieveTobogganValidPasswordCount(passwordsWithCorporatePolicy: List<String>) =
        passwordsWithCorporatePolicy.map { extractPolicyFromPassword(it) }
                .count { passwordIsValidAccordingToTobogganPolicy(it.policy, it.password) }

    fun extractPolicyFromPassword(passwordWithCorporatePolicy: String): Pair<String, String> {
        return passwordWithCorporatePolicy.split(":").map { it.trim() }.zipWithNext().first()
    }

    fun passwordIsValidAccordingToTobogganPolicy(policy: String, password: String): Boolean {

        val policyInfo: Pair<IntRange, String> = extractRuleInfo(policy)
        val positionPolicy = policyInfo.occurrencePolicy
        val letterConcerned = policyInfo.letterConcerned
        
        val firstIndex = positionPolicy.first - 1
        val lastIndex = positionPolicy.last - 1


        return (password[firstIndex].toString() == letterConcerned).xor(password[lastIndex].toString() == letterConcerned)
    }

    fun passwordIsValid(policy: String, password: String): Boolean {

        val policyInfo: Pair<IntRange, String> = extractRuleInfo(policy)
        val occurrencePolicy = policyInfo.occurrencePolicy
        val letterConcerned = policyInfo.letterConcerned


        return password.count { it.toString() == letterConcerned } in occurrencePolicy
    }

    fun extractRuleInfo(policy: String): Pair<IntRange, String> {
        val splitRule: List<String> = policy.split(" ")
        return splitRule.first().toRange("-") to splitRule.last()
    }

    private fun String.toRange(delimiter: String): IntRange {
        val numberOfOccurrenceAllowed: List<Int> = this.split(delimiter).map { it.toInt() }
        return numberOfOccurrenceAllowed.first()..numberOfOccurrenceAllowed.last()
    }

    private val Pair<IntRange, String>.occurrencePolicy: IntRange get() = this.first
    private val Pair<IntRange, String>.letterConcerned: String get() = this.second

    private val Pair<String, String>.policy: String get() = this.first
    private val Pair<String, String>.password: String get() = this.second

}