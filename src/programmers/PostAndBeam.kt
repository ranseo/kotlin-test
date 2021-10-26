package programmers

class SolutionPostAndBeam {
    lateinit var bluePrint: Array<Array<Array<Boolean>>>
    lateinit var currBuild: MutableList<IntArray>
    var size = 0

    fun solution(n: Int, build_frame: Array<IntArray>): List<List<Int>> {
        var answer = mutableListOf<List<Int>>()
        //x,y, (0=기둥 or 1=보)
        bluePrint = Array(n + 2) { Array(n + 2) { Array(2) { false } } }
        currBuild = mutableListOf()
        size = n

        build_frame.forEach { frame ->
            val (x, y, a, b) = frame

            if (b == 0) {
                bluePrint[x][y][a] = false
                currBuild.forEach { curr ->
                    val (i, j, k) = curr
                    if (bluePrint[x][y][a]) return@forEach
                    if (bluePrint[i][j][k] && !check(i, j, k)) bluePrint[x][y][a] = true
                }
                if (!bluePrint[x][y][a]) currBuild.remove(intArrayOf(x, y, a))
            } else {
                if (check(x, y, a)) {
                    bluePrint[x][y][a] = true
                    currBuild.add(intArrayOf(x, y, a))
                }
            }
        }

//        bluePrint.mapIndexed { i, x ->
//            x.mapIndexed { j, y ->
//                print("[ ")
//                y.mapIndexed { k, isBuild ->
//                    if(isBuild)
//                        print(isBuild)
//                    else print(String.format("%2s",""))
//                }
//                print(" ]")
//            }
//            println()
//        }

        bluePrint.mapIndexed { i, x ->
            x.mapIndexed { j, y ->
                y.mapIndexed { k, isBuild ->
                    if (isBuild) answer.add(
                        listOf(i, j, k)
                    )
                }
            }
        }

        return answer
    }

    fun check(x: Int, y: Int, a: Int): Boolean {
        //a는 '기둥'일 경우, b는 '보'일 경우.
        if (a == 0) {
            //기둥이 바닥에 있을 때, '보'의 한쪽 끝 부분에 있을 때, 다른 기둥 위에 있을 때
            if (y == 0 || (bluePrint[x][y][1]) || (x-1 >= 0 && bluePrint[x-1][y][1]) || (y - 1 >= 0 && bluePrint[x][y - 1][0])) return true
        } else {
            //보의 한쪽 끝 부분이 기둥 위에 있을 때, 양쪽 끝 부분이 다른 보와 동시에 연결 되어 있을 때
            if ((y - 1 >= 0 && bluePrint[x + 1][y - 1][0]) || (y - 1 >= 0 && bluePrint[x][y - 1][0]) || (x-1 >= 0 && bluePrint[x + 1][y][1] && bluePrint[x - 1][y][1])) return true
        }

        return false
    }


}

fun main() {
    val solutionPostAndBeam = SolutionPostAndBeam()
    val n = 5
    val build_frame = arrayOf(
        intArrayOf(1, 0, 0, 1),
        intArrayOf(1, 1, 1, 1),
        intArrayOf(2, 1, 0, 1),
        intArrayOf(2, 2, 1, 1),
        intArrayOf(5, 0, 0, 1),
        intArrayOf(5, 1, 0, 1),
        intArrayOf(4, 2, 1, 1),
        intArrayOf(3, 2, 1, 1)
    )
    val build_frame2 = arrayOf(
        intArrayOf(0, 0, 0, 1),
        intArrayOf(2, 0, 0, 1),
        intArrayOf(4, 0, 0, 1),
        intArrayOf(0, 1, 1, 1),
        intArrayOf(1, 1, 1, 1),
        intArrayOf(2, 1, 1, 1),
        intArrayOf(3, 1, 1, 1),
        intArrayOf(2, 0, 0, 0),
        intArrayOf(1, 1, 1, 0),
        intArrayOf(2, 2, 0, 1)
    )

    println(solutionPostAndBeam.solution(n, build_frame2))
}

class SolutionPostAndBeam2 {
    //해당 좌표에 보 또는 기둥이 설치 되어있는지 유무를 확인하는 배열필요. 3차원 배열 [x][y][a]
    var building : Array<Array<Array<Boolean>>> = arrayOf()
    var size :Int = 0
    fun solution(n: Int, build_frame: Array<IntArray>): List<List<Int>> {
        var answer = mutableListOf<List<Int>>()
        building = Array(n+1){Array(n+1){Array(2){false}}}
        size = n

        build_frame.forEach{ frame ->
            val (x,y,a,b) = frame
            //b가 0이면 삭제, 1이면 설치
            if(b==1) {
                if(checkRuleForBuild(x,y,a)) {
                    building[x][y][a] = true
                }
            } else {
                //삭제 과정 1.해당 자리에 있는 건물을 없앤다. (false로 만든다) 2.다른 모든 건축물을 완전탐색하면서 현재 건물 상태가 룰에 맞는지 확인한다.
                //3.다른 모든 건물의 상태가 룰에 맞다면 삭제가 가능하다는 의미 - false로 내비둠. 그렇지 않다면 다시 true로 만든다.
                building[x][y][a] = false

                building.mapIndexed{i,v -> v.mapIndexed{ j, elem -> elem.mapIndexed{k,isTrue ->
                    if(isTrue && !checkRuleForBuild(i,j,k)) {
                        building[x][y][a] = true
                        return@mapIndexed}}}}}
        }

        //return 배열 => [x,y,a] 좌표 + 구조물의 종류 -> x좌표 기준 오름차순 정렬, x좌표와 같은 경우 오름차순 정렬.
        //x,y좌표가 모두 같은 경우 기둥이 보 보다 앞에 오면 된다.

        building.mapIndexed{i,v -> v.mapIndexed{j,elem -> elem.mapIndexed{ k, isTrue -> if(isTrue) answer.add(listOf(i,j,k))}}}

        return answer
    }

    //우선 규칙에 맞게 설치 및 삭제를 해야하기 때문에. 현재 건축물이 규칙에 맞는지 아닌지 체크할 수 있는 함수 필요
    //기둥은 바닥 위에 있거나, 보의 한쪽 끝 부분 위에 있거나 또는 다른 기둥 위에 있어야 한다.
    //보는 한쪽 끝 부분이 기둥 위엥 있거나, 또는 양쪾 끝 부분이 다른 보와 동시에 연결되어야 한다.
    fun checkRuleForBuild(x:Int, y:Int, a: Int): Boolean {
        //0이면 기둥. 1이면 보.
        if(a==0) {
            if(y==0 || building[x][y][1] || (x-1>=0 && building[x-1][y][1]) || (y-1>=0 && building[x][y-1][0])) return true
        } else {
            if((y-1>=0 && building[x][y-1][0]) || ( y-1>=0 && building[x+1][y-1][0])
                || ( x-1>=0 && building[x-1][y][1] && building[x+1][y][1])) return true
        }

        return false
    }
}