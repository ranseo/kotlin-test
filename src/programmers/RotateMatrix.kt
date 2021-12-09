package programmers

typealias T = Triple<Int,Int,Int>

class SolutionRotateMatrix {
    val INF = 123_456_789

    val colPlusOne = mutableListOf<T>()
    val rowPlusOne = mutableListOf<T>()
    val colMinusOne = mutableListOf<T>()
    val rowMinusOne = mutableListOf<T>()

    fun solution(rows: Int, columns: Int, queries: Array<IntArray>): IntArray {
        var answer = intArrayOf()
        val arr = Array(rows+1){Array(columns+1){0}}
        var value = 0
        for(i in 1..rows) {
            for(j in 1..columns) {
                arr[i][j] = ++value
            }
        }

        for(q in queries) {
            var Min = INF

            val list = listOf(colPlusOne.toMutableList(), rowPlusOne.toMutableList(), colMinusOne.toMutableList(), rowMinusOne.toMutableList())

            val (x1,y1,x2,y2) = q

            for(y in y1..y2-1) list[0].add(T(x1,y+1,arr[x1][y]))
            for(x in x1..x2-1) list[1].add(T(x+1,y2,arr[x][y2]))
            for(y in y1+1..y2) list[2].add(T(x2,y-1,arr[x2][y]))
            for(x in x1+1..x2) list[3].add(T(x-1,y1,arr[x][y1]))

            for(l in list) {
                for(t in l) {
                    val (x,y,v) = t
                    Min = Math.min(Min, v)
                    arr[x][y] = v
                }
            }
            answer = answer.plus(Min)
        }

        return answer
    }
}