package test01

import programmers.P
import java.util.*

fun main() {
    val pq= PriorityQueue<P>(compareBy{it.second})

    pq.addAll(arrayOf(P(1,2), P(3,1), P(4,5)))


    println(pq)
    pq.poll()

    println(pq)
}