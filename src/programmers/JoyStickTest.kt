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

class SolutionJoyStick2 {
    fun solution(name:String) :Int {
        var answer = 0

        //name의 각 철자들이 되려면 'A'로 부터 [위, 아래] 중 어느 방향으로 움직여야 더 가깝게 이동할 수 있는지 체크하고, answer에 더해줌
        for(i in name.indices) {
            val char = name[i]
            //'A'에서 char까지 걸리는 거리 = char - 'A'
            //'A'에서 거꾸로 돌아 'Z'로 간다음 char까지 걸리는 거리 = 'Z' - char + 1 (+1은 'A'에서 'Z'로 한 번 이동한 거리)
            if(char == 'A') continue
            answer += if(char - 'A' > 'Z' - char + 1) 'Z' - char + 1 else char - 'A'
        }

        //만약 단순히 [오른쪽]으로만 이동했다면 이동 거리는 name의 길이가 된다.
        var Min = name.length-1

        // [오른쪽] 으로 이동하다가 '연속되는 A'가 있어서 (낭비되는 동선) [왼쪽]으로 이동했을 시, 이동거리를 구한다.
        var start = 0

        while(start<name.length) {
            var idx = start
            if(name[idx]=='A') {
                while(idx<name.length && name[idx]=='A') idx++

                //start-1 : 'A'를 발견한 위치에서 뒤로 한칸 = 0에서 'A'까지의 거리
                //(start-1)*2 : 0에서 start-1 까지 왔다가, 다시 0으로 돌아간 거리.
                //(name.length-1) - idx : name의 끝 인덱스에서 start지점에서 연속된 'A'문자열의 마지막 인덱스를 뺀다. = 0에서 거꾸로 돌아서 이동한 거리.

                //+ 만약 idx가 name.length와 같다면 - 뒤로 돌아갈 필요가 없다.
                if(idx == name.length) Min = Math.min(Min, start-1)
                else {
                    var tmp = (start-1)*2 + (name.length-1) - (idx-1)
                    Min = Math.min(Min, tmp)
                }

                start = idx
            } else start++
        }


        answer += Min
        return answer
    }
}

fun main() {
    val solutionJoyStick = SolutionJoyStick2()

    //11번
    println(solutionJoyStick.solution("BBAAAA"))
}

class SolutionJoyStickTest {
    fun solution(name: String): Int {
        var answer = 0
        var exp = name.length - 1
        for (i in name.indices) {
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
