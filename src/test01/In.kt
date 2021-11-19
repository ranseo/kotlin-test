package test01


fun main() {
    val list1 = listOf(1,2,3)
    val list2 = listOf(2,3,4)

    println(list1.all{ it in list2})
    //=println(list1.all{ list2.contains(it)})

    println(list1.any{ it in list2})
    //=println(list1.any{ list2.contains(it)})

    println(list1.none{ it in list2})
    //=println(list1.none{ list2.contains(it)})


}