package programmers

import java.util.*

class SolutionChangeBracket {
    val leftBra = '('
    val rightBra = ')'



    fun solution(p: String): String {
        var answer = ""
        answer = divideBalancedStr(p)
        return answer
    }

    fun String.checkRightStr() : Boolean {
        if(this.length < 1) return true
        if(this[0] == rightBra) return false

        val stack :Stack<Char> = Stack()
        for(i in 0..this.length-1) {
            val bra = this[i]
            if(bra == leftBra) {
                stack.push(bra)
            } else {
                if(stack.isEmpty()) return false
                stack.pop()
            }
        }

        return if(stack.isEmpty()) true else false
    }

    fun String.checkBalancedStr() : Boolean {
        var leftCount = 0
        var rightCount = 0
        this.map{ if(it == leftBra) leftCount++ else rightCount++}

        return if(leftCount==rightCount) true else false
    }

    fun String.findFirstBalanced() : Int {
        if(this.length < 1) return 0

        var leftCount = 0
        var rightCount = 0
        for((index,v) in this.withIndex()) {

            if(v == leftBra) leftCount++
            else rightCount ++

            if(leftCount == rightCount) return index
        }

        return -1
    }



    fun divideBalancedStr(p: String) : String {
        if(p.checkRightStr()) {
            return p
        }

        var bra = ""
        var u = ""
        var v = ""

        val divIndex = p.findFirstBalanced()

        u = p.substring(0..divIndex)
        v = p.substring(divIndex+1..p.length-1)

        if(u.checkRightStr()) {
            bra += u
            bra += divideBalancedStr(v)
        } else {
            bra += leftBra + divideBalancedStr(v) + rightBra
            u = u.drop(1).dropLast(1)

            u.map{if(it==leftBra) bra+=rightBra else bra+=leftBra}
        }

        return bra
    }
}

fun main() {
    val solution = SolutionChangeBracket()
    val str1 = "(()())()"
    val str2 = ")("
    val str3 = "()))((()"
    println(solution.solution(str1))
    println(solution.solution(str2))
    println(solution.solution(str3))
}
