package test01

class SolutionJoHyunChul {
    fun solution(relation: Array<Array<String>>): Int {

        fun <T> List<T>.powerSet(): List<List<T>> {
            var r = listOf(emptyList<T>())
            tailrec fun recursive(ll: List<T>) {
                if (ll.isEmpty())
                    return
                r = r.flatMap { listOf(it + ll.first(), it) }
                return recursive(ll.drop(1))
            }

            recursive(this)
            return r.toList()
        }

        val columnSize = relation.first().size
        val tupleSize = relation.size
        val powerSets = (1..columnSize).toList().powerSet().dropLast(1).sortedBy { it.size }

        val candidateKey = mutableListOf<List<Int>>()

        fun <T> List<T>.isPartOf(l: List<T>): Boolean = all { it in l }

        return powerSets.filter { set ->
            if (candidateKey.any { it.isPartOf(set) })
                false
            else {
                val keys = relation.map { tuple -> set.fold("") { acc, i -> acc + tuple[i - 1] } }

                if (keys.distinct().size == tupleSize) {
                    candidateKey.add(set)
                    true
                } else
                    false
            }
        }.size
    }
}

//조합을 이걸로 만든건데 뭐냐고 진짜;;
fun <T> List<T>.powerSet(): List<List<T>> {
    var r = listOf(emptyList<T>())
    tailrec fun recursive(ll: List<T>) {
        //println("before : $r")
        if (ll.isEmpty())
            return
        r = r.flatMap { listOf(it + ll.first(), it) }
        //println("after : $r")
        return recursive(ll.drop(1))
    }

    recursive(this)
    return r.toList()
}

fun main() {

    val powerSets = (1..5).toList().powerSet().dropLast(1).sortedBy { it.size }

    println(powerSets)

    //println(listOf(listOf(1,2)+2, 3))
}