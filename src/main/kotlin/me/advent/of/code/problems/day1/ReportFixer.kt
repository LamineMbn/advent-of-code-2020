package me.advent.of.code.problems.day1

import me.advent.of.code.reader.readFile

fun main(){
    val expenseReport = readFile("src/main/resources/inputs/day1/input_AoC.txt").map { it.toInt() }
    
    val expenseReportFixer = ReportFixer()
    
    val productForTwoEntries = expenseReportFixer.fixReport(expenseReport)
    val productForThreeEntries = expenseReportFixer.fixReport(expenseReport, 3)
    
    println("The correct answer is $productForTwoEntries for 2 entries")
    println("The correct answer is $productForThreeEntries for 3 entries")
}

class ReportFixer {
    fun fixReport(inputExample: List<Int>, numberOfEntries: Int = 2): Int {
        
        var product = 0
        
        val cartesianProduct = cartesianProduct(inputExample, numberOfEntries)

        cartesianProduct.forEach { 
            if(sumEqualsCursedYear(it.sum())){
                product = it.reduce { a, b -> a * b }
            }
        }
        
        return product
    }


    private fun sumEqualsCursedYear(sum: Int) =  sum == 2020

    private fun cartesianProduct(list: List<Int>, times: Int = 2): List<List<Int>> {
        val multiList = mutableListOf<List<Int>>()
        
        repeat(times){
            multiList.add(list)
        }
        
        return multiList.fold(listOf(listOf())) { acc, initialList -> acc.flatMap { accumulatedList -> initialList.map { element -> accumulatedList + element } } }
    }


}