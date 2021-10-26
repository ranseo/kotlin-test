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


//typealias P = Pair<Int,Int>
//typealias P2 = Pair<List<P>,Int>
class SolutionMoveBlock2 {
    //아래, 위, 오른쪽, 왼쪽
    val move = arrayOf(P(1,0), P(-1,0), P(0,1), P(0,-1))
    //<아래,오른쪽>, <아래, 왼쪽>, <위,오른쪽>, <위, 왼쪽>
    val rot_move = arrayOf(P(1,1), P(1,-1) , P(-1,1), P(-1,-1))
    var boardSize = 0

    enum class NSEW{
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    fun P.minus(_p:P) : P = P(this.first - _p.first, this.second - _p.second)
    fun P.plus(_p:P) : P = P(this.first + _p.first, this.second + _p.second)



    fun solution(board: Array<IntArray>): Int {
        boardSize = board.size

        //bfs위한 queue
        //해쉬맵 자료구조 (방문용 체크), drone의 좌표 담을 Pair, 드론의 move[4] P, 드론의 로테이션 관리. 반시계,시계.
        //board넘어갔는지 체크, board의 벽에 위치했는지 check, 회전 시 회전방향에 보드 벽 있는지 체크.
        //드론의 중심에서 다른 한 쪽이 어느 방향에 있는지 알아내는 그런 함수.

        return bfs(board, listOf(P(0,0), P(0,1)) )
    }

    fun bfs(board:Array<IntArray>, start: List<P>) : Int {
        //드론이 움직인 위치를 담는 queue
        val q : ArrayList<P2> = arrayListOf()
        //드론의 방문 위치를 체크하는 해쉬맵. key는
        val visit : HashMap<String,Boolean> = hashMapOf()

        //start q에 푸쉬
        q.add(P2(start,0))
        //첫 start 위치 방문 체크.
        visit.put(makeKey(start), true)

        //BFS시작.
        while(!q.isEmpty()) {
            val (s,c) = q[0]
            q.removeAt(0)

            if(findResult(s)) return c

            //move
            for(i in 0 until 4) {
                val next = moveDrone(s, move[i])
                if(checkBoard(next,board)) continue
                if(checkBlock(next,board) || checkVisit(next,visit)) continue

                visit.put(makeKey(next),true)

                q.add(P2(next,c+1))
            }


            //rotate 시계
            for(center in 0..1) {
                val rotIdx = if(center==0) 1 else 0
                val loc = s[rotIdx].minus(s[center])
                val nsew = getDirection(loc)

                val next = rotate(s[center], s[rotIdx], nsew).sortedWith(compareBy({it.first},{it.second}))

                if(checkBoard(next,board)) continue
                if(checkVisit(next,visit)) continue
                if(checkRotBlock(s[rotIdx],board,nsew) || checkBlock(next, board)) continue

                visit.put(makeKey(next),true)
                q.add(P2(next,c+1))

            }


            //rotate 반시계
            for(center in 0..1) {
                val rotIdx = if(center==0) 1 else 0
                val loc = s[rotIdx].minus(s[center])
                val nsew = getDirection(loc)

                val next = rotate_R(s[center], s[rotIdx], nsew).sortedWith(compareBy({it.first},{it.second}))

                if(checkBoard(next,board)) continue
                if(checkVisit(next,visit)) continue
                if(checkRotBlock_R(s[rotIdx],board,nsew) || checkBlock(next, board)) continue

                visit.put(makeKey(next),true)
                q.add(P2(next,c+1))

            }
        }

        return -1
    }

    fun makeKey(_key:List<P>) : String  = "${_key[0].first},${_key[0].second}+${_key[1].first},${_key[1].second}"

    //이미 방문된 위치라면 true, 방문되지 않은 위치라면 false
    fun checkVisit(_key:List<P>, visit:HashMap<String,Boolean>) : Boolean  = visit[makeKey(_key)] ?: false

    //드론이 board를 넘어갔는지 확인, 넘어갔다면 true 그렇지 않으면 false
    fun checkBoard(drone:List<P>, board:Array<IntArray>) : Boolean {
        val (d1,d2) = drone
        return (d1.first < 0 || d1.second < 0 || d1.first >= boardSize || d1.second >= boardSize || d2.first < 0 || d2.second < 0 || d2.first >= boardSize || d2.second >= boardSize)
    }

    //드론이 board = 1 위에 위치하고 있는지 확인, 그렇다면 true 그렇지 않다면 false
    fun checkBlock(drone:List<P>, board: Array<IntArray>) : Boolean {
        val (d1,d2) = drone
        return (board[d1.first][d1.second] == 1 || board[d2.first][d2.second] == 1)
    }

    //드론을 아래, 위, 오른쪽, 왼쪽 (P(1,0), P(-1,0), P(0,1), P(0,-1)) 으로 이동시키는 함수
    fun moveDrone(drone: List<P>, dir : P) : List<P> {
        val (d1,d2) = drone
        return listOf(d1.plus(dir), d2.plus(dir))
    }

    //드론의 중심점을 기준으로 다른 부품이 어느 방향에 있는지 리턴하는 함수
    fun getDirection(loc:P) : NSEW  = if(loc == move[0]) NSEW.SOUTH
    else if(loc == move[1]) NSEW.NORTH
    else if(loc == move[2]) NSEW.EAST
    else NSEW.WEST



    //드론 회전 시계
    //dirt = direction 방위
    //<아래,오른쪽>, <아래, 왼쪽>, <위,오른쪽>, <위, 왼쪽>
    fun rotate(d_center:P, d_rot:P, dirt: NSEW) : List<P> = when(dirt){
        NSEW.NORTH -> listOf(d_center, d_rot.plus(rot_move[0]))
        NSEW.SOUTH -> listOf(d_center, d_rot.plus(rot_move[3]))
        NSEW.EAST -> listOf(d_center, d_rot.plus(rot_move[1]))
        NSEW.WEST -> listOf(d_center, d_rot.plus(rot_move[2]))
    }


    fun rotate_R(d_center:P, d_rot:P, dirt: NSEW) : List<P> = when(dirt){
        NSEW.NORTH -> listOf(d_center, d_rot.plus(rot_move[1]))
        NSEW.SOUTH -> listOf(d_center, d_rot.plus(rot_move[2]))
        NSEW.EAST -> listOf(d_center, d_rot.plus(rot_move[3]))
        NSEW.WEST -> listOf(d_center, d_rot.plus(rot_move[0]))
    }





    //dirt = direction 방위
    //드론의 회전 진행 방향에 블록이 있는지 확인, 회전 (시계방향) 방향에 블록이 있으면 true 그렇지 않으면 false
    fun checkRotBlock(drone_rot:P, board:Array<IntArray>, dirt:NSEW) : Boolean {
        //clockWise가 시계 방향일 경우
        return  when(dirt){
            NSEW.NORTH -> {
                val (idx1,idx2) = drone_rot.plus(move[2])
                if(board[idx1][idx2] == 1) true else false
            }
            NSEW.SOUTH -> {
                val (idx1,idx2) = drone_rot.plus(move[3])
                if(board[idx1][idx2] == 1) true else false
            }
            NSEW.EAST -> {
                val (idx1,idx2) = drone_rot.plus(move[0])
                if(board[idx1][idx2] == 1) true else false
            }
            NSEW.WEST -> {
                val (idx1,idx2) = drone_rot.plus(move[1])
                if(board[idx1][idx2] == 1) true else false
            }
        }
    }

    //dirt = direction 방위
    fun checkRotBlock_R(drone_rot:P, board:Array<IntArray>,dirt:NSEW) : Boolean {
        //clockWise가 반시계 방향일 경우
        return  when(dirt){
            NSEW.NORTH -> {
                val (idx1,idx2) = drone_rot.plus(move[3])
                if(board[idx1][idx2] == 1) true else false
            }
            NSEW.SOUTH -> {
                val (idx1,idx2) = drone_rot.plus(move[2])
                if(board[idx1][idx2] == 1) true else false
            }
            NSEW.EAST -> {
                val (idx1,idx2) = drone_rot.plus(move[1])
                if(board[idx1][idx2] == 1) true else false
            }
            NSEW.WEST -> {
                val (idx1,idx2) = drone_rot.plus(move[0])
                if(board[idx1][idx2] == 1) true else false
            }
        }
    }

    //drone의 한 부분이라도 보드 사이즈에 도달하면 true, 그렇지 않으면 false
    fun findResult(drone: List<P>) : Boolean {
        val (d1,d2) = drone
        return ((d1.first == boardSize-1 && d1.second == boardSize-1) || (d2.first == boardSize-1 && d2.second == boardSize-1))
    }



}



