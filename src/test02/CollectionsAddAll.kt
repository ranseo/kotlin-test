package test02

fun main() {
    val list = mutableListOf<List<Int>>()

    val list2 = listOf<Int>(2,3,4)
    val list3 = listOf<Int>(4,5,6)

    list.addAll(listOf(list2))

    val list4 = list2+2

    println(list4)
}