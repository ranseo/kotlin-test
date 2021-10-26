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

class SolutionInspection2 {
    var wallSize = 0
    var weakSize = 0
    var distSize = 0
    var INF = 123_456_789
    var answer = INF
    fun solution(n: Int, weak: IntArray, dist: IntArray): Int {


        wallSize = n
        weakSize = weak.size
        distSize = dist.size

        //n은 외벽의 길이 (1~200)
        //weak은 사이즈 = (1~15), 오름차순 정렬 - 취약지점으로 친구들이 들러야 할 방문점. n으로부터 떨어진 거리로 표시
        //dist 는 각 친구들이 이동할 수 있는 거리, 사이즈 = (1~8), 1에서 100이하의 원소

        //해야할 것, dist를 내림차순 정렬하고, 첫번째 원소부터 모든 취약지점을 돌아다녔을 때 최소의 수 = 1 이 나오는 지 검사.
        dist.sortDescending()

        val tmpWeak = weak.toMutableList()
        val tmpDist = dist.toMutableList()
        //확인해볼 수 있는 루트 찾기.
        checkWall(tmpWeak,tmpDist)
        if(answer == 1) return answer

        //친구들의 순서를 순열로 번갈아 루트 찾기.
        permutation(tmpWeak, tmpDist, mutableListOf(), Array<Boolean>(distSize+1){false})



        return if(answer>100) -1 else answer
    }

    fun permutation(weakSite: MutableList<Int>, dist_fix: MutableList<Int>, dist_target: MutableList<Int>, visit: Array<Boolean>) {
        if(distSize == dist_target.size) {
            checkWall(weakSite, dist_target)
        }

        for(i in 0 until distSize) {
            if(visit[i]) continue

            dist_target.add(dist_fix[i])
            visit[i] = true

            permutation(weakSite, dist_fix, dist_target, visit)

            visit[i] = false
            dist_target.removeAt(dist_target.size-1)


        }
    }

    fun checkWall(weakSite: MutableList<Int>, distance: MutableList<Int>) {
        var tmpWeak = weakSite.toMutableList()

        for(a in 0 until weakSize) {
            var Min = INF
            var success = false
            var tmpDist = distance.toMutableList()
            //end변수는 친구가 취약지점에서 시작해 갈 수 있는 최대 거리를 의미
            var end = tmpDist[0] + tmpWeak[0]
            tmpDist.removeAt(0)

            tmpWeak.mapIndexed{i,v ->
                if(end < v) {
                    if(tmpDist.isEmpty()) return@mapIndexed
                    end = tmpDist[0] + v
                    tmpDist.removeAt(0)
                }
                if(i== weakSize-1 && end >= v) success = true
            }
            if(success) Min = distSize - tmpDist.size
            answer = Math.min(answer , Min)

            if(answer == 1) return

            val tmp = tmpWeak[0] + wallSize
            tmpWeak.removeAt(0)
            tmpWeak.add(tmp)
        }
    }
}