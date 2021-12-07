package programmers

typealias PK = Pair<Int, Int>


class SolutionKeepDistance {

    //오른쪽, 왼쪽, 아래, 위, 대각(아래,우), 대각(위,좌), 대각(위,우), 대각(아래,좌)
    val check = arrayOf(P(0, 2), P(0, -2), P(2, 0), P(-2, 0), P(1, 1), P(-1, -1), P(-1, 1), P(1, -1))

    //오른쪽, 왼쪽, 아래, 위
    val move = arrayOf(P(0, 1), P(0, -1), P(1, 0), P(-1, 0))
    private lateinit var _place: Array<String>

    fun solution(places: Array<Array<String>>): IntArray {
        var answer: IntArray = intArrayOf()

        for (place in places) {
            var pList = mutableListOf<P>()
            _place = place

            for (i in place.indices)
                for (j in place[i].indices)
                    if (place[i][j] == 'P') pList.add(P(i, j))


            if (pList.size == 0) {
                answer = answer.plus(1)
                continue
            }

            var flag = false
            for (p in pList) {
                //1의 거리에 P가 있을 경우. -> 파티션 상관없이 무조건 거리두기 실패
                for (m in move) {
                    val next = p.plus(m)
                    if (isInRoom(next) && pList.indexOf(next) > -1) {
                        answer = answer.plus(0)
                        flag = true
                        break
                    }
                }
                if (flag) break

                for ((i, c) in check.withIndex()) {
                    val next = p.plus(c)
                    //2의 거리에 P가 있을 경우. -> Parition 있는지 확인
                    if (isInRoom(next) && pList.indexOf(next) > -1)
                        if (!isDist(i, p)) {
                            answer = answer.plus(0)
                            flag = true
                            break
                        }
                }
                if (flag) break
            }
            if (!flag) answer = answer.plus(1)
        }
        return answer
    }

    //벗어나면 false
    fun isInRoom(p: P): Boolean = !(p.first < 0 || p.second < 0 || p.first >= 5 || p.second >= 5)

    //파티션이 있으면 true
    fun isPartition(p: P): Boolean = (_place[p.first][p.second] == 'X')

    //오른쪽, 왼쪽, 아래, 위, 대각(오른쪽,아래), 대각(왼쪽,위), 대각(오른쪽,위), 대각(왼쪽,아래)
    //오른쪽, 왼쪽, 아래, 위
    fun isDist(idx: Int, p: P): Boolean {
        return when (idx) {
            0 -> isPartition(p.plus(move[0]))
            1 -> isPartition(p.plus(move[1]))
            2 -> isPartition(p.plus(move[2]))
            3 -> isPartition(p.plus(move[3]))
            4 -> (isPartition(p.plus(move[0])) && isPartition(p.plus(move[2])))
            5 -> (isPartition(p.plus(move[1])) && isPartition(p.plus(move[3])))
            6 -> (isPartition(p.plus(move[0])) && isPartition(p.plus(move[3])))
            else -> (isPartition(p.plus(move[1])) && isPartition(p.plus(move[2])))
        }
    }

    fun P.plus(_p: P): P = P(this.first + _p.first, this.second + _p.second)
}


class SolutionKeepDistance2 {
    fun solution(places: Array<Array<String>>): IntArray {
        var answer = IntArray(places.size)
        for (placeNum in places.indices) {
            var place = places[placeNum]
            var flag = true
            for (i in place.indices) {
                for (j in place[0].indices) {
                    if (!flag)
                        break
                    if (place[i][j] == 'P')
                        flag = check(place, i, j, 0, 0)
                }
                if (!flag)
                    break
            }
            answer[placeNum] = if (flag) 1 else 0
        }
        return answer
    }

    fun check(place: Array<String>, i: Int, j: Int, skip: Int, depth: Int): Boolean {
        //아래 확인 1
        var result = true
        if (result && skip != 1 && i < place.lastIndex)
            when (place[i + 1][j]) {
                'P' -> return false
                'O' -> if (depth == 0) result = check(place, i + 1, j, 0, 1)
            }
        //왼쪽 확인 2
        if (result && skip != 2 && j > 0)
            when (place[i][j - 1]) {
                'P' -> return false
                'O' -> if (depth == 0) result = check(place, i, j - 1, 3, 1)
            }
        //오른쪽 확인 3
        if (result && skip != 3 && j < place[0].lastIndex)
            when (place[i][j + 1]) {
                'P' -> return false
                'O' -> if (depth == 0) result = check(place, i, j + 1, 2, 1)
            }
        //전부 아닐시
        return result
    }
}