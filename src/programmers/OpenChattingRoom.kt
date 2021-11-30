package programmers

typealias Pstr = Pair<String,String>

class SolutionOpenChattingRoom {


    fun solution(record: Array<String>): Array<String> {
        var answer = arrayOf<String>()
        //[유저 아이디],[입장 or 퇴장] 을 기록하는 mutableList
        val query = mutableListOf< Pstr >()

        //hashMap 필요 <String : 유저 아이디 , String : 닉네임>
        val hashMap = hashMapOf<String, String>()

        record.forEach{ re ->
            val list = re.split(" ")
            hashMap[list[1]] ?: hashMap.put(list[1], list[2])
            when(list[0]) {
                "Enter" -> {
                    query.add(Pstr(list[1], INPUT))
                    hashMap[list[1]] = list[2]
                }

                "Leave" -> {
                    query.add(Pstr(list[1], OUTPUT))
                }

                "Change" -> {
                    hashMap[list[1]] = list[2]
                }
            }
        }

        query.forEach{ q ->
            answer += (hashMap[q.first] + q.second)
        }


        return answer
    }

    companion object{
        val INPUT = "님이 들어왔습니다."
        val OUTPUT = "님이 나갔습니다."
    }
}


class SolutionOpenChattingRoom2 {
    fun solution(record: Array<String>): Array<String> {
        val user = mutableMapOf<String, String>()

        return record
            .map {
                val r = it.split(" ")
                val action = r.first()
                when (action) {
                    "Enter", "Change" -> user += r[1] to r[2]
                }
                r
            }
            .asSequence()
            .filter { it[0] != "Change" }
            .map {
                val nickName = user[it[1]]
                val explanation = when (it[0]) {
                    "Enter" -> "님이 들어왔습니다."
                    "Leave" -> "님이 나갔습니다."
                    else -> throw IllegalArgumentException()
                }
                "$nickName$explanation"
            }
            .toList().toTypedArray()
    }
}