package me.advent.of.code.problems.day9

import me.advent.of.code.reader.readFile


fun main() {
    val input = readFile("src/main/resources/inputs/day9/input_AoC.txt")

    val xmasBreaker = XMASBreaker(input.map { it.toInt() })

}

class XMASBreaker(private val input: List<Int>) {

    fun findXMASWeakness(preamble: Int): Int {
        var weakness = 0
        loop@ for (i in preamble + 1 until input.size) {
            val preambleList = retrievePreambleNumbers(input, i, preamble)
            val sums = cartesianSum(preambleList, preamble)
            println(preambleList)
            println(sums)
            if (input[i] !in sums){
                weakness = input[i]
                break@loop
            }

        }
        return weakness
    }

    fun retrievePreambleNumbers(data: List<Int>, currentIndex: Int, preamble: Int): List<Int> {
        return data.filterIndexed { index, _ -> index < currentIndex }.takeLast(preamble)
    }
    
    fun cartesianSum(data: List<Int>, preamble: Int = 5): Set<Int> {
        val sums = mutableSetOf<Int>()
        val lastIndex = preamble - 1
        
        for (i in 0..lastIndex){
            if(i != lastIndex) {
                sums.add(data[i] + data[i+1])
            }
        }
        
        return sums
    }




}