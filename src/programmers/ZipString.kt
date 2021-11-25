package programmers

class SolutionZipString {

    //lateinit var arr : Array<Int>
    val INF = 123_456_789
    var answer = INF

    fun solution(s: String): Int {

        //arr = Array(s.length){0}
        //STRING LENGTH를 이용.
        //String을 압축.
        //index를 이용. 자른다는 의미는 인덱스를 의미.

        //자른것과 옆에와 같아야 해. count와 자른놈들을 이용해서 갖다 붙이자 새로운 단어에,그리고 길이를 재서 min에 기록하자.
        //반복문의 끝은 string의 길이가 된다

        s.zip()
        //arr.sort()
        //answer = arr[0]
        return answer
    }

    fun String.zip() {

        //for문의 i는 string을 자르는 단위
        //whil문은 i단위로 반복할 횟 수
        for(i in 0..(this.length-1) )  {
            var words = this
            var tmp = ""
            var comp1 = ""
            var comp2 = ""
            var count = 1

            comp1 = words.substring(0..i)
            words = words.removeRange(0..i)
            tmp += comp1

            while(!words.isEmpty()) {
                try {
                    comp2 = words.substring(0..i)
                    words = words.removeRange(0..i)
                } catch (e : StringIndexOutOfBoundsException) {
                    comp2 = words.substring(0..words.length-1)
                    words = words.removeRange(0..words.length-1)
                }

                if(comp1 == comp2) count++
                else {
                    if(count>1) tmp = count.toString() + tmp
                    tmp += comp2
                    count = 1
                }

                comp1 = comp2
            }

            if(count > 1) tmp = count.toString() + tmp

            answer = Math.min(answer, tmp.length)
        }
    }
}


class SolutionZipString2 {

    var Min = 123_456_789

    fun solution(s: String): Int {
        var answer = 0
        //s의 길이
        var len = s.length

        //단어를 idx씩 잘라가면서 이동.
        //전체 string에서 해당 idx만큼 잘라낸다. 자른 단어는 result에 저장
        //다음 단어와 자른 단어와 비교 -> 같으면 count증가, 그리고 잘라냄 , 다르면 비교 단어가 다음 단어로 바뀜 그리고 잘라내면서 result에 붙인다.
        //while문에서는 i보다 전체 단어의 길이가 작으면 중지.
        for(i in 0 until len) {
            var result = ""
            var count = 1
            var tmp = s
            var word = tmp.substring(0..i)
            var countStr = ""

            tmp = tmp.drop(i+1)
            result += word

            while(tmp.length > i) {
                val next = tmp.substring(0..i)
                if(word == next) {
                    count++
                    tmp = tmp.drop(i+1)
                } else {
                    word = next
                    countStr = if(count>1) "${count}" else ""
                    result =  countStr + result + word
                    tmp = tmp.drop(i+1)
                    count = 1
                }
            }

            countStr = if(count>1) "${count}" else ""
            result = countStr + result + tmp

            Min = Math.min(Min, result.length)
            //println("$Min, $i, $result")
        }

        answer = Min

        return answer
    }
}



//fold를 이용한 zipString
class SolutionZipString3 {

    val INF = 123_456_789

    fun solution(s: String): Int {
        var answer = INF

        //s의 길이만큼 for문을 돌린다. : 그래야 s를 idx만큼 잘라서 다음 s(by idx)와 비교해볼 수 있다.
        for(idx in 1..s.length) {
            //1.s를 fold로 비교
            var new = ""
            var count = 0
            var cmp = s.substring(0 until idx)

            s.foldIndexed(""){i,acc,c ->
                // i+1 == s.length는 s의 i가 마지막까지 왔을 때 처리하기 위한 조건문. (idx가 남은 인덱스보다 큰 경우에는 문자열을 자르지 못하기에 그 부분을 처리;)
                if((i+1)%idx == 0 || i+1 == s.length) {
                    //tmp는 현재 인덱스의 내용물까지 포함한 acc : 현재 인덱스를 포함하려면 acc에 c를 더해줘야 함.
                    val tmp = acc+c
                    //cmp와 tmp를 비교했을 때, 같다면 count를 증가시킨다. 그리고 acc는 그대로 둔다.
                    //같지 않다면 현재 카운트(toString)과 현재 cmp를 new에 저장하고, cmp를 tmp로 교체, acc는 ""로 초기화, count를 1로 초기화한다.
                    if(cmp == tmp) {
                        count++
                        if(i+1 == s.length) new += cmp + if(count>1) count.toString() else ""

                    } else {
                        //count가 1이라면 줄일 수 없음으로 1은 생략한다.
                        //else 구문 ..와 괄호때문에 틀렷네..
                        new += (if(i+1 == s.length) cmp + tmp else cmp) + if(count>1) count.toString() else ""
                        cmp = tmp
                        count = 1
                    }
                    acc.drop(acc.length)
                } else acc+c
            }

            println("new : $new")
            answer = Math.min(answer, new.length)
        }


        return answer
    }
}

fun main() {
    val solutionZipString = SolutionZipString3()
    val str = "acacacacacacbacacacacacac"

    val ans = solutionZipString.solution(str)

    println(ans)

}

