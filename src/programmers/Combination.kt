package programmers

fun <T> Combination( comb: MutableList<List<T>>, contents: List<T>, check: Array<Boolean>, start: Int, target: Int) {
    if(0==target) {
        comb.addAll(listOf(contents.filterIndexed{index ,v -> check[index]}))
    }

    for(i in start until contents.size) {
        check[i] = true
        Combination(comb, contents, check, i+1, target-1)
        check[i] = false
    }
}


fun main() {
    val orders = arrayOf("XYZ", "XWY", "WXA")
    val course = intArrayOf(2,3,4)
    val map = mutableMapOf<String, Int>()
    var answer : MutableList<String> = mutableListOf()

    for(o in orders) {
        val order = o.toSortedSet().toList()
        course.forEach { target ->
            val comb = mutableListOf<List<Char>>()
            Combination(comb, order, Array(order.size) { false }, 0, target)

            comb.forEach {
                val setMenu = it.joinToString("")
                if (!map.containsKey(setMenu)) map.put(setMenu, 1)
                else map[setMenu] = map[setMenu]!! + 1
            }
        }
    }

    //println(map)

    for(i in course) {
        //val setMenu = (map.filter{it.key.length == i}.maxByOrNull { it.value })?.key ?: ""
        val setMap = map.filter{it.key.length == i && it.value > 1}
        val max = setMap.maxByOrNull { it.value }?.value ?: continue
        answer.addAll((setMap.filter{ it.value == max }.keys.toList()))
    }

    answer.sort()
    println(answer)
}