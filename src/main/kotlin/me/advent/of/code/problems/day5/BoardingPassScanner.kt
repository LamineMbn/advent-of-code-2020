package me.advent.of.code.problems.day5

import me.advent.of.code.reader.readFile


fun main() {
    val input = readFile("src/main/resources/inputs/day5/input_AoC.txt")
    val boardingPassScanner = BoardingPassScanner()

    val highestSeat = boardingPassScanner.retrieveHighestSeatId(input)
    val mySeat = boardingPassScanner.retrieveMySeatId(input)

    println("$highestSeat is the highest seat number")
    println("My seat is the $mySeat seat")


}


class BoardingPassScanner {

    companion object {
        private const val ROWS_NUMBER = 128
        private const val COLUMNS_NUMBER = 8
    }

    fun retrieveHighestSeatId(input: List<String>): Int {
        return retrieveAllSeatId(input).last()
    }

    fun retrieveMySeatId(input: List<String>): Int {
        val orderedSeatList = retrieveAllSeatId(input)
        val seatRange = IntRange(orderedSeatList.first(), orderedSeatList.last())
        return seatRange.first { it !in orderedSeatList }
    }

    fun retrieveSeatId(boardingPass: String): Int {
        val rowsRegionDesignation = retrieveRowsRegionsDesignation(boardingPass)
        val columnsRegionDesignation = retrieveColumnsRegionsDesignation(boardingPass)

        val row = retrieveRow(rowsRegionDesignation)

        val column = retrieveColumn(columnsRegionDesignation)

        return (row.first * 8) + column.first
    }

    fun retrieveRowsRegionsDesignation(boardingPass: String): String {
        return boardingPass.take(7)
    }

    fun retrieveColumnsRegionsDesignation(boardingPass: String): String {
        return boardingPass.takeLast(3)
    }

    fun retrieveLowerHalfRange(rows: IntRange): IntRange {
        return rows.lowerHalf()
    }

    fun retrieveUpperHalfRange(rows: IntRange): IntRange {
        return rows.upperHalf()
    }

    private fun retrieveAllSeatId(input: List<String>): List<Int> {
        return input.map { retrieveSeatId(it) }.sorted()
    }

    private fun retrieveRow(rowsRegionDesignation: String): IntRange {
        var rows = 0 until ROWS_NUMBER
        rowsRegionDesignation.forEach { region ->
            rows = SeatDesignationEnum.fromCode(region.toString()).chunkRange(rows)
        }
        return rows
    }

    private fun retrieveColumn(columnsRegionDesignation: String): IntRange {
        var columns = 0 until COLUMNS_NUMBER
        columnsRegionDesignation.forEach { region ->
            columns = SeatDesignationEnum.fromCode(region.toString()).chunkRange(columns)
        }
        return columns
    }


}

enum class SeatDesignationEnum(val code: String) {

    FRONT("F") {
        override fun chunkRange(range: IntRange): IntRange {
            return range.lowerHalf()
        }
    },
    BACK("B") {
        override fun chunkRange(range: IntRange): IntRange {
            return range.upperHalf()
        }
    },
    RIGHT("R") {
        override fun chunkRange(range: IntRange): IntRange {
            return range.upperHalf()
        }
    },
    LEFT("L") {
        override fun chunkRange(range: IntRange): IntRange {
            return range.lowerHalf()
        }
    },
    UNKNOWN("") {
        override fun chunkRange(range: IntRange): IntRange {
            return IntRange.EMPTY
        }
    };

    companion object {
        private val map = values().associateBy(SeatDesignationEnum::code)
        fun fromCode(code: String): SeatDesignationEnum = map[code] ?: UNKNOWN
    }

    abstract fun chunkRange(range: IntRange): IntRange

}

private fun IntRange.lowerHalf(): IntRange {
    val lowerHalf = this.chunked(this.count().div(2)).first()
    return IntRange(lowerHalf.first(), lowerHalf.last())
}

private fun IntRange.upperHalf(): IntRange {
    val upperHalf = this.chunked(this.count().div(2)).last()
    return IntRange(upperHalf.first(), upperHalf.last())
}