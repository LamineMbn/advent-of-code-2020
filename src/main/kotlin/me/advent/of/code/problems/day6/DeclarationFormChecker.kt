package me.advent.of.code.problems.day6

import me.advent.of.code.reader.readFile
import me.advent.of.code.reader.separateInputData

fun main() {
    val input = readFile("src/main/resources/inputs/day6/input_AoC.txt")

    val declarationFormChecker = DeclarationFormChecker(input)

    val affirmativeAnswers = declarationFormChecker.countAllAffirmativeResponse()

    println("There is $affirmativeAnswers affirmative answers")
}

class DeclarationFormChecker(initialInput: List<String>) {

    var input: List<String> = separateInputData(initialInput)

    fun countAllAffirmativeResponse(): Int {
        return input.map { commonResponse(it) }
                .filterNot { it.isEmpty() }
                .map { value -> countAffirmativeResponse(value) }
                .reduce { a, b -> a + b }
    }

    fun countAffirmativeResponse(question: String): Int {
        return question.toCharArray().distinct().count()
    }

    fun commonResponse(question: String): String {
        return question.split(" ").asSequence()
                .map { it.toCharArray().toSet() }
                .reduce { a, b -> a.intersect(b) }
                .joinToString(separator = "") { it.toString() }
    }


}