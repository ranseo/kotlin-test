package programmers

import java.util.*
import java.util.function.Function
import kotlin.collections.HashMap

var combKey : String = ""

fun Combination(answer: MutableList<String>, comb:List<List<String>>, start:Int, target:Int, idx:List<Int>){
    if(0==target) {
        answer.add(combKey)
    }

    for(i in start until comb.size) {
        for(j in 0..idx[i]-1) {
            combKey += comb[i][j]
            Combination(answer, comb, i+1, target-1, idx)
            combKey = combKey.removeSuffix(comb[i][j])
        }
    }
}

fun main() {
    var answer = mutableListOf<Int>()
    var dataMap: HashMap<String, MutableList<Int>> = hashMapOf()

    val info: Array<String> = arrayOf(
        "java backend junior pizza 150",
        "python frontend senior chicken 210",
        "python frontend senior chicken 150",
        "cpp backend senior pizza 260",
        "java backend junior chicken 80",
        "python backend senior chicken 50"
    )
    val query: Array<String> = arrayOf(
        "java and backend and junior and pizza 100",
        "python and frontend and senior and chicken 200",
        "cpp and - and senior and pizza 250",
        "- and backend and senior and - 150",
        "- and - and - and chicken 100",
        "- and - and - and - 150"
    )

    //1-1.info를 문자열부분과 숫자부분으로 나눠주자. 문자열 부분은 dataMap의 key가 되고, 숫자부분은 value가 된다.
    for (i in info) {
        val split = i.split(" ")

        val key: String = split[0] + split[1] + split[2] + split[3]
        val value: Int = split[4].toInt()
        dataMap.getOrPut(key) { mutableListOf() }.add(value)
    }

    dataMap.map { (_, value) -> value.sort()}

    for (i in query) {
        //query각 string을 list로 만들고, value값 따로 만든 뒤 해당 list에서 맨마지막값(value)은 삭제하는 과정
        var totalScore = 0
        var split = i.replace(" and", "").split(" ")
        val value: Int = split[4].toInt()
        split = split.dropLast(1)

        //쿼리에 - 있을 경우, 해당 조건의 모든 요소들이 포함되기 때문에 조합 알고리즘을 이용하여
        //가능한 조합으로 쿼리를 재구성하여 쿼리를 다시 만든다.
        val indexList = mutableListOf<Int>()
        resetList()

        for((index,v) in split.withIndex()) {
            if(v == "-") {
                arrList[index].addAll(ArrList[index])
                indexList.add(arrList[index].size)
            } else {
                arrList[index].add(v)
                indexList.add(1)
            }
        }

        val combs = mutableListOf<String>()
        Combination(combs, arrList, 0, arrList.size, indexList)

        //재구성된 쿼리를 hashMap의 key로 사용해 해당 value가 있는지 확인
        //해당 value(=mutableList<Int>)가 있다면 이 리스트를 이용해 lower bound 알고리즘을 사용
        //쿼리에서 찾으려는 value보다 크거나 같은 원소의 index를 찾아 반환하면, 이를 리스트의 사이즈에서 차감
        //이는 곧 몇명의 인원 해당 쿼리의 점수 이상을 받았는지 알 수 있다.
        combs.forEach { comb ->
            val scoreList = dataMap?.get(comb) ?: return@forEach
            val score = scoreList.size - findLowerBound(scoreList, 0, scoreList.size, value)
            totalScore += score
        }

        answer.add(totalScore)
    }
    println(answer)
}

typealias Index = Int
fun findLowerBound(list: List<Int>, start:Index, end:Index, target: Int) : Index {
    var s = start
    var e = end

    while(s<e) {
        var mid = (s+e) shr 1

        if(mid==e) return e

        if(list.get(mid) < target) s = mid+1
        else e = mid
    }
    return s
}

val tools = mutableListOf<String>()
val jobGroup= mutableListOf<String>()
val career = mutableListOf<String>()
val soulFood = mutableListOf<String>()

var arrList = mutableListOf(tools, jobGroup, career, soulFood)

fun resetList() {
    tools.clear()
    jobGroup.clear()
    career.clear()
    soulFood.clear()

    arrList = mutableListOf(tools, jobGroup, career, soulFood)
}

val Tools = arrayOf("java", "python", "cpp")
val JobGroup = arrayOf("backend", "frontend")
val Career = arrayOf("junior", "senior")
val SoulFood = arrayOf("pizza", "chicken")

val ArrList = arrayOf(Tools, JobGroup, Career, SoulFood)

