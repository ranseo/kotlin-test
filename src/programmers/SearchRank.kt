package programmers

import java.util.function.Function

var combKey : String = ""


fun Combination(answer: MutableList<String>, comb:ArrayList<ArrayList<String>>, start:Int, target:Int, idx:ArrayList<Int>){
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

    var answer: List<Int> = listOf()

    //1.info를 문자열부분과 숫자부분으로 나눠주자. 문자열 부분은 dataMap의 key가 되고, 숫자부분은 value가 된다.
    for (i in info) {
        val split = i.split(" ")

        val key: String = split[0] + split[1] + split[2] + split[3]
        val value: Int = split[4].toInt()
        dataMap.getOrPut(key) { mutableListOf() }.add(value)
    }

    println(dataMap)

    dataMap.map { (key, value) -> value.sortDescending() }

    println(dataMap)


    //2.query도 문자열부분과 숫자부분으로 나눠주자. 문자열 부분은 dataMap의 key가 되고, 숫자부분은 value가 된다.
    for (i in query) {
        val split = i.replace(" and", "").split(" ")
        val indexList = mutableListOf<Int>()
        for((index,value) in split.withIndex()) {
            if(value == "-") {
                indexList.add(arrList[index].size)
            } else indexList.add(1)
        }

    }
}

val Tools = arrayOf("java", "python", "cpp")
val JobGroup = arrayOf("backend", "frontend")
val Career = arrayOf("junior", "senior")
val SoulFood = arrayOf("pizza", "chicken")

val arrList = arrayOf(Tools, JobGroup, Career, SoulFood)

