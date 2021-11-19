package test01

class FlatMap {
    val numbers = 0..10

    fun flatMap() {
        println(numbers.flatMap { number -> 1..number })


    }
}

fun main() {
    val flatMap = FlatMap()

    flatMap.flatMap()

    val list = listOf(listOf(1), listOf(2,3), listOf(4), listOf(4,5,6), listOf(7,8))
    println(list)

    val list2 = list.flatMap { it }
    println(list2)



}