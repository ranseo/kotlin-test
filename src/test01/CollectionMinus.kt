package test01

fun main() {
    val list1 = setOf(1,2,3)
    val list1_1 = setOf(1,3,2,4)


    val list2 = listOf(2,3)


    println(list1+4)

    val list3 = listOf(5,6)

    println(list1+list3)

    println(list1_1.sum())

    val listEmp = emptyList<Int>()

    println(listEmp.sum())

    println(list1.union(list1_1))

    val list4  = listOf(4)
    val list5 = listOf(2,4)
    val list6 = listOf(6,4,2)

    println((list4.union(list5)).union(list6))


}