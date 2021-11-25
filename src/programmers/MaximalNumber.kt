package programmers

class SolutionMaximalNumber {
    fun solution(numbers: IntArray): String {

        val comparator = Comparator<Int> { i1, i2 ->
            val a = "$i1" + "$i2"
            val b = "$i2" + "$i1"
            if (a > b) -1
            else if (a == b) 0
            else 1
        }

        val tmp = numbers.sortedWith(comparator)
        var answer = tmp.joinToString("")

        return if (answer[0] == '0') "0" else answer
    }


}

fun main() {
    val solutionMaximalNumber = SolutionMaximalNumber()
    val numbers = intArrayOf(6,10,2)
    println(solutionMaximalNumber.solution(numbers))
}