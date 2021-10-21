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


class SolutionChangeBracket2 {
    fun solution(p: String): String {
        var answer = ""
        if(p.length == 0) return answer


        answer = divWord(p)
        return answer
    }

    fun checkBalanced(word: String) : Boolean {
        if(word.length == 0) return false

        var left = 0
        var right = 0
        for(s in word) {

            if(s==leftBra) left++
            else right++
        }
        return if((left == right) && (left > 0 && right > 0)) true else false
    }

    fun checkRightBalanced(word: String) : Boolean {
        if(word.length == 0 || word[0] == rightBra) return false
        val stack = arrayListOf<Char>()
        for(s in word) {
            if(s==leftBra) stack.add(s)
            else {
                if(stack.isEmpty()) return false
                stack.removeAt(0)
            }
        }
        return true
    }

    fun divWord(_word:String) : String {
        var word = _word
        //1.
        if(word.length == 0) return word

        //1_2. 올바른 괄호 문자열이 아닐 경우
        if(!checkRightBalanced(word)) {

            var u = ""
            var v = ""
            //2.
            while(word.length > 0) {
                u += word[0]
                word = word.drop(1)

                if(checkBalanced(u)) {
                    v += word
                    break;
                }
            }

            //3.
            if(checkRightBalanced(u)) {
                u += divWord(v)
                return u
            } else { //4.
                var tmp = "(" + divWord(v) + ")"
                u = u.drop(1)
                u = u.dropLast(1)
                for(s in u) {
                    if(s == leftBra) tmp += rightBra
                    else tmp += leftBra
                }
                return tmp
            }
        } else {
            return word
        }
    }

    companion object {
        val leftBra = '('
        val rightBra = ')'
    }
}