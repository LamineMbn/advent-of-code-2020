package me.advent.of.code.problems.day8.operations

abstract class Operation(val argument : Int = 0) {
    
    abstract val increment : Int
    
    companion object {
        var accumulator = 0
        var executedInstructionsIndex = mutableListOf<Int>()
    }
    
    internal fun execute(currentIndex: Int): Int {
        
        val nextIndex = incrementIndex(currentIndex)
        
        if (nextIndex in executedInstructionsIndex) throw RuntimeException("$accumulator")
        
        executedInstructionsIndex.add(currentIndex)
        
        return processOperation(nextIndex)
    }

    protected abstract fun incrementIndex(currentIndex: Int): Int
    protected abstract fun processOperation(nextIndex: Int): Int
}