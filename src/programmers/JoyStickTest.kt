package programmers

typealias PJ = Pair<String,Int>
class SolutionJoyStick {
    val INF = 123_456_789
    var Min = INF
    fun solution(name:String) : Int{

        val str = name.fold(""){acc,_ ->
            acc+"A"
        }

        dfs(name,str,0,Array<Int>(name.length){0}, 0, NONE)

        return Min
    }

    fun dfs(name: String, str:String, count:Int, visit:Array<Int>, idx:Int, vec:Int) {
        if(name == str) {
            Min = Math.min(Min, count-1)
            return
        }


        if( (idx >= name.length) || 2 <= visit[idx]) return
        val newVisit = visit.clone()
        newVisit[idx]++

        val (s_up,c_up) = moveUp(name,str,idx)

        if(c_up == 0 && vec == RIGHT) dfs(name,s_up,count+c_up+1, newVisit,idx+1, RIGHT)
        else if(c_up == 0 && vec == LEFT) dfs(name,s_up,count+c_up+1, newVisit, if(idx-1<0) name.length-1 else idx-1, LEFT)
        else if(vec == TURN_RIGHT) dfs(name,s_up,count+c_up+1, newVisit,idx+1, TURN_RIGHT)
        else if(vec == TURN_LEFT) dfs(name,s_up,count+c_up+1, newVisit, if(idx-1<0) name.length-1 else idx-1, TURN_LEFT)
        else if(c_up == 1 && vec ==LEFT) {
            dfs(name,s_up,count+c_up+1, newVisit,idx+1, TURN_RIGHT)
            dfs(name,s_up,count+c_up+1, newVisit, if(idx-1<0) name.length-1 else idx-1,LEFT)
        }
        else if(c_up ==1 && vec == RIGHT) {
            dfs(name,s_up,count+c_up+1, newVisit,idx+1,RIGHT)
            dfs(name,s_up,count+c_up+1, newVisit, if(idx-1<0) name.length-1 else idx-1, TURN_LEFT)
        }
        else {
            dfs(name,s_up,count+c_up+1, newVisit,idx+1,RIGHT)
            dfs(name,s_up,count+c_up+1, newVisit, if(idx-1<0) name.length-1 else idx-1,LEFT)
        }

        val (s_down, c_down) = moveDown(name,str,idx)

        if(c_down == 0 && vec == RIGHT) dfs(name,s_down,count+c_down+1, newVisit,idx+1, RIGHT)
        else if(c_down == 0 && vec == LEFT) dfs(name,s_down,count+c_down+1, newVisit, if(idx-1<0) name.length-1 else idx-1, LEFT)
        else if(vec == TURN_RIGHT) dfs(name,s_down,count+c_down+1, newVisit,idx+1, TURN_RIGHT)
        else if(vec == TURN_LEFT) dfs(name,s_down,count+c_down+1, newVisit, if(idx-1<0) name.length-1 else idx-1, TURN_LEFT)
        else if(c_down == 1 && vec ==LEFT) {
            dfs(name,s_down,count+c_down+1, newVisit,idx+1, TURN_RIGHT)
            dfs(name,s_down,count+c_down+1, newVisit, if(idx-1<0) name.length-1 else idx-1,LEFT)
        }
        else if(c_down ==1 && vec == RIGHT) {
            dfs(name,s_down,count+c_down+1, newVisit,idx+1,RIGHT)
            dfs(name,s_down,count+c_down+1, newVisit, if(idx-1<0) name.length-1 else idx-1, TURN_LEFT)
        }
        else {
            dfs(name,s_down,count+c_down+1, newVisit,idx+1,RIGHT)
            dfs(name,s_down,count+c_down+1, newVisit, if(idx-1<0) name.length-1 else idx-1,LEFT)
        }
    }

    fun moveUp(name:String, str:String, idx:Int) :PJ {
        var tmp = str
        var count = 0
        if(name[idx]==tmp[idx]) return PJ(str,0)
        else {
            while(name[idx] != tmp[idx]) {
                val char = tmp[idx] + 1
                tmp = tmp.replaceRange(idx..idx, "$char")
                count++
            }
        }
        return PJ(tmp, count)
    }

    fun moveDown(name:String, str:String, idx:Int) : PJ {
        var tmp =str
        var count = 0
        if(name[idx] == tmp[idx]) return PJ(str,0)
        else {
            while(name[idx] != tmp[idx]) {
                val char = if(tmp[idx]-1 < 'A') 'Z' else tmp[idx]-1
                tmp = tmp.replaceRange(idx..idx, "$char")
                count++
            }
        }
        return PJ(tmp,count)
    }

    companion object {
        val LEFT = 0
        val RIGHT = 1
        val TURN_LEFT = 2
        val TURN_RIGHT = 3
        val NONE = -1

    }
}

fun main() {
    val solutionJoyStick = SolutionJoyStick()

    //11번
    println(solutionJoyStick.solution("JAN"))
}

class SolutionJoyStickTest2 {
    fun solution(name: String): Int {
        var answer = 0
        var exp = name.length - 1
        for (i in 0 until name.length) {
            val c = name[i]
            answer += if ('Z' - c + 1 > c - 'A') c - 'A' else 'Z' - c + 1
            if (c == 'A') {
                var nextIdx = i + 1
                var countA = 0
                while (nextIdx < name.length && name[nextIdx] == 'A') {
                    countA++
                    nextIdx++
                }
                val tmp = (i - 1) * 2 + (name.length - 1 - i - countA)
                if (exp > tmp) exp = tmp
            }
        }

        answer += exp
        return answer
    }
}
