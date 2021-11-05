package test01

class UnionFind{
    fun getParent(parent: Array<Int>, x:Int) : Int{
        if(parent[x] == x) return x
        return getParent(parent, parent[x])
    }

    fun findParent(parent:Array<Int>, _a:Int, _b:Int): Boolean {
        val a = getParent(parent, _a)
        val b = getParent(parent, _b)

        return a==b
    }

    fun unionParent(parent: Array<Int>, _a:Int, _b:Int) {
        val a = getParent(parent,_a)
        val b = getParent(parent,_b)

        if(a<b) parent[b] = a
        else parent[a] = b
    }
}

fun main() {
    val unionFind = UnionFind()

    val parent : Array<Int> = Array(10){0}
    for (i in 0 until 10) parent[i] = i

    unionFind.unionParent(parent, 1,2)
    unionFind.unionParent(parent, 2,3)
    unionFind.unionParent(parent, 3,4)

    unionFind.unionParent(parent, 5,6)
    unionFind.unionParent(parent, 6,7)
    unionFind.unionParent(parent, 7,8)

    println("1과 5는 연결되어 있는가? ${unionFind.findParent(parent,1,5)}")
    println()
    println("8의 최상위 노드는 무엇인가?? ${unionFind.getParent(parent,8)}")

    unionFind.unionParent(parent,1,5)
    println("1과 5는 연결되어 있는가? ${unionFind.findParent(parent,1,5)}")

    println("4의 최상위 노드는 무엇인가?? ${unionFind.getParent(parent,4)}")
    println("8의 최상위 노드는 무엇인가?? ${unionFind.getParent(parent,8)}")
}



