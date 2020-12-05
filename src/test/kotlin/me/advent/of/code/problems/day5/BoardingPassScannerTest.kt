package me.advent.of.code.problems.day5

import io.kotest.matchers.shouldBe
import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class BoardingPassScannerTest{

    companion object {
        @JvmStatic
        fun lowerRanges() = listOf(
            Arguments.of(0..127, 0..63),
            Arguments.of(0..63, 0..31),
            Arguments.of(32..63, 32..47),
            Arguments.of(44..45, 44..44)
        )

        @JvmStatic
        fun upperRanges() = listOf(
            Arguments.of(0..127, 64..127),
            Arguments.of(0..63, 32..63),
            Arguments.of(32..63, 48..63),
            Arguments.of(44..45, 45..45)
        )

        @JvmStatic
        fun seatIds() = listOf(
            Arguments.of("BFFFBBFRRR", 567),
            Arguments.of("FFFBBBFRRR", 119),
            Arguments.of("BBFFBBFRLL", 820)
        )

        @JvmStatic
        fun rowsRegionDesignation() = listOf(
            Arguments.of("BFFFBBFRRR", "BFFFBBF"),
            Arguments.of("FFFBBBFRRR", "FFFBBBF"),
            Arguments.of("BBFFBBFRLL", "BBFFBBF")
        )
        
        @JvmStatic
        fun columnsRegionDesignation() = listOf(
            Arguments.of("BFFFBBFRRR", "RRR"),
            Arguments.of("FFFBBBFRRR", "RRR"),
            Arguments.of("BBFFBBFRLL", "RLL")
        )
    }
    
    private val sut = BoardingPassScanner()

    @ParameterizedTest(name = "Lower half of {0} should be {1}")
    @MethodSource(value = ["lowerRanges"])
    internal fun `Should return lower half`(range: IntRange, expectedRange: IntRange) {
       val rangeKept = sut.retrieveLowerHalfRange(range)
        
        rangeKept shouldBe expectedRange
    }

    @ParameterizedTest(name = "Upper half of {0} should be {1}")
    @MethodSource(value = ["upperRanges"])
    internal fun `Should return upper half`(range: IntRange, expectedRange: IntRange) {
        val rangeKept = sut.retrieveUpperHalfRange(range)

        rangeKept shouldBe expectedRange
    }

    @ParameterizedTest(name = "The borading pass {0} should have the seat {1}")
    @MethodSource(value = ["seatIds"])
    @Ignore
    internal fun `Should retrieve seat id`(boardingPass: String, expectedSeat: Int) {
        val seatId = sut.retrieveSeatId(boardingPass)

        seatId shouldBe expectedSeat
    }

    @ParameterizedTest(name = "The boarding pass {0} should have {1} as row region designation")
    @MethodSource(value = ["rowsRegionDesignation"])
    internal fun `Should retrieve row region designation`(boardingPass: String, expectedRegion: String) {
        val region = sut.retrieveRowsRegionsDesignation(boardingPass)

        region shouldBe expectedRegion
    }

    @ParameterizedTest(name = "The boarding pass {0} should have {1} as column region designation")
    @MethodSource(value = ["columnsRegionDesignation"])
    internal fun `Should retrieve column region designation`(boardingPass: String, expectedRegion: String) {
        val region = sut.retrieveColumnsRegionsDesignation(boardingPass)

        region shouldBe expectedRegion
    }

    @Test
    internal fun `Highest seat id should be 820`() {
        val input = listOf("BFFFBBFRRR","FFFBBBFRRR", "BBFFBBFRLL")
        
        val highestSeat = sut.retrieveHighestSeatId(input)
        
        highestSeat shouldBe 820
    }
}