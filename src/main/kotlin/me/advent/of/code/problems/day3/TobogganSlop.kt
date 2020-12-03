package me.advent.of.code.problems.day3

import me.advent.of.code.reader.readFileLineByLine

fun main() {
    val input = readFileLineByLine("src/main/resources/inputs/day3/input_AoC.txt").map { it.toCharArray() }

    val tobogganSlop = TobogganSlop(input)

    val treeCount = tobogganSlop.calculateNumberOfTreesInTheWay()
    val allTreeEncountered = tobogganSlop.productOfAllTreeEncountered()

    println("There''s $treeCount trees in the way")
    println("$allTreeEncountered is the product of all encountered trees in different movement")
}

class TobogganSlop(private val input: List<CharArray>) {

    private var slopSize: Pair<Int, Int>

    init {
        slopSize = retrieveSlopSize(input)
    }

    fun productOfAllTreeEncountered(): Long {
        return ShiftingEnum.values().map { calculateNumberOfTreesInTheWay(it.movement) }.map { it.toLong() }.reduce { a, b -> a * b }
    }


    fun calculateNumberOfTreesInTheWay(movement: Pair<Int, Int> = ShiftingEnum.RIGHT_THREE_DOWN_ONE_MVT.movement): Int {
        val treeCoordinates = retrieveTreeCoordinates(input)

        var startingPoint = 0 to 0
        var treeCount = 0
       
        while (startingPoint.first < slopSize.first - 1) {
            val nextPoint = moveFrom(startingPoint, movement)
            if (treeCoordinates.contains(nextPoint)) treeCount++

            startingPoint = nextPoint
        }
        return treeCount
    }

    fun retrieveSlopSize(input: List<CharArray>) = input.size to input.first().size

    fun retrieveTreeCoordinates(input: List<CharArray>) = retrieveCoordinatesForType(input, LocalGeology.TREE.code)
    fun retrieveOpenSquareCoordinates(input: List<CharArray>) =
        retrieveCoordinatesForType(input, LocalGeology.OPEN_SQUARE.code)

    private fun retrieveCoordinatesForType(input: List<CharArray>, elementType: Char): List<Pair<Int, Int>> {
        return input.withIndex().flatMap { (index, row) ->
            row.withIndex().filter { it.value == elementType }.map { index to it.index % row.size }
        }
    }

    fun moveFrom(startingPoint: Pair<Int, Int>, movement: Pair<Int, Int> = ShiftingEnum.RIGHT_THREE_DOWN_ONE_MVT.movement): Pair<Int, Int> {
        val gridMaxLength = slopSize.second
        val verticalMove = startingPoint.first + movement.first
        val horizontalMove = startingPoint.second + movement.second
        return verticalMove to if (horizontalMove > gridMaxLength - 1) horizontalMove % gridMaxLength else horizontalMove
    }


    enum class LocalGeology(val code: Char) {
        OPEN_SQUARE('.'),
        TREE('#')
    }

    enum class ShiftingEnum(val movement: Pair<Int, Int>) {
        RIGHT_ONE_DOWN_ONE_MVT(1 to 1),
        RIGHT_THREE_DOWN_ONE_MVT(1 to 3),
        RIGHT_FIVE_DOWN_ONE_MVT(1 to 5),
        RIGHT_SEVEN_DOWN_ONE_MVT(1 to 7),
        RIGHT_ONE_DOWN_TWO_MVT(2 to 1)
    }
}

