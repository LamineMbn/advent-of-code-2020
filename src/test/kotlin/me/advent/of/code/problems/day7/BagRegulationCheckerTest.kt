package me.advent.of.code.problems.day7

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import me.advent.of.code.reader.readFile
import org.junit.jupiter.api.Test

internal class BagRegulationCheckerTest {

    private val initialInput = readFile("src/test/resources/inputs/day7/input_AoC.txt")

    private var sut = BagRegulationChecker(initialInput)

    @Test
    internal fun `Should split string correctly`() {
        val regulation = "light red bags contain 1 bright white bag, 2 muted yellow bags."

        val extractedRegulation: BagRegulation = sut.extractRegulationFromText(regulation)

        extractedRegulation.bag.colorCode shouldBe "light red"

        val acceptedContent = extractedRegulation.contentRegulation
        acceptedContent shouldHaveSize 2
        acceptedContent[0].quantity shouldBe 1
        acceptedContent[0].bag shouldBe Bag("bright white", 0)

        acceptedContent[1].quantity shouldBe 2
        acceptedContent[1].bag shouldBe Bag("muted yellow", 0)

    }

    @Test
    internal fun `Should split string correctly when final bag`() {
        val regulation = "faded blue bags contain no other bags."

        val extractedRegulation: BagRegulation = sut.extractRegulationFromText(regulation)

        extractedRegulation.bag.colorCode shouldBe "faded blue"

        val acceptedContent = extractedRegulation.contentRegulation

        acceptedContent shouldHaveSize 0
    }

    @Test
    internal fun `Should create a map of all bags`() {

        val bags: Map<Bag, List<BagContentRegulation>> = sut.createMapFromBags()

        val expectedKeys =
            listOf("light red", "dark orange", "bright white", "muted yellow", "shiny gold", "dark olive", "vibrant plum", "faded blue", "dotted black")

        bags.keys.shouldContainExactly(expectedKeys.map { Bag(it, 0) })

        bags[Bag("light red", 0)].shouldContainExactly(regulation(1, "bright white"), regulation(2, "muted yellow"))
        bags[Bag("dark orange", 0)].shouldContainExactly(regulation(3, "bright white"), regulation(4, "muted yellow"))
        bags[Bag("bright white", 0)].shouldContainExactly(regulation(1, "shiny gold"))
        bags[Bag("muted yellow", 0)].shouldContainExactly(regulation(2, "shiny gold"), regulation(9, "faded blue"))
        bags[Bag("shiny gold", 0)].shouldContainExactly(regulation(1, "dark olive"), regulation(2, "vibrant plum"))
        bags[Bag("dark olive", 0)].shouldContainExactly(regulation(3, "faded blue"), regulation(4, "dotted black"))
        bags[Bag("vibrant plum", 0)].shouldContainExactly(regulation(5, "faded blue"), regulation(6, "dotted black"))
        bags[Bag("faded blue", 0)].shouldNotBeNull().shouldBeEmpty()
        bags[Bag("dotted black", 0)].shouldNotBeNull().shouldBeEmpty()
    }

    @Test
    internal fun `Should return 4 bag that can contain the shinny gold`() {

        val bags: Int = sut.retrieveNumberOfBagsThatCanContainColor("shiny gold")
        bags shouldBe 4

    }


    private fun regulation(quantity: Int, colorCode: String) = BagContentRegulation(quantity, Bag(colorCode, 0))
}