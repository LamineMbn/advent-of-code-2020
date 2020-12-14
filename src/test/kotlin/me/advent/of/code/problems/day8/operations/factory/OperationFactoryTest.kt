package me.advent.of.code.problems.day8.operations.factory

import io.kotest.matchers.shouldBe
import me.advent.of.code.problems.day8.operations.Accumulation
import me.advent.of.code.problems.day8.operations.Jump
import me.advent.of.code.problems.day8.operations.NoOperation
import me.advent.of.code.problems.day8.operations.Operation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.reflect.KClass

internal class OperationFactoryTest{

    companion object {
        @JvmStatic
        fun instructions() = listOf(
            Arguments.of("nop", NoOperation::class),
            Arguments.of("acc", Accumulation::class),
            Arguments.of("jmp", Jump::class)
        )
    }

    private var sut = OperationFactoryImpl()


    @ParameterizedTest(name = "Operation {0} should be instance of {1}")
    @MethodSource(value = ["instructions"])
    internal fun `Should create different instances for each instruction type`(type: String, expectedClass : KClass<Operation>) {
        val operation = sut.createOperation(type)
        
        operation::class shouldBe expectedClass
    }
    
}