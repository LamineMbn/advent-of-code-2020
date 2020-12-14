package me.advent.of.code.problems.day8.operations

class Accumulation(argument: Int = 0) : Operation(argument) {
    override val increment: Int
        get() = 1

    override fun incrementIndex(currentIndex: Int): Int {
        return currentIndex + increment
    }


    override fun processOperation(nextIndex: Int): Int {
        accumulator += argument
        return nextIndex
    }

}
