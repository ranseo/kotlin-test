package test01

fun main() {

    //A n B = B TRUE
    //A n B != B FALSE
    val list = mutableListOf(listOf(1), listOf(2,3))

    val list2 = listOf(2,4)

    list.forEach{
        if(list2.containsAll(it)) println(it)
    }

}