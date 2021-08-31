package programmers

import java.util.*
import java.util.function.Function
import kotlin.collections.HashMap

var combKey : String = ""


fun Combination(answer: MutableList<String>, comb:List<List<String>>, start:Int, target:Int, idx:List<Int>){
    if(0==target) {
        answer.add(combKey)
    }

    //comb의 사이즈는 4개
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

    //1.info를 문자열부분과 숫자부분으로 나눠주자. 문자열 부분은 dataMap의 key가 되고, 숫자부분은 value가 된다.
    for (i in info) {
        val split = i.split(" ")

        val key: String = split[0] + split[1] + split[2] + split[3]
        val value: Int = split[4].toInt()
        dataMap.getOrPut(key) { mutableListOf() }.add(value)
    }

    dataMap.map { (key, value) -> value.sort()}
//
//    println()
    //2.query도 문자열부분과 숫자부분으로 나눠주자. 문자열 부분은 dataMap의 key가 되고, 숫자부분은 value가 된다.
    for (i in query) {
//        println()
//        println("query = $i")
//        println()
        var totalTester = 0
        var split = i.replace(" and", "").split(" ")
        val value: Int = split[4].toInt()
//        println("value = $value")
        split = split.dropLast(1)
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

        //println(answer)
        combs.forEach { comb ->
//            print("comb = $comb ")
            val satisfiedList = dataMap?.get(comb) ?: return@forEach
//            println()
//            println("satisfiedList = $satisfiedList")


            var start = 0
            var end = satisfiedList.size

            while(start < end) {
                var mid = (start+end)/2

                if(satisfiedList.get(mid) < value) start = mid+1
                else end = mid
            }
            val searchPoint = satisfiedList.size - start
//            println("searchPoint = $searchPoint")

            totalTester += searchPoint
//            println("totalTester = $totalTester")
        }

        answer.add(totalTester)
    }
    println()
    println(answer)
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

