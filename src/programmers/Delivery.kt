package programmers

import java.util.*

class SolutionDelivery {
    val graph = Array(50+1){Array(50+1){-1}}
    val hashMap = hashMapOf<Int,MutableList<Int>>()
    fun solution(N: Int, road: Array<IntArray>, k: Int): Int {
        initGraph(road)
        return BFS(1,Array(51){Int.MAX_VALUE},k)
    }


    fun initGraph(road:Array<IntArray>) {
        for(i in road) {
            val (a,b,w) = i
            hashMap[a]?.add(b) ?: hashMap.computeIfAbsent(a){mutableListOf<Int>(b)}
            hashMap[b]?.add(a) ?: hashMap.computeIfAbsent(b){mutableListOf<Int>(a)}

            if(graph[a][b] > -1) {
                if(graph[a][b] > w) {
                    graph[a][b] = w
                    graph[b][a] = w
                }
            } else {
                graph[a][b] = w
                graph[b][a] = w
            }
        }
    }

    fun BFS(start:Int, dist:Array<Int>, k:Int) : Int {
        var answer = 0
        val q = PriorityQueue<P>(compareBy{it.second})
        dist[start] = 0
        q.add(P(start,0))

        while(q.isNotEmpty()){
            var (curr,time) = q.peek()
            q.poll()

            if(dist[curr] < time) continue

            for(next in hashMap.get(curr) ?: mutableListOf<Int>(1)) {
                val currDist = dist[next]
                val newDist = time+graph[curr][next]
                if(newDist <= currDist) {
                    dist[next] = newDist
                    q.add(P(next,newDist))
                }
            }
        }

        for(i in dist) {
            if(i<=k) answer++
        }


        return answer
    }
}