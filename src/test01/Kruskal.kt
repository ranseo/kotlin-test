package test01

import java.util.*

class Edge(a:Int, b:Int, _dist:Int) : Comparable<Edge> {
    var node : Array<Int>
    var dist : Int = 0

    init {
        this.node = Array(2){0}
        this.node[0] = a
        this.node[1] = b
        this.dist = _dist
    }

    override fun compareTo(other: Edge): Int = (this.dist - other.dist)
}

class Kruskal {
    fun solution(_list:MutableList<Edge>, size:Int) {
        val n = size
        val list = _list.toMutableList()

        //간선(=EDGE)의 비용(=거리)을 오름차순으로 정리
        list.sort()

        //각 정점의 최상위 노드 초기화
        val rootParent : Array<Int> = arrayOf()
        for(i in 0 until n) rootParent[i] = i

        //거리의 합을 0으로 초기화
        var sum = 0
        val unionFind = UnionFind()



    }
}

fun main() {
    val kruskal = Kruskal()
    val list = listOf<Edge>(Edge(1,2,44), Edge(1,4,12),Edge(1,5,27),Edge(1,7,56),
        Edge(2,4,34),Edge(2,5,11),Edge(3,5,60),Edge(3,6,25),Edge(4,7,58),
        Edge(5,6,42),Edge(5,7,31))
}