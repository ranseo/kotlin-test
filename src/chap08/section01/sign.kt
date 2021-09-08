package chap08.section01

import kotlin.math.*

fun main(args: Array<String>) {
    val arr = Array(11) { y ->
        Array(40) { x ->
            val sinx = (sin(x.toDouble() / (2 * PI)) * 5).toInt()

            when {
                y == 5 - sinx -> '*'
                y == 5 && x == 0 -> '+'
                y == 5 -> '-'
                x == 0 -> '|'
                else -> ' '
            }
        }
    }

    println(arr.joinToString("\n") { it.joinToString("") })
}