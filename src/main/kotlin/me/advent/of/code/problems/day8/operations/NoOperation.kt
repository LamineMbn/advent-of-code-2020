package me.advent.of.code.problems.day8.operations

class NoOperation(argument: Int = 0) : Operation(argument) {
    
    override val increment: Int
        get() = 1

    override fun incrementIndex(currentIndex: Int): Int {
        return currentIndex + increment
    }

    override fun processOperation(nextIndex: Int): Int {
        return nextIndex
    }

}
