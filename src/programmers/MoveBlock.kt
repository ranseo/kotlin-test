package programmers

import java.util.*

//typealias P = Pair<Int, Int>
typealias P2 = Pair<Array<P>, Int>

fun P.plus(v: P) : P = P(this.first + v.first, this.second + v.second)
fun P.minus(v: P) : P = P(this.first - v.first, this.second - v.second)




class SolutionMoveBlock {
    val visit = hashMapOf<String, Boolean>()
    val direction = arrayOf(P(1,0), P(-1,0), P(0,1), P(0,-1))


    val rotationValue = arrayOf(P(1,1), P(-1,1), P(-1,-1), P(1,-1))
    //가운데 기준 - 왼쪽, 아래, 오른쪽, 위쪽
    val location = arrayOf(P(0,-1), P(1,0),P(0,1), P(-1,0))
    var n = 0
    var answer =0
    fun solution(board: Array<IntArray>): Int {
         n = board.size
        var drone = arrayOf(P(0,0), P(0,1))



        return answer
    }

    //BFS.
    fun BFS(board: Array<IntArray>, drone: Array<P>) {
        var queue : ArrayList<P2> = arrayListOf()
        visit(drone)
        queue.add(P2(drone,0))


        while(!queue.isEmpty()) {
            var start = queue.first().first
            var count = queue.first().second

            queue.removeAt(0)
            for(i in 0 until 4) {
                var next = move(start, direction[i])

                if(!checkCurrLoc(next, board)) continue
                if(checkVisit(next)) continue

                visit(next)
                count++
                queue.add(P2(next, count))
            }
        }


    }
    //움직이게 만드는 함수.
    fun move(drone: Array<P>, direction: P) : Array<P> = arrayOf(drone[0].plus(direction), drone[1].plus(direction))



    //해당 board 위치 가능한지 확인. 가능하면 true 아니면 false
    fun checkCurrLoc(drone: Array<P>, board: Array<IntArray>) : Boolean {
        val (left,right) = drone
        return !(checkOutBoard(left) || checkOutBoard(right) || board[left.first][left.second] == 1 || board[right.first][right.second] == 1)

    }
    //해당 좌표가 board를 벗어나서 불가능하다면 true 가능하다면 false
    fun checkOutBoard(p: P) : Boolean {
        return !(p.first < 0 || p.second <0 || p.first >= n || p.second >= n)
    }

    //해당 좌표에 방문했었는 지 체크. 방문했으면 true 안했으면 false
    fun checkVisit(drone: Array<P>) : Boolean {
        val key = "${drone[0].first},${drone[0].second}+${drone[1].first},${drone[1].second}"
        if(visit.containsKey(key)) return true

        return false
    }

    fun visit(drone: Array<P>) {
        val key = "${drone[0].first},${drone[0].second}+${drone[1].first},${drone[1].second}"
        if(!visit.containsKey(key)) visit.put(key,true)
    }



    //90도 반시계방향으로 회전 시키는 함수.
    fun rotateReverse(board: Array<IntArray>, drone: Array<P>, center: Int ) {
        val rotation = if(center==0) 1 else 0

        val tmp = drone[rotation].minus(drone[center])
        when(tmp) {
            //왼쪽
            location[0] ->
            //아래
            location[1] -> "."
            //오른쪽
            location[2] -> "."
            //위쪽
            location[3] -> "."
        }
    }
    //90도 시계방향으로 회전 시키는 함수.
    fun rotate(board: Array<IntArray>, drone: Array<P>, center:Int) {

    }

    //해당 위치에서 반시계방향 회전 가능한지 확인

    //해당 위치에서 시계방향 회전 가능한지 확인.


}

fun main() {
    val solutionMoveBlock = SolutionMoveBlock()
    val board = arrayOf(intArrayOf(0,0,0,1,1),intArrayOf(0,0,0,1,0),intArrayOf(0,1,0,1,1),intArrayOf(1,1,0,0,1),intArrayOf(0,0,0,0,0))
    println(solutionMoveBlock.solution(board))
}