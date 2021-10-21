package test01

fun main() {
    val map: MutableMap<Int, Int> = mutableMapOf()
    val map2: MutableMap<Int, MutableList<Int>> = mutableMapOf()

    map2.put(3, mutableListOf(7))
    map2[3]?.add(5) ?: map2.computeIfAbsent(3) { mutableListOf<Int>().also { it.add(5)}}


    println(map2[3]?.first())
    println(map2[3]?.get(1))

    println(map2[3])

    var value = map[3]
    println(value)

    value = map.computeIfAbsent(4) { k -> getValue(k) }

    println(value)
    println(map[4])
}

fun getValue(k: Int): Int = 5