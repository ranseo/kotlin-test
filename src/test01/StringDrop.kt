package test01

fun main() {
    val str = "abc"

    println(str.drop(1))

    str.substring(0)

    val braket = "))(())(("
    val braket2 = "))())((("

    println(braket.reversed())
    println(braket2.reversed())

    val tmp = braket
    println(tmp)
}