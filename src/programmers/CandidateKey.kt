package programmers

class SolutionCandidateKey {

    val attr = mutableListOf<Int>()
    val candidateKey = mutableListOf<List<Int>>()

    fun solution(relation: Array<Array<String>>): Int {
        val attr_size = relation[0].size
        for(i in 0 until attr_size) {
            attr.add(i)
        }

        //조합으르 idxList 뽑는 중
        for (target in 1..attr.size) {
            val idxList = mutableListOf<List<Int>>()
            combination(idxList, mutableListOf(), 0, target)

            findCandiKey(idxList, relation)

            idxList.clear()
        }

        return candidateKey.size
    }

    //조합
    fun combination(ans: MutableList<List<Int>>, comb: MutableList<Int>, start: Int, target: Int) {
        if (target == 0) {
            ans.add(comb.toList())
            return
        }

        for (i in start until attr.size) {
            comb.add(attr[i])
            combination(ans, comb, i + 1, target - 1)
            comb.removeAt(comb.size - 1)
        }
    }

    //후보키 찾아내는 함수
    fun findCandiKey(idxlist:List<List<Int>>, relation: Array<Array<String>>) {

        for(list in idxlist) {
            //유일성 체크
            var isUnique = checkUnique(list, relation)

            if(!isUnique) continue

            //최소성 체크, "후보키" 리스트에 추가.
            if(checkMinimal(list)) candidateKey.add(list)
        }
    }

    //유일성 체크
    fun checkUnique(list:List<Int>, relation: Array<Array<String>>) : Boolean {
        val unique = mutableSetOf<String>()
        var flag = true

        for(tuple in relation) {
            var key = ""
            for(idx in list) {
                key += tuple[idx]+"${idx}"
            }
            if(unique.contains(key)) {
                flag = false
                break
            } else {
                unique.add(key)
            }
        }
        return flag
    }

    //최소성 체크
    fun checkMinimal(list:List<Int>) : Boolean {
        if(list.size == 1) return true

        for(keyList in candidateKey) {
            if (list.containsAll(keyList)) return false
        }
        return true
    }
}

fun main() {
    val candidateKey = SolutionCandidateKey()
    val relation = arrayOf(arrayOf("100","ryan","music","2"), arrayOf("200","apeach","math","2"), arrayOf("300","tube","computer","3"),
        arrayOf("400","con","computer","4"), arrayOf("500","muzi","music","3"),arrayOf("600","apeach","music","2"))
    println(candidateKey.solution(relation))

}



class SolutionDavidCode {

    data class Database(val records: List<Record>) {

        fun getUniqueKeys(): Set<Key> {
            val masterKey = Key(List(records.first().columns.size) { it }.toSet())
            return findAllUniqueKeys(masterKey)
        }

        private fun findAllUniqueKeys(key: Key): Set<Key> {
            return if (!definedUniquelyBy(key)) {
                emptySet()
            } else {
                key.subKeys()
                    .flatMap(::findAllUniqueKeys).toSet()
                    .takeIf { it.isNotEmpty() } ?: setOf(key)
            }
        }

        private fun definedUniquelyBy(key: Key): Boolean {
            return records.groupBy { record ->
                // pick only record's columns defined by key
                record.columns.filterIndexed { index, _ -> index in key.columns }
            }.size == records.size
        }
    }

    data class Record(val columns: List<String>)

    data class Key(val columns: Set<Int>) {

        fun subKeys(): List<Key> = columns.map { Key(columns - it) }
    }

    fun solution(relation: Array<Array<String>>): Int {
        return Database(relation.map {
            Record(it.toList())
        }).getUniqueKeys().size
    }
}

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