package me.advent.of.code.problems.day8

import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equality.shouldBeEqualToUsingFields
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import me.advent.of.code.problems.day8.operations.Accumulation
import me.advent.of.code.problems.day8.operations.Jump
import me.advent.of.code.problems.day8.operations.NoOperation
import me.advent.of.code.problems.day8.operations.Operation
import me.advent.of.code.reader.readFile
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class InfiniteLoopFixerTest{

    companion object {
        @JvmStatic
        fun instructions() = listOf(
            Arguments.of("nop +0", NoOperation()),
            Arguments.of("acc +1", Accumulation(1)),
            Arguments.of("jmp -4", Jump(-4))
        )
    }

    private val initialInput = readFile("src/test/resources/inputs/day8/input_AoC.txt")

    private var sut = InfiniteLoopFixer(initialInput)


    @ParameterizedTest(name = "Operation {0} should be instance of {1}")
    @MethodSource(value = ["instructions"])
    internal fun `Should create different instances for each instruction type`(instruction : String, expectedOperation: Operation) {
        val operation = sut.extractOperationFromInstruction(instruction)

        operation::class shouldBe expectedOperation::class
        operation.shouldBeEqualToUsingFields(expectedOperation, Operation::argument)
    }

    @Test
    internal fun `Should create list of operations from input`() {
        val operations : List<Operation> = sut.extractOperations()
        
        operations shouldHaveSize 9
        
        operations[5].shouldBeInstanceOf<Accumulation>()
        operations[5].argument shouldBe -99
        operations[5].increment shouldBe 1

        operations[7].shouldBeInstanceOf<Jump>()
        operations[7].argument shouldBe -4
        operations[7].increment shouldBe -4
        
    }

    @Test
    internal fun `Should return 5 as the value of the accumulator`() {
        val accumulator = sut.executeOperations()
        
        accumulator shouldBe 5
    }

    @Test
    internal fun `Should return 8 as the value of the accumulator when fixed`() {
        val accumulator = sut.fixInfiniteLoop()

        accumulator shouldBe 8
    }

    @Test
    internal fun `Should retrieve index of jump and nop operation`() {
        val operations : List<Operation> = sut.extractOperations()
        val accumulator = sut.retrieveJumpAndNoopMapSwitch(operations)

        accumulator.map { it.key }.shouldContainAll(0,2,4,7)
        
        val noOperationTransformedToJump = accumulator.getValue(0)
        val jumpOperationTransformedToNoOp = accumulator.getValue(7)
        
        noOperationTransformedToJump::class shouldBe Jump::class
        noOperationTransformedToJump.argument shouldBe 0
        
        jumpOperationTransformedToNoOp::class shouldBe NoOperation::class
        jumpOperationTransformedToNoOp.argument shouldBe -4
    }
}