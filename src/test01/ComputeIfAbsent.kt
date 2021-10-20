package test01

fun main() {
    val map : MutableMap<Int, Int> = mutableMapOf()

    val value = map[3]
    println(value)

    map.computeIfAbsent(4){ k -> getValue(k) }
}

fun getValue(k:Int) : Int = 5