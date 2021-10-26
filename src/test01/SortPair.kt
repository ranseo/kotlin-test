package test01

import programmers.P


fun main() {
    val p = listOf(P(6,2), P(3,5), P(3,4))

    val p2 = p.sortedWith(compareBy({it.first},{it.second}))
    println(p2)

    val p3 =  p.sortedWith(compareBy<P>({it.first},{it.second}).reversed())
    println(p3)
}