import java.math.*

typealias P = Pair<Int,Int>
class SolutionChessBoard {
    //Queen[idx] : idx행에 있는 Queen의 열 위치
    lateinit var Queen : Array<Int>
    var answer = 0
    fun solution(n: Int): Int {

        Queen = Array(n){-1}
        dfs(n,0)
        return answer
    }

    fun dfs(n:Int, col:Int) {
        if(col==n) {
            if(Queen[n-1] > -1) answer++
            return
        }
        for(i in 0 until n) {
            if(isPossible(P(col,i),col)) {
                Queen[col] = i
                dfs(n,col+1)
                Queen[col] = -1
            }
        }
    }


    fun isPossible(p:P, col:Int) : Boolean {
        for(i in 0 until col) {
            if(p.second == Queen[i] || Math.abs(p.first-i) == Math.abs(p.second - Queen[i])) return false
        }
        return true
    }
}