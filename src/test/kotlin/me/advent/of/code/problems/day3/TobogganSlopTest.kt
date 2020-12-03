package me.advent.of.code.problems.day3

import io.kotest.matchers.shouldBe
import me.advent.of.code.reader.readFileLineByLine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class TobogganSlopTest {

    companion object {
        @JvmStatic
        fun movements() = listOf(
            Arguments.of(1 to 1, 2),
            Arguments.of(1 to 3, 7),
            Arguments.of(1 to 5, 3),
            Arguments.of(1 to 7, 4),
            Arguments.of(2 to 1, 2)
        )
    }

    private lateinit var sut: TobogganSlop

    private val input = readFileLineByLine("src/test/resources/inputs/day3/input_AoC.txt").map { it.toCharArray() }

    @BeforeEach
    internal fun setUp() {
        sut = TobogganSlop(input)
    }

    @Test
    internal fun `Should retrieve tree coordinates`() {
        val treeCoordinates: List<Pair<Int, Int>> = sut.retrieveTreeCoordinates(input)
        treeCoordinates[0] shouldBe (0 to 2)
        treeCoordinates[12] shouldBe (4 to 1)
    }

    @Test
    internal fun `Should retrieve open square coordinates`() {
        val openSquareCoordinates: List<Pair<Int, Int>> = sut.retrieveOpenSquareCoordinates(input)
        openSquareCoordinates[0] shouldBe (0 to 0)
        openSquareCoordinates[12] shouldBe (1 to 5)
    }

    @Test
    internal fun `Should move right 3 down 1`() {
        val startPoint = (0 to 0)

        val nextPoint = sut.moveFrom(startPoint)

        nextPoint shouldBe (1 to 3)
    }

    @Test
    internal fun `Should move right 3 down 1 at edge`() {
        val startPoint = (3 to 9)

        val nextPoint = sut.moveFrom(startPoint)

        nextPoint shouldBe (4 to 1)
    }

    @Test
    internal fun `Should slop size be 22, 11`() {
        val treeEncountered = sut.retrieveSlopSize(input)

        treeEncountered shouldBe (11 to 11)
    }

    @ParameterizedTest(name = "With {0} movement we should encounter {1} trees")
    @MethodSource("movements")
    internal fun `Should calculate the correct number of trees encountered`(mvt: Pair<Int, Int>, trees: Int) {
        val treeEncountered = sut.calculateNumberOfTreesInTheWay(mvt)

        treeEncountered shouldBe trees
    }

    @Test
    internal fun `The product of all encountered trees should be equal to 336`() {
        val allTreeEncountered = sut.productOfAllTreeEncountered()
        
        allTreeEncountered shouldBe 336
    }
}