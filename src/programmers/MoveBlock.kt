package programmers

import com.sun.org.apache.xalan.internal.xsltc.runtime.Operators
import kotlin.math.min

fun P.minus(_p: P): P {
    return P(this.first - _p.first, this.second - _p.second)
}


class SolutionMoveBlock {

    //아래, 위, 오른쪽, 왼쪽
    val direction = arrayOf(P(1, 0), P(-1, 0), P(0, 1), P(0, -1))
    var drone = arrayOf(P(0, 0), P(0, 1))
    var size = 0
    var answer = 123_456_789

    fun solution(board: Array<IntArray>): Int {

        size = board.size

        dfs(board,Array(size+1){Array(size+1){false}}, P(size-1,size-1), 0)

        //dfs로 nxn까지 이동.-최소의 수를 찾아난다.
        return answer
    }

    //dfs
    fun dfs(board: Array<IntArray>, visit: Array<Array<Boolean>>, target: P, count: Int) {
        if (target == drone[0] || target == drone[1]) {
            answer = min(answer, count)
            return
        }

        for (i in 0 until 4) {
            if(move(board, direction[i], visit)) {
                println("[ ${direction[i].first}, ${direction[i].second} ]")
                dfs(board, visit, target, count+1)
                //backMove(direction[i],visit)
            }
        }

        for(i in 0 until 4) {
            for(j in 0..1) {
                if(rotate(board, j, visit)) dfs(board, visit,target,count+1)
                println("[ rotate! ]")
            }
        }
    }

    //드론이 움직인 위치가 벽(=1)에 막히지 않는지, 보드판을 나가진 않는지 체크
    fun checkBlock(board: Array<IntArray>, move: Array<P>): Boolean {
        val (first, second) = move
        val (f_i, f_j) = first
        val (s_i, s_j) = second

        if ( ( checkOutBoard(f_i, f_j) || checkOutBoard(s_i,s_j) ) ) return false

        return !((1 == board[f_i][f_j]) || (1 == board[s_i][s_j]))
    }

    //보드판을 나가지 않는지 체크
    fun checkOutBoard(x: Int, y: Int): Boolean {
        return x < 0 || y < 0 || x >= size || y >= size
    }

    //드론을 움직이게 하는 Move 함수
    fun move(board: Array<IntArray>, direction: P, visit: Array<Array<Boolean>>): Boolean {


        //드론 2x1 위치 모두 확인
        val (first, second) = drone
        var (f_i, f_j) = first
        var (s_i, s_j) = second

        print(" move전 : [ $f_i, $f_j ], [ $s_i, $s_j ] ----------> move후 :")

        val (next_i, next_j) = direction
        val f_n = P(f_i + next_i, f_j + next_j)
        val s_n = P(s_i + next_i, s_j + next_j)
        val droneMove = arrayOf(f_n, s_n)

        if (!checkBlock(board, droneMove)) return false

        if(visit[f_n.first][f_n.second] && visit[s_n.first][s_n.second]) return false

        visit[f_n.first][f_n.second] = true
        visit[s_n.first][s_n.second] = true

        println("[ ${f_n.first}, ${f_n.second} ], [ ${s_n.first}, ${s_n.second} ]")
        drone = droneMove
        return true
    }


    //드론을 회전 시키는 rotate함수
    fun rotate(board: Array<IntArray>, center: Int, visit: Array<Array<Boolean>>): Boolean {

        //3x3 짜리 이차원 배열이라 생각하고, center에 해당하는 드론은 (1,1)로. rotate에 해당하는 드론은 센터에 맞추기.
        var tmp = drone[center]
        val rotate = if (center == 0) 1 else 0

        //회전 시 벽 있는지 체크
        if (!checkBlockForRotate(board, center, rotate)) return false

        var (tmp_first, tmp_second) = tmp
        var first_count = 0
        var second_count = 0

        while (tmp_first > 1 || tmp_second > 1) {
            if (tmp_first > 1) {
                tmp_first--
                first_count++
            }

            if (tmp_second > 1) {
                tmp_second--
                second_count++
            }
        }

        //회전
        tmp = drone[rotate]
        tmp = P(tmp.first - first_count, tmp.second - second_count)
        tmp = P(3 - tmp.second - 1, tmp.first)
        tmp = P(tmp.first + first_count, tmp.second + second_count)

        //회전 후 해당 위치 벽인지 체크
        if (!checkBlock(board, arrayOf(drone[center], tmp))) return false


        drone[rotate] = tmp
        if(visit[drone[rotate].first][drone[rotate].second] && visit[drone[center].first][drone[center].second]) return false

        visit[drone[rotate].first][drone[rotate].second] = true
        visit[drone[center].first][drone[center].second] = true
        return true
    }


    //회전할 때 벽이 있는지 체크해주는 함수
    fun checkBlockForRotate(board: Array<IntArray>, center: Int, rotate: Int): Boolean {
        val (i, j) = drone[rotate]
        val tmp = drone[rotate].minus(drone[center])

        when (tmp) {
            //아래 -> 오른쪽을 확인한다.
            direction[0] -> return (j + 1 <= size && board[i][j + 1] == 0)
            //위 -> 왼쪽을 확인한다
            direction[1] -> return (j - 1 > 0 && board[i][j - 1] == 0)
            //오른쪽 -> 위쪽을 확인한다
            direction[2] -> return (i - 1 > 0 && board[i - 1][j] == 0)
            //왼쪽 -> 아래쪽을 확인한다
            direction[3] -> return (i + 1 <= size && board[i + 1][j] == 0)
        }

        return false
    }

}

fun main() {
    val solutionMoveBlock = SolutionMoveBlock()
    val board = arrayOf(intArrayOf(0,0,0,1,1),intArrayOf(0,0,0,1,0),intArrayOf(0,1,0,1,1),intArrayOf(1,1,0,0,1),intArrayOf(0,0,0,0,0))
    println(solutionMoveBlock.solution(board))
}