package me.advent.of.code.reader

import java.io.File

fun readFile(fileName: String) : List<String> {
    val bufferedReader = File(fileName).bufferedReader()
    val lineList = mutableListOf<String>()

    bufferedReader.useLines { lines -> lines.forEach { lineList.add(it) } }

    return lineList.toList()
}

fun separateInputData(input: List<String>) = input.joinToString(" ").split("  ")

fun readFileLineByLine(fileName: String) : List<String> {
    val bufferedReader = File(fileName).bufferedReader()
    val lineList = mutableListOf<String>()

    bufferedReader.forEachLine { lineList.add(it) }

    return lineList.toList()
}