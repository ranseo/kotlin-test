package test01

import programmers.combKey
typealias hashp = Pair<String,Int>
fun main() {

    //A n B = B TRUE
    //A n B != B FALSE
    val list = mutableListOf(listOf(1), listOf(2,3))

    val list2 = listOf(2,4)


    val abb = list2.toMutableList()
    abb.removeAt(0)
    println(abb)

    list.forEach{
        if(list2.containsAll(it)) println(it)
    }

    val hash = hashMapOf<String,Int>("a" to 2,
    "b" to 5)


    val ab = arrayListOf<Int>()




    val a = hash.map{hashp(it.key, it.value)}
    println(a)


}