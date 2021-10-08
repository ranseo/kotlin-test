package programmers

import java.lang.Integer.min

class SolutionInpsection {
    var wallLength : Int = 0
    var answer = 123
    lateinit var distList : MutableList<Int>
    fun solution(n: Int, weak: IntArray, dist: IntArray): Int {

        wallLength = n

        val size = dist.size
        dist.sortDescending()

        val weakList : MutableList<Int> = weak.toMutableList()
        distList  = dist.toMutableList()

        val tmp = weakList.toMutableList()
        for(i in 0 until tmp.size) {
            val start = tmp.first()
            val end = tmp.last()
            if(end-start <= distList.first()) return 1

            val startValue = tmp.first()
            tmp.removeFirst()
            tmp.add(wallLength+startValue)
        }

        permutation(weakList.toMutableList(), mutableListOf(), Array(size){false}, size)


        return if(answer==123) -1 else answer

    }

    //외벽 검사를 위한 함수. - 이중 for문으로 구성
    //1.첫번째 for문은 weak 취약지점을 재구성하는 역할
    //2.두번째 for문은 취약지점에 친구들을 투입하는 역할
    fun check(weak: MutableList<Int>, dist: List<Int>) {
        for(i in 0 until weak.size) {
            var startWeakIndex = 0
            var count = 0
            var flag = false


            for(j in 0 until dist.size) {
                if( (j+1 > answer) || flag) break

                //end = 취약지점 시작 위치 + 친구 한명이 감당할 수 있는 시간 : 즉, 친구 한명이 시작지점부터 어디까지의 취약지점을 감당할 수 있는 척도가 된다.
                val end = weak[startWeakIndex] + dist[j]
                //while문에서 weak값 end값보다 커지는 순간, 친구 한명이 감당할 수 있는 구간이 지났다는 것으로 친구를 더 추가한다.
                while(end >= weak[startWeakIndex]) {
                    startWeakIndex++
                    if(startWeakIndex == weak.size) {
                        count = j+1
                        flag = true
                        break
                    }
                }
            }

            if(flag) answer = min(answer, count)

            val startValue = weak.first()
            weak.removeFirst()
            weak.add(wallLength+startValue)

        }
    }

    //순열을 이용하여 dist (친구들의 이동거리가 담긴 리스트) 를 섞어주며 외벽점검에 투입할 수 있는 최적의 친구 수를 체크해준다.
    fun permutation(weak: MutableList<Int>, dist: MutableList<Int>, visit: Array<Boolean>, target:Int) {
        if(0==target) {
            check(weak.toMutableList(), dist.toList())
            return
        }

        for(i in 0 until distList.size) {
            if(visit[i]) continue
            visit[i] = true
            dist.add(distList[i])
            permutation(weak, dist, visit, target-1)
            dist.removeLast()
            visit[i]= false
        }
    }
}

fun main() {
    val solutionInpsection = SolutionInpsection()
    val n = 12
    val weak = intArrayOf(1,5,6,10)
    val weak2 = intArrayOf(1,3,4,9,10)
    val dist = intArrayOf(1,2,3,4)
    val dist2 = intArrayOf(3,5,7)

    println(solutionInpsection.solution(n,weak2,dist2))
}