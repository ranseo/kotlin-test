package test01

fun main() {
    val number = listOf(1,2,3,4,5)
    println(number.fold(listOf(0)){ total, i -> total+i})
}