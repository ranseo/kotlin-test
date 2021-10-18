package test01

fun main() {
    val arr = mutableSetOf<String>("ad","cbb","dd","rf","asss","aoa","ococ")

    println(arr.groupBy { it.startsWith("a") })

    val arr2 = (arr.groupBy { it.startsWith("a") })[true]

    println(arr2)

}