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