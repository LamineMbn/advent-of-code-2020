package me.advent.of.code.problems.day8

import me.advent.of.code.problems.day8.operations.Jump
import me.advent.of.code.problems.day8.operations.NoOperation
import me.advent.of.code.problems.day8.operations.Operation
import me.advent.of.code.problems.day8.operations.factory.OperationFactoryImpl
import me.advent.of.code.reader.readFile

fun main() {
    val input = readFile("src/main/resources/inputs/day8/input_AoC.txt")

    val infiniteLoopFixer = InfiniteLoopFixer(input)

    val accumulatorValue = infiniteLoopFixer.executeOperations()
    val fixedAccumulatorValue = infiniteLoopFixer.fixInfiniteLoop()

    assert(accumulatorValue == 2080) { "ERROR" }
    println("-------------------------- Part 1 -----------------------------------------")
    println("Infinite loop detected with accumulator value equals $accumulatorValue")
    
    println("-------------------------- Part 2 -----------------------------------------")
    println("The fixed loop gives us an accumulator value equals $fixedAccumulatorValue")

}

class InfiniteLoopFixer(private val input: List<String>) {
    private val operationFactory = OperationFactoryImpl()

    fun fixInfiniteLoop(): Int {
        val operations: List<Operation> = extractOperations()
        val operationSwitches = retrieveJumpAndNoopMapSwitch(operations)
        
        kotlin.run loop@{
            operationSwitches.forEach { switch ->
                val overriddenOperations = operations.mapIndexed { index, operation ->
                    when (index){
                        switch.key -> switch.value
                        else -> operation
                    }
                }
                val executor = OperationExecutor(overriddenOperations)
                executor.run()

                if(Operation.accumulator != 0) {
                    return@loop
                }
            }
        }

        return Operation.accumulator
    }
    
    

    fun executeOperations(): Int {
        val operations: List<Operation> = extractOperations()
        val executor = OperationExecutor(operations)

        return executor.run()
    }

    fun extractOperations(): List<Operation> {
        return input.map { extractOperationFromInstruction(it) }
    }

    fun extractOperationFromInstruction(instruction: String): Operation {
        val operation = instruction.split(" ")
        return operationFactory.createOperation(operation.first(), operation.last().toInt())
    }

    fun retrieveJumpAndNoopMapSwitch(operations: List<Operation>): Map<Int, Operation> {
        val jumpOperationIndices = operations.filterIsInstance<Jump>().map{operations.indexOf(it) to NoOperation(it.argument) }.toMap()
        val noOperationIndices = operations.filterIsInstance<NoOperation>().map{operations.indexOf(it) to Jump(it.argument) }.toMap()
        return jumpOperationIndices + noOperationIndices
    }

}

class OperationExecutor(val operations: List<Operation>) {

    fun run(): Int {
        reinitializeAccumulatorData()
        var accumulator = 0
        try {
            var i = 0
            while (i in operations.indices && i !in Operation.executedInstructionsIndex) {
                i = operations[i].execute(i)
            }
        } catch (ex: RuntimeException) {
            println("Infinite loop detected, storing accumulator value...")
            accumulator = Operation.accumulator
            reinitializeAccumulatorData()
            
            
        }
        return accumulator
    }

    private fun reinitializeAccumulatorData() {
        println("Initializing accumulator...")
        Operation.accumulator = 0
        Operation.executedInstructionsIndex = mutableListOf()
    }
}