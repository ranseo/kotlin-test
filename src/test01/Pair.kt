package test01

import programmers.P
import programmers.plus


fun main() {
    var a = P(1,2)
    a.plus(P(4,3))

    println("${a.first} , ${a.second}")

    a = a.plus(P(4,3))
    println("${a.first} , ${a.second}")
}