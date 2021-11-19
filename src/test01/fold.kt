package test01

fun main() {


    var str = "(())"

    val number = listOf(1,2,3,4,5)

    val relation = arrayOf("a", "b", "c", "d", "e")

    val new = number.fold(""){acc,i -> acc + relation[i-1]}

    println(new)


    println(number.fold(0){ total, i -> total+i})

    println(number.fold(0) {acc,i -> if(acc > i) i else acc})




}