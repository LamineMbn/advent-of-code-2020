package me.advent.of.code.problems.day7

import me.advent.of.code.reader.readFile


fun main() {
    val input = readFile("src/main/resources/inputs/day7/input_AoC.txt")

    val bagRegulationChecker = BagRegulationChecker(input)

    val numberOfBagsContainingShinyGold = bagRegulationChecker.retrieveNumberOfBagsThatCanContainColor("shiny gold")
    val numberOfBagsInsideShinyGold = bagRegulationChecker.retrieveNumberOfBagsInsideOne("shiny gold")

    assert(numberOfBagsContainingShinyGold == 229)
    println("There's $numberOfBagsContainingShinyGold bags that can contain a shiny gold one")

    assert(numberOfBagsInsideShinyGold == 6683)
    println("There's $numberOfBagsInsideShinyGold bags that inside a shiny gold one")
}

class BagRegulationChecker(private val initialInput: List<String>) {

    companion object {
        const val FINAL_BAG = "no other"
        const val FINAL_BAG_STOP = "STOP"
    }

    var bagsRules: Map<Bag, List<BagContentRegulation>>

    init {
        bagsRules = createMapFromBags()
    }

    fun retrieveNumberOfBagsThatCanContainColor(colorCode: String): Int {
        val topLevelBags: List<Bag> = retrieveTopLevelBags()

        val tree = retrieveTree(topLevelBags)

        return countIndirectBags(tree, colorCode)
    }

    fun retrieveNumberOfBagsInsideOne(colorCode: String): Int {
        val desiredBag: Bag = retrieveTopLevelBags().first { it.colorCode == colorCode }

        val tree = createTree(TreeNode(desiredBag), bagsRules[desiredBag] ?: emptyList())

        println(tree)

        return tree.children.sumBy { it.value.quantity }
    }

    fun createMapFromBags(): Map<Bag, List<BagContentRegulation>> {
        return initialInput.map { extractRegulationFromText(it) }.map { it.bag to it.contentRegulation }
                .toMap()
    }

    fun extractRegulationFromText(regulation: String): BagRegulation {
        val bag = Bag(extractBagTypeFromRegulation(regulation))
        val contentRegulation: List<BagContentRegulation> = extractBagContentFromRegulation(regulation)

        return BagRegulation(bag, contentRegulation)
    }

    private fun countIndirectBags(tree: List<TreeNode<Bag>>, colorCode: String) =
        tree.filter { colorCode in it.children.map { child -> child.value.colorCode } }.count()

    private fun retrieveTree(topLevelBags: List<Bag>): List<TreeNode<Bag>> {
        return topLevelBags.map {
            val contentRegulation = bagsRules[it] ?: emptyList()
            createTree(TreeNode(it), contentRegulation)
        }
    }

    private fun createTree(root: TreeNode<Bag>, regulation: List<BagContentRegulation>, times: Int = 1): TreeNode<Bag> {

        regulation.forEach {
            val quantity = it.quantity * times
            val child = TreeNode(Bag(it.bag.colorCode, quantity))
            root.addChild(child)
            val contentRegulation = bagsRules[it.bag] ?: emptyList()
            if (contentRegulation.isNotEmpty()) {
                createTree(root, contentRegulation, quantity)
            }
        }

        return root
    }

    private fun retrieveTopLevelBags(): List<Bag> = bagsRules.map { it.key }

    private fun extractBagContentFromRegulation(regulation: String): List<BagContentRegulation> {
        return regulation.substringAfter("contain").split(",").map { extractBagTypeFromRegulation(it) }
                .map { convertToBagContentRegulation(it) }.filterNot { it.bag.colorCode == FINAL_BAG_STOP }
    }

    private fun extractBagTypeFromRegulation(regulation: String): String {
        return regulation.substringBefore("bag").trim()
    }

    private fun convertToBagContentRegulation(content: String): BagContentRegulation {
        val quantity = content.takeWhile { it.isDigit() }.takeIf { it.isNotEmpty() }?.toInt() ?: 0
        val acceptedBagType =
            content.takeLastWhile { it.isLetter() || it == ' ' }.takeIf { it != FINAL_BAG }?.trim() ?: FINAL_BAG_STOP

        return BagContentRegulation(quantity, Bag(acceptedBagType, quantity))
    }


}

data class BagRegulation(val bag: Bag, val contentRegulation: List<BagContentRegulation>)
data class BagContentRegulation(val quantity: Int, val bag: Bag) {
    override fun toString(): String {
        return "$quantity $bag"
    }
}

data class Bag(val colorCode: String, val quantity: Int = 0) {

    override fun equals(other: Any?): Boolean {
        return other is Bag && this.colorCode == other.colorCode
    }

    override fun toString(): String {
        return "$quantity $colorCode"
    }

    override fun hashCode(): Int {
        return colorCode.hashCode()
    }
}

class TreeNode<Bag>(val value: Bag) {
    var parent: TreeNode<Bag>? = null

    var children: MutableList<TreeNode<Bag>> = mutableListOf()

    fun addChild(node: TreeNode<Bag>) {
        children.add(node)
        node.parent = this
    }

    override fun toString(): String {
        var s = "$value"
        if (children.isNotEmpty()) {
            s += " {" + children.map { it.toString() } + " }"
        }
        return s
    }
}