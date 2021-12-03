package programmers

class SolutionTuple {

    fun solution(s: String): IntArray {
        val answer = mutableListOf<Int>()
        val tmp = s.replace("},{", " ").replace("{", "").replace("}", "").replace(",  ", "").split(" ")
        val list = tmp.map {
            it.split(",")
        }
            .asSequence()
            .map { v ->
                v.map { e -> e.toInt() }
            }.toList().sortedBy { it.size }

        println(list)

        list.fold(listOf<Int>()) { acc, v ->
            val diff = v.sum() - acc.sum()
            answer.add(diff)

            v
        }

        return answer.toIntArray()
    }
}

class SolutionTuple2 {
    fun solution(s: String): IntArray {
        return s.substring(2 until s.length - 2)
            .split("},{")
            .asSequence()
            .map { it.split(",").map { num -> num.toInt() } }
            .toList()
            .sortedBy { it.size }
            .fold(setOf<Int>()) { acc, list -> acc.union(list) }
            .toIntArray()
    }
}

class SolutionTuple3 {
    fun solution(s: String): IntArray = arrayListOf<Int>().apply {
        s.split("{", "}", ",")
            .groupBy { it }
            .toList()
            .sortedByDescending { it.second.count() }
            .also {
                it.takeLast(it.size - 1)
                    .forEach { add(it.first.toInt()) }
            }
    }.toIntArray()
}


fun main() {
    val solutionTuple = SolutionTuple3()
    val s = "{{2},{1,2},{2,1,3},{2,1,3,4}}"


    val tmp = arrayListOf<Int>().apply {
        s.substring(2 until s.length - 2)
            .split("},{", ",")
            .groupBy { it }
            .toList()
            .sortedByDescending { it.second.count() }
            .forEach { add(it.first.toInt()) }
    }.toIntArray()

    tmp.forEach{ println(it)}


    solutionTuple.solution(s).forEach { println(it) }
}



