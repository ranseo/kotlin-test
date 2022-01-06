package programmers

//참고
//https://jisunshine.tistory.com/175

class SolutionRouteLightCycle {

    var Row = 0
    var Col = 0
    //아래(1,0), 왼(0,-1), 위(-1,0), 오른(0,1)
    //dr = d row , dc = d col
    val dr = arrayOf(1,0,-1,0)
    val dc = arrayOf(0,-1,0,1)

    lateinit var route : Array<Array<Array<Boolean>>>



    fun solution(grid:Array<String>): IntArray {
        var answer: IntArray = intArrayOf()
        Row = grid.size
        Col = grid[0].length

        route = Array(Row){Array(Col){Array(4){false}}}


        //d는 direction 의미
        for(i in 0 until Row) {
            for(j in 0 until Col) {
               for(d in 0..3) {
                   if(route[i][j][d]) continue
                   answer += shootTheLight(grid,i,j,d)

               }
            }
        }


        return answer.sorted().toIntArray()
    }

    fun shootTheLight(grid:Array<String>, i:Int, j:Int, k:Int): Int {
        var r = i
        var c = j
        var d = k

        var cnt = 0


        while(true) {
            if(route[r][c][d]) break

            cnt++
            route[r][c][d] = true

            if(grid[r][c] == 'L') d = if(d==0) 3 else d-1
            else if(grid[r][c] == 'R') d = if(d==3) 0 else d+1

            r = (r+dr[d]+Row) % Row
            c = (c+dc[d]+Col) % Col
        }

        return cnt
    }
}

fun main() {
    val solutionRouteLightCycle = SolutionRouteLightCycle()
    val grid = arrayOf("SL", "LR")
    solutionRouteLightCycle.solution(grid).forEach { println(it) }
}