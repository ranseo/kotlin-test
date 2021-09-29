package test01

fun main() {
    val number = listOf(1,2,3,4,5)
    println(number.fold(0){ total, i -> total+i})

    println(number.fold(0) {acc,i -> if(acc > i) i else acc})

    var str = "(())"


}