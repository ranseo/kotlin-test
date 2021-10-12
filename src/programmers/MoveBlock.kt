package programmers

import java.util.*

//typealias P = Pair<Int, Int>
typealias P2 = Pair<Array<P>, Int>

fun P.plus(v: P): P = P(this.first + v.first, this.second + v.second)
fun P.minus(v: P): P = P(this.first - v.first, this.second - v.second)


class SolutionMoveBlock {
    val visit = hashMapOf<String, Boolean>()

    //아래,위,오른쪽,왼쪽
    val direction = arrayOf(P(1, 0), P(-1, 0), P(0, 1), P(0, -1))

    val rotationValue = arrayOf(P(1, 1), P(-1, 1), P(-1, -1), P(1, -1))

    //가운데 기준 - 왼쪽, 아래, 오른쪽, 위쪽
    val location = arrayOf(P(0, -1), P(1, 0), P(0, 1), P(-1, 0))
    var n = 0
    var answer = 123_456_789
    fun solution(board: Array<IntArray>): Int {
        n = board[0].size
        var drone = arrayOf(P(0, 0), P(0, 1))

        BFS(board, drone)

        return answer
    }

    //BFS.
    fun BFS(board: Array<IntArray>, drone: Array<P>) {
        var queue: ArrayList<P2> = arrayListOf()
        visit(drone)
        queue.add(P2(drone, 0))




        while (queue.isNotEmpty()) {
            var start = queue.first().first
            var count = queue.first().second

            println("start : ${start[0]}  ${start[1]}, count : $count")

            if ((start[0].first == n - 1) && (start[0].second == n - 1)) {
                answer = Math.min(answer, count)
                break
            }
            if ((start[1].first == n - 1) && (start[1].second == n - 1)) {
                answer = Math.min(answer, count)
                break
            }

            queue.removeAt(0)
            for (i in 0 until 4) {
                var next = move(start, direction[i])

                if (checkVisit(next)) continue
                if (!checkOutBoard(next[0]) || !checkOutBoard(next[1])) continue
                if (!checkCurrLoc(next, board)) continue


                visit(next)
                queue.add(P2(next, count + 1))
            }


            for (i in 0..1) {
                var next = start
                for (j in 0..1) {
                    next = start
                    for (k in 0 until 4) {
                        next = rotate(board, next, j, i)

                        if (checkVisit(next)) continue
                        if (!checkOutBoard(next[0]) || !checkOutBoard(next[1])) continue
                        if (!checkCurrLoc(next, board)) continue


                        visit(next)
                        queue.add(P2(next, count + 1))

                    }
                }
            }
        }


    }

    //움직이게 만드는 함수.
    fun move(drone: Array<P>, direction: P): Array<P> = arrayOf(drone[0].plus(direction), drone[1].plus(direction))


    //해당 board 위치 가능한지 확인. 가능하면 true 아니면 false
    fun checkCurrLoc(drone: Array<P>, board: Array<IntArray>): Boolean {
        val (left, right) = drone
        return !(board[left.first][left.second] == 1 || board[right.first][right.second] == 1)

    }

    //해당 board 위치 가능한지 확인. 가능하면 true 아니면 false - P 버전
    fun checkCurrLoc2(drone: P, board: Array<IntArray>): Boolean {
        val (i, j) = drone
        return board[i][j] != 1
    }

    //해당 좌표가 board를  벗어나지 않았다면 true, 벗어났다면 false
    fun checkOutBoard(p: P): Boolean {
        return !(p.first < 0 || p.second < 0 || p.first >= n || p.second >= n)
    }

    //해당 좌표에 방문했었는 지 체크. 방문했으면 true 안했으면 false
    fun checkVisit(drone: Array<P>): Boolean {
        val key1 = "${drone[0].first},${drone[0].second}+${drone[1].first},${drone[1].second}"
        val key2 = "${drone[1].first},${drone[1].second}+${drone[0].first},${drone[0].second}"
        if (visit.containsKey(key1)) return true
        if (visit.containsKey(key2)) return true

        return false
    }

    fun visit(drone: Array<P>) {
        val key1 = "${drone[0].first},${drone[0].second}+${drone[1].first},${drone[1].second}"
        val key2 = "${drone[1].first},${drone[1].second}+${drone[0].first},${drone[0].second}"
        if (!visit.containsKey(key1)) visit.put(key1, true)
        if (!visit.containsKey(key2)) visit.put(key2, true)
    }


    //90도 (반시계or시계) 방향으로 회전 시키는 함수. 반시계는 direction = 0 // 시계는 direction = 1
    fun rotate(board: Array<IntArray>, drone: Array<P>, center: Int, clockwise: Int): Array<P> {
        val rotation = if (center == 0) 1 else 0

        val tmp = drone[rotation].minus(drone[center])
        var rotate: P = P(0, 0)
        when (tmp) {
            //왼쪽
            location[0] -> {
                if (checkRotateLeft(board, drone[rotation], clockwise)) {
                    rotate = if (clockwise == 0) drone[rotation].plus(rotationValue[0]) else drone[rotation].plus(
                        rotationValue[1]
                    )
                } else return drone
            }

            //아래
            location[1] -> {
                if (checkRotateDown(board, drone[rotation], clockwise)) {
                    rotate = if (clockwise == 0) drone[rotation].plus(rotationValue[1]) else drone[rotation].plus(
                        rotationValue[2]
                    )
                } else return drone
            }

            //오른쪽
            location[2] -> {
                if (checkRotateRight(board, drone[rotation], clockwise)) {
                    rotate = if (clockwise == 0) drone[rotation].plus(rotationValue[2]) else drone[rotation].plus(
                        rotationValue[3]
                    )
                } else return drone
            }

            //위쪽
            location[3] -> {
                if (checkRotateUp(board, drone[rotation], clockwise)) {
                    rotate = if (clockwise == 0) drone[rotation].plus(rotationValue[3]) else drone[rotation].plus(
                        rotationValue[0]
                    )
                } else return drone
            }
        }

        return if (center == 0) arrayOf(drone[center], rotate) else arrayOf(rotate, drone[center])


    }

    //val direction = arrayOf(P(1, 0), P(-1, 0), P(0, 1), P(0, -1))
    fun checkRotateLeft(board: Array<IntArray>, rotation: P, clockwise: Int): Boolean {
        var tmp: P

        return if (clockwise == 0) {
            tmp = rotation.plus(direction[0])
            !(!checkOutBoard(tmp) || !checkCurrLoc2(tmp, board))
        } else {
            tmp = rotation.plus(direction[1])
            !(!checkOutBoard(tmp) || !checkCurrLoc2(tmp, board))
        }

    }

    fun checkRotateDown(board: Array<IntArray>, rotation: P, clockwise: Int): Boolean {
        var tmp: P

        return if (clockwise == 0) {
            tmp = rotation.plus(direction[2])
            !(!checkOutBoard(tmp) || !checkCurrLoc2(tmp, board))
        } else {
            tmp = rotation.plus(direction[3])
            !(!checkOutBoard(tmp) || !checkCurrLoc2(tmp, board))
        }
    }

    fun checkRotateRight(board: Array<IntArray>, rotation: P, clockwise: Int): Boolean {
        var tmp: P

        return if (clockwise == 0) {
            tmp = rotation.plus(direction[1])
            !(!checkOutBoard(tmp) || !checkCurrLoc2(tmp, board))
        } else {
            tmp = rotation.plus(direction[0])
            !(!checkOutBoard(tmp) || !checkCurrLoc2(tmp, board))
        }
    }

    fun checkRotateUp(board: Array<IntArray>, rotation: P, clockwise: Int): Boolean {
        var tmp: P

        return if (clockwise == 0) {
            tmp = rotation.plus(direction[3])
            !(!checkOutBoard(tmp) || !checkCurrLoc2(tmp, board))
        } else {
            tmp = rotation.plus(direction[2])
            !(!checkOutBoard(tmp) || !checkCurrLoc2(tmp, board))
        }
    }


}

fun main() {
    val solutionMoveBlock = SolutionMoveBlock()
    val board = arrayOf(
        intArrayOf(0, 0, 0, 1, 1),
        intArrayOf(0, 0, 0, 1, 0),
        intArrayOf(0, 1, 0, 1, 1),
        intArrayOf(1, 1, 0, 0, 1),
        intArrayOf(0, 0, 0, 0, 0)
    )
    val board2 = arrayOf(
        intArrayOf(0, 0, 1, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 1, 0, 0, 0, 0),
        intArrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 1, 1, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 0, 0, 0),
        intArrayOf(0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 1, 1, 0, 1),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    )
    println(solutionMoveBlock.solution(board2))
}