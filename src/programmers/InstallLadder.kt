package programmers

import test01.UnionFind
import kotlin.math.absoluteValue

typealias P1 = Pair<Int,Int>

class SolutionInstallLadder {
    //오른쪽,왼쪽,아래쪽,위쪽
    val move = arrayOf(P1(0,1), P1(0,-1), P(1,0), P(-1,0))

    val INF = 123_456_789
    var visit : Array<Array<Boolean>> = arrayOf()
    var group : Array<Array<Int>> = arrayOf()
    var groupVisit : Array<Boolean> = arrayOf()


    var N = 0
    var ladder = 0

    fun P1.plus(_p:P1) : P1 = P1(this.first + _p.first, this.second + _p.second)


    fun solution(land: Array<IntArray>, height: Int): Int {
        N = land.size
        val graph = mutableListOf<Edge>()

        group = Array(N){Array(N){0}}

        visit = Array(N){Array(N){false}}

        var groupNum = 1
        for(i in 0 until N) {
            for(j in 0 until N) {
                if(visit[i][j]) continue
                BFS(land,height, P1(i,j), groupNum)
                groupNum++
            }

        }


        //group.map { v->v.map { print("$it ") }; println() }

        ladder = groupNum-1

        groupVisit = Array(N*N+1){false}
        for(i in 1..ladder) {
            findLadder(land, graph ,i)
        }

        graph.sort()

        graph.map { println("${it.node[0]}, ${it.node[1]} and ${it.dist}")}


        //1.BFS로 이중배열 탐색. -> 각 그룹의 번호를 매긴다.- 그룹번호의 시작 좌표를 기록.
        //2.BFS가 끝나면 번호별로 탐색.

        //2번 탐색 시, 다른 번호와 접촉 후 두 층의 차이를 Min변수에 기록한다. 그리고 해당 idx와 다른 idx를 기록한다 Pair -> 탐색이 끝나면 가장 작은 Min을 가진 idx와 idx를 사다리로 연결되어있음을 확인한다.
        //탐색이 끝나면 다음 그룹 번호부터 탐색.

        //필요한 자료구조.


        return Kruskal_custom(graph,N)

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

    fun findLadder(land:Array<IntArray>, graph: MutableList<Edge>, groupNum: Int)  {
        if(groupVisit[groupNum]) return

        var Min = INF
        var ladderIdx = Array(2){0}
        var distance = 0
        group.mapIndexed{ i,v-> v.mapIndexed { j,elem ->
            if(elem == groupNum){
                val s = P1(i,j)
                val (s_x,s_y) = s

                for(i in 0 until 4) {
                    val n = s.plus(move[i])
                    val (n_x, n_y) = n


                    if(!isLand(n_x,n_y) || group[n_x][n_y] == groupNum) continue
                    //if(groupVisit[groupNum] || groupVisit[group[n_x][n_y]] ) continue

                    val dist = Math.abs(land[s_x][s_y] - land[n_x][n_y])
                    val tmp = Min
                    Min = Math.min(Min, dist)
                    if(tmp != Min) {
                        ladderIdx[0] = groupNum
                        ladderIdx[1] = group[n_x][n_y]
                        distance = dist
                    }
                }
            }
        }}

        graph.add(Edge(ladderIdx[0],ladderIdx[1],distance))

        groupVisit[ladderIdx[0]] = true
        groupVisit[ladderIdx[1]] = true
    }

    fun Kruskal_custom(graph:List<Edge>, size:Int) : Int {
        var sum = 0
        val n = size * size
        val rootParent = Array(n+1){0}
        for(i in 1..n) rootParent[i] = i

        val uf = UnionFind()

        for(i in 0 until graph.size) {
            val x = graph[i].node[0]
            val y = graph[i].node[1]
            val dist = graph[i].dist

            if(!uf.findParent(rootParent,x,y)) {
                sum += dist
                uf.unionParent(rootParent,x,y)
            }
        }

        return sum
    }

    fun getNode(_p:P1, size:Int) : Int {
        val (x,y) = _p.plus(P1(1,1))
        return (x*size) - (size-y)
    }

    fun isLand(x:Int, y:Int) : Boolean = !(x<0 || y<0 || x>=N || y>=N)


}

class Edge(a:Int, b:Int, _dist:Int) : Comparable<Edge> {
    val node = Array(2){0}
    var dist = 0

    init {
        node[0] = a
        node[1] = b
        dist = _dist
    }

    override fun compareTo(other: Edge): Int = this.dist - other.dist
}

fun main() {
    val solution = SolutionInstallLadder()
    val arr = arrayOf(intArrayOf(1,4,8,10), intArrayOf(5,5,5,5), intArrayOf(10,10,10,10), intArrayOf(10,10,10,20))
    val arr2 = arrayOf(intArrayOf(10,11,10,11), intArrayOf(2,21,20,10), intArrayOf(1,20,21,11), intArrayOf(2,1,2,1))
    val height = 3

    println(solution.solution(arr,height))
}