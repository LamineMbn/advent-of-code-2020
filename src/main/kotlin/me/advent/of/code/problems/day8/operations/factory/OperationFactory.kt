package me.advent.of.code.problems.day8.operations.factory

import me.advent.of.code.problems.day8.operations.Accumulation
import me.advent.of.code.problems.day8.operations.Jump
import me.advent.of.code.problems.day8.operations.NoOperation
import me.advent.of.code.problems.day8.operations.Operation

interface OperationFactory {
    fun createOperation(type: String, argument: Int = 0) : Operation
}

class OperationFactoryImpl : OperationFactory {
    override fun createOperation(type: String, argument: Int) : Operation {
        return when(type){
            "nop" -> NoOperation(argument)
            "acc" -> Accumulation(argument)
            "jmp" -> Jump(argument)
            else -> throw IllegalArgumentException("Unknown $type")
        }
    }


}