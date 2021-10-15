package programmers

import java.util.*

//typealias P = Pair<Int, Int>
typealias P2 = Pair<Array<P>, Int>
typealias T1 =Triple<Array<P>, Int, MutableList<String>>

fun P.plus(v: P): P = P(this.first + v.first, this.second + v.second)
fun P.minus(v: P): P = P(this.first - v.first, this.second - v.second)
fun P.compare(v:P): Boolean = (this.first-v.first > 0 || this.second - v.second >0 )

class SolutionMoveBlock {
    val visit = hashMapOf<String, Boolean>()
    var visitRoute = mutableListOf<String>()
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
        //var queue: ArrayList<P2> = arrayListOf()
        var queue: ArrayList<T1> = arrayListOf()
        Visit(drone)

        queue.add(T1(drone, 0, mutableListOf()))




        while (queue.isNotEmpty()) {
            var start = queue.first().first
            var count = queue.first().second
            var route = queue.first().third
            var FinalRoute = ""


            println("start : ${start[0]}  ${start[1]}, count : $count")


            if ((start[0].first == n - 1) && (start[0].second == n - 1)) {
                FinalRoute = "${start[0]}, ${start[1]} and Count : ${count}"
                answer = Math.min(answer, count)
                print(route+FinalRoute )
                println()
                break
            }
            if ((start[1].first == n - 1) && (start[1].second == n - 1)) {
                FinalRoute= "${start[0]}, ${start[1]} and Count : ${count}"
                answer = Math.min(answer, count)
                print(route+FinalRoute )
                println()
                break
            }

            queue.removeAt(0)
            for (i in 0 until 4) {
                var next = move(start.clone(), direction[i])

                if (checkVisit(next)) continue
                if (!checkOutBoard(next[0]) || !checkOutBoard(next[1])) continue
                if (!checkCurrLoc(next, board)) continue


                Visit(next)
                val tmp = route.toMutableList()
                var visitRoute = "[Move!] ${start[0]}, ${start[1]} and Count : ${count} ->"
                tmp.add(visitRoute)
                queue.add(T1(next, count + 1, tmp))
            }


            for (i in 0..1) {
                    var next1 = rotate(board, start.clone(), i, 0)

                    if(next1.size < 1) continue
                    if (checkVisit(next1)) continue
                    if (!checkOutBoard(next1[0]) || !checkOutBoard(next1[1])) continue
                    if (!checkCurrLoc(next1, board)) continue


                    Visit(next1)
                    val tmp = route.toMutableList()
                    var visitRoute = "[반시계 회전!] ${start[0]}, ${start[1]} and Count : ${count}  ->"
                    tmp.add(visitRoute)
                    queue.add(T1(next1, count + 1,tmp))

            }


            for (i in 0..1) {
                    var next2 = rotate(board, start.clone(), i, 1)

                    if(next2.size < 1) continue
                    if (checkVisit(next2)) continue
                    if (!checkOutBoard(next2[0]) || !checkOutBoard(next2[1])) continue
                    if (!checkCurrLoc(next2, board)) continue


                    Visit(next2)
                    val tmp = route.toMutableList()
                    var visitRoute = "[시계 회전!] ${start[0]}, ${start[1]} and Count : ${count} ->"
                    tmp.add(visitRoute)
                    queue.add(T1(next2, count + 1,tmp))
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

    fun Visit(drone: Array<P>) {
        val key1 = "${drone[0].first},${drone[0].second}+${drone[1].first},${drone[1].second}"
        val key2 = "${drone[1].first},${drone[1].second}+${drone[0].first},${drone[0].second}"
        if (!visit.containsKey(key1)) visit.put(key1, true)
        if (!visit.containsKey(key2)) visit.put(key2, true)
    }


    //90도 (반시계or시계) 방향으로 회전 시키는 함수. 반시계는 direction = 0 // 시계는 direction = 1
    //val rotationValue = arrayOf(P(1, 1), P(-1, 1), P(-1, -1), P(1, -1))
    //가운데 기준 - 왼쪽, 위, 오른쪽, 아래
    //val location = arrayOf(P(0, -1), P(1, 0), P(0, 1), P(-1, 0))
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
                } else return arrayOf()
            }

            //아래
            location[1] -> {
                if (checkRotateDown(board, drone[rotation], clockwise)) {
                    rotate = if (clockwise == 0) drone[rotation].plus(rotationValue[1]) else drone[rotation].plus(
                        rotationValue[2]
                    )
                } else return arrayOf()
            }

            //오른쪽
            location[2] -> {
                if (checkRotateRight(board, drone[rotation], clockwise)) {
                    rotate = if (clockwise == 0) drone[rotation].plus(rotationValue[2]) else drone[rotation].plus(
                        rotationValue[3]
                    )
                } else return arrayOf()
            }

            //위
            location[3] -> {
                if (checkRotateUp(board, drone[rotation], clockwise)) {
                    rotate = if (clockwise == 0) drone[rotation].plus(rotationValue[3]) else drone[rotation].plus(
                        rotationValue[0]
                    )
                } else return arrayOf()
            }
        }

        return if (rotate.compare(drone[center])) arrayOf(drone[center], rotate) else arrayOf(rotate, drone[center])
    }

    //
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