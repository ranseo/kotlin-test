package programmers

import kotlin.math.exp

typealias PM = Pair<Char,Int>
class SolutionMathExpMax() {

    fun solution(expression: String) : Long {
        var answer: Long = 0

        //피연산자, 연산자 부분을 분리 -> List로 저장
        val num = expression.split("-","*","+").map{it.toLong()}
        val oper = expression.filter { (it=='-' || it=='*' || it=='+')}.toList()

        //유니온파인드 rootParent 만들기.
        val rootParent = Array(num.size){0}
        for(i in num.indices) rootParent[i] = i

//        println(num)
//        println(oper)

        //연산자의 순서가 담긴 조합 변수 - 조합 함수를 통해 만들어준다.
        val perm = mutableListOf<List<Char>>()
        permutation(perm,oper.distinct(), listOf(), Array(3){false})

//        println(perm)

        //연산자와 인덱스가 Pair<Char,Int>타입으로 담긴 operToIdx
        val operToIdx = oper.mapIndexed{i,v-> PM(v,i)}

        println(operToIdx)

        for(p in perm) {
            val unionFind = UnionFind(rootParent.clone())
            val tmp = num.toMutableList()
            for(curr in p) {
                operToIdx
                    .filter{ it.first==curr }
                    .asSequence()
                    .forEach{ oti ->
                        val o = oti.first
                        val i = oti.second
                        var result = 0L

                        val f_i = unionFind.find(i)
                        val s_i = unionFind.find(i+1)

                        val fir = tmp[f_i]
                        val sec = tmp[s_i]

                        result = when(o){
                            '*' -> fir*sec
                            '-' -> fir-sec
                            else -> fir+sec
                        }

                        tmp[f_i] = result
                        unionFind.union(f_i,s_i)
                    }
            }
            answer = answer.coerceAtLeast(Math.abs(tmp[0]))
            //answer = Math.max(answer,tmp[0])
        }

        return answer
    }

    fun permutation(ans:MutableList<List<Char>>, list:List<Char> ,oper:List<Char>, visit: Array<Boolean>) {
        if(oper.size==list.size) {
            ans.addAll(listOf(oper))
            return
        }

        for(i in list.indices) {
            if(visit[i]) continue
            visit[i]=true
            permutation(ans,list,oper+list[i], visit)
            visit[i]=false
        }
    }
}

fun main() {
    val solutionMathExp = SolutionMathExpMax()
    val s = "100-200*300-500+20"
    println(solutionMathExp.solution(s))

}

class UnionFind(_rootParent: Array<Int>) {
    val root : Array<Int>

    init {
        root = _rootParent
    }

    fun find(x:Int) : Int {
        return if(root[x]==x) x
        else find(root[x])
    }

    fun union(_x:Int, _y:Int) {
        val x = find(_x)
        val y = find(_y)

        if(x<y) root[y] = x else root[x] = y
    }

    //같으면 true, 틀리면 false
    fun isSame(x:Int, y:Int) :Boolean = (find(x) == find(y))

}
