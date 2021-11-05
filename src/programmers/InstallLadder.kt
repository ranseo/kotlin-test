package programmers

import test01.UnionFind
import kotlin.math.absoluteValue

typealias P1 = Pair<Int,Int>

class SolutionInstallLadder {
    //오른쪽,왼쪽,아래쪽,위쪽
    val move = arrayOf(P1(0,1), P1(0,-1), P1(1,0), P1(-1,0))
    var visit : Array<Array<Int>> = arrayOf()

    //groupNumber가 key가 되고, mutableList에 좌표를 집어넣는 형식. -> 시간초과 해결하기 위해서
    val hashMap : HashMap<Int,MutableList<P1>> = hashMapOf()

    var N = 0
    var ladder = 0

    fun P1.plus(_p:P1) : P1 = P1(this.first + _p.first, this.second + _p.second)


    fun solution(land: Array<IntArray>, height: Int): Int {
        N = land.size
        val graph = mutableListOf<Edge>()

        visit = Array(N){Array(N){0}}

        var groupNum = 1
        for(i in 0 until N) {
            for(j in 0 until N) {
                if(visit[i][j]>0) continue
                BFS(land,height, P1(i,j), groupNum)
                groupNum++
            }

        }


        //group.map { v->v.map { print("$it ") }; println() }

        ladder = groupNum-1

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

        visit[x][y] = groupNum
        q.add(s)

        while(!q.isEmpty()) {
            s = q[0]
            var (s_x, s_y) = s

            q.removeAt(0)

            for(i in 0 until 4) {
                val next = s.plus(move[i])
                val (n_x, n_y) = next

                if(!isLand(n_x,n_y)) continue

                //val interval = (land[s_x][s_y] - land[n_x][n_y]).absoluteValue
                val interval = Math.abs(land[s_x][s_y] - land[n_x][n_y])

                if(visit[n_x][n_y] > 0 || interval > height) continue

                visit[n_x][n_y] = groupNum
                q.add(next)

                hashMap[groupNum]?.add(P1(n_x,n_y)) ?: hashMap.put(groupNum, mutableListOf())?.add(P1(n_x,n_y))
            }
        }
    }

    fun findLadder(land:Array<IntArray>, graph: MutableList<Edge>, groupNum: Int)  {

        val groupVisit = hashMap[groupNum] ?: return

        groupVisit.forEach { s->
            val (s_x,s_y) = s
            val s_g = groupNum

            for(i in 0 until 4) {
                val n = s.plus(move[i])
                val (n_x, n_y) = n


                if(!isLand(n_x,n_y) || visit[n_x][n_y] <= s_g) continue

                //nextGroup
                val n_g = visit[n_x][n_y]
                val dist = Math.abs(land[s_x][s_y] - land[n_x][n_y])

                graph.add(Edge(s_g,n_g,dist))
            }
        }

        visit.mapIndexed{ i,v-> v.mapIndexed { j,elem ->
            if(elem == groupNum){
                val s = P1(i,j)
                val (s_x,s_y) = s
                val s_g = groupNum

                for(i in 0 until 4) {
                    val n = s.plus(move[i])
                    val (n_x, n_y) = n


                    if(!isLand(n_x,n_y) || visit[n_x][n_y] <= s_g) continue
                    //nextGroup
                    val n_g = visit[n_x][n_y]
                    val dist = Math.abs(land[s_x][s_y] - land[n_x][n_y])

                    graph.add(Edge(s_g,n_g,dist))
                }
            }
        }}
    }


    fun findLadder2 (land:Array<IntArray>, graph: MutableList<Edge>, groupNum: Int)  {
       visit.map { it.filter { v -> v == groupNum }}


    }

    fun Kruskal_custom(graph:List<Edge>, size:Int) : Int {
        var sum = 0
        val n = size * size
        val rootParent = Array(n+1){0}
        val groupVisit = Array(n+1){false}

        for(i in 1..n) rootParent[i] = i

        val uf = UnionFind()

        for(i in 0 until graph.size) {
            val x = graph[i].node[0]
            val y = graph[i].node[1]
            val dist = graph[i].dist
            if(groupVisit[x] && groupVisit[y]) continue

            if(!uf.findParent(rootParent,x,y)) {
                sum += dist
                uf.unionParent(rootParent,x,y)
                groupVisit[x] = true
                groupVisit[y] = true
            }
        }

        return sum
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

    println(solution.solution(arr2,1))
}


//노드 1, 2 사이에 수많은 WEIGHT를 가진 간선이 있다고 생각하고, 다 넣어버리자.
//그리고 그룹을 edge로 list에 add할 때 자신의 그룹번호보다는 낮은 그룹번호는 무시하도록. - 이미 이 전에 추가가 되었기 때문.