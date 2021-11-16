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


}