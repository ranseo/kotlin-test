package programmers

class SolutionFindLyrics {
    fun solution(words: Array<String>, queries: Array<String>): List<Int> {

        var answer = mutableListOf<Int>()
        //arr은 word를 length별로 구분하여 저장하기 위한 배열
        var arr = Array(10_000 + 1) { mutableSetOf<String>() }

        //words를 각 word의 length로 구분. -> array<MutableList<String>>[idx] 배열에 추가.
        words.map {
            val idx = it.length
            arr[idx].add(it)
        }
        //queries의 원소의 length를 idx로 해당 단어들의 배열을 탐색.
        queries.map { q ->
            var list = listOf<String>()
            var cnt = 0
            val idx = q.length
            var pre = false
            if (q[0] == '?') pre = true // else suffix = true

            val tmp = q.replace(Regex("[?]+"), "")
            val len = tmp.length


            if(!pre) {
                list = arr[idx].groupBy { it.startsWith(tmp)}[true] ?: listOf()
            } else {
                list = arr[idx].groupBy { it.endsWith(tmp)}[true] ?: listOf()
            }

            cnt = list.size

            answer.add(cnt)
        }

        return answer
    }
}

fun main() {
    val solutionFindLyrics = SolutionFindLyrics()
    val words = arrayOf("frodo", "front", "frost", "frozen", "frame", "kakao")
    val queries = arrayOf("fro??", "????o", "fr???", "fro???", "pro?")

    println(solutionFindLyrics.solution(words, queries))

}