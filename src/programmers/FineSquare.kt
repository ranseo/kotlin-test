package programmers

class SolutionFindSquare {
    fun getGcd(a:Int, b:Int) : Int {
        if(b==0) return a
        else return getGcd(b,a%b)
    }

//    fun getDiagnoal(n:Int, m:Int) : Int {
//        val gcd = getGcd(if(n>m) n else m, if(n>m) m else n)
//        var isGridPoint = gcd>1
//
//        if(isGridPoint) return (n+m-gcd)
//        else return n+m-1
//    }

    fun solution(w:Int, h:Int): Long {
        var answer = (w.toLong() * h.toLong())
        return answer - ((w+h)-getGcd(w,h))
    }
}

fun main() {
    val solution = SolutionFindSquare()
    println(solution.solution(8,12))
}