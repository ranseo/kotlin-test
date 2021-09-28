package programmers

import java.util.*

class Solution {
    var answer = mutableListOf<Array<String>>()
    var flag = false
    fun solution(tickets: Array<Array<String>>): Array<String> {


        //DFS와 HASHMAP을 이용하면 쉬울듯.
        //우선 hashMap에 공항의 이름과 갈 수 있는 공항의 이름이 담긴 배열을 만든다.

        val hashMap = hashMapOf<String, MutableList<String>>()

        //hashMap에 tickets의 원소와 value값을 넣는다.
        tickets.forEach{ ticket ->
            val (dep,arr) = ticket
            if(hashMap.containsKey(dep)) hashMap[dep]!!.add(arr)
            else hashMap.put(dep, mutableListOf(arr))
        }

        hashMap.map{(key,value) -> value.sort()}

        dfs(hashMap, mutableListOf("ICN"), tickets.size, "ICN", 0)


        return answer[0]
    }


    @OptIn(kotlin.ExperimentalStdlibApi::class)
    fun dfs(hashMap: HashMap<String, MutableList<String>>, route: MutableList<String>, target: Int, start: String, size: Int) {
        if(size == target) {
            //println(route)
            answer.addAll(mutableListOf(route.toTypedArray()))
            flag = true
            return
        }

        val index = hashMap[start]?.size ?: -1

        for(i in 0..index) {
            if(hashMap[start]!!.isEmpty()) continue

            val next = hashMap[start]!!.get(0)
            hashMap[start]!!.removeFirst()
            route.add(next)
            //println(route)
            dfs(hashMap, route, target, next, size+1)
            if(flag) return
            route.removeLast()
            hashMap[start]!!.add(next)
            //println("after dfs : ${hashMap[start]!!.get(0)} ")

        }
    }
}

fun main() {
    val ticket = arrayOf(arrayOf("ICN", "COO"), arrayOf("ICN", "BOO"), arrayOf("COO", "ICN"), arrayOf("BOO", "DOO"))
    val solution = Solution()

    solution.solution(ticket).forEach { print("$it, ") }
}