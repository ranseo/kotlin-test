package programmers

import kotlin.math.absoluteValue

typealias P1 = Pair<Int,Int>
class SolutionInstallLadder {
    //오른쪽,왼쪽,아래쪽,위쪽
    val move = arrayOf(P1(0,1), P1(0,-1), P(1,0), P(-1,0))

    val INF = 123_456_789

    var group : Array<Array<Int>> = arrayOf()
    var visit : Array<Array<Boolean>> = arrayOf()
    var hashMap = hashMapOf<String, Boolean>()
    var N = 0
    var ladder = 0

    fun P1.plus(_p:P1) : P1 = P1(this.first + _p.first, this.second + _p.second)


    fun solution(land: Array<IntArray>, height: Int): Int {
        var answer = 0
        N = land.size

        group = Array(N){Array(N){0}}
        visit = Array(N+1){Array(N+1){false}}

        var groupNum = 1
        for(i in 0 until N) {
            for(j in 0 until N) {
                if(visit[i][j]) continue
                BFS(land,height, P1(i,j), groupNum)
                groupNum++
            }

        }


        group.map { v->v.map { print("$it ") }; println() }

        ladder = groupNum-1

        for(i in 1..ladder) {
            answer += putLadder(land, i)
        }


        //1.BFS로 이중배열 탐색. -> 각 그룹의 번호를 매긴다.- 그룹번호의 시작 좌표를 기록.
        //2.BFS가 끝나면 번호별로 탐색.

        //2번 탐색 시, 다른 번호와 접촉 후 두 층의 차이를 Min변수에 기록한다. 그리고 해당 idx와 다른 idx를 기록한다 Pair -> 탐색이 끝나면 가장 작은 Min을 가진 idx와 idx를 사다리로 연결되어있음을 확인한다.
        //탐색이 끝나면 다음 그룹 번호부터 탐색.

        //필요한 자료구조.


        return answer
    }

    fun BFS(land:Array<IntArray>, height: Int, start:P1, groupNum:Int) {
        val q = ArrayList<P1>()
        var s = start
        var (x,y) = s

        visit[x][y] = true
        q.add(s)
        group[x][y] = groupNum

        while(!q.isEmpty()) {
            s = q[0]
            var (s_x, s_y) = s

            q.removeAt(0)

            for(i in 0 until 4) {
                val next = s.plus(move[i])
                val (n_x, n_y) = next

                if(!isLand(n_x,n_y)) continue

                val interval = (land[s_x][s_y] - land[n_x][n_y]).absoluteValue

                if(visit[n_x][n_y] || interval > height) continue

                visit[n_x][n_y] = true
                q.add(next)
                group[n_x][n_y] = groupNum
            }
        }

    }

    fun putLadder(land:Array<IntArray>, groupNum: Int) : Int {
        var Min = INF
        var ladderIdx = Array(2){P1(0,0)}
        group.mapIndexed{ i,v-> v.mapIndexed { j,elem ->
            if(elem == groupNum){
                val s = P1(i,j)
                val (s_x,s_y) = s

                for(i in 0 until 4) {
                    val next = s.plus(move[i])
                    val (n_x, n_y) = next

                    if(!isLand(n_x,n_y) || group[n_x][n_y] <= groupNum) continue
                    val tmp = Min
                    Min = Math.min(Min, (land[s_x][s_y] - land[n_x][n_y]).absoluteValue)
                    if(tmp != Min) {
                        ladderIdx[0] = P1(n_x,n_y)
                        ladderIdx[1] = P1(s_x,s_y)
                    }
                }
            }
        }}

        if(Min == INF) return 0

        val key = "${ladderIdx[0]}, ${ladderIdx[1]}"

        if(!hashMap.containsKey(key)) {
            hashMap.put(key, true)
            println("groupNum : ${groupNum}, Min : ${Min}, key : $key")
            return Min
        }

        return 0
    }
    fun isLand(x:Int, y:Int) : Boolean = !(x<0 || y<0 || x>=N || y>=N)


}

fun main() {
    val solution = SolutionInstallLadder()
    val arr = arrayOf(intArrayOf(1,4,8,10), intArrayOf(5,5,5,5), intArrayOf(10,10,10,10), intArrayOf(10,10,10,20))
    val arr2 = arrayOf(intArrayOf(10,11,10,11), intArrayOf(2,21,20,10), intArrayOf(1,20,21,11), intArrayOf(2,1,2,1))
    val height = 3

    println(solution.solution(arr2,1))
}