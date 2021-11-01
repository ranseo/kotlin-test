package test01

fun getDiagnoal(n:Int, m:Int) : Int{

    val gcd = getGCD(if(n>m)n else m, if(n>m) m else n)
    var isGridPoint = gcd>1


    //대각선에 의해 잘려지는 사각형의 개수 =
    //격자점이 존재하지 않을 때 = (가로)+(세로)-1
    //격자점이 존재할 때 = (가로)+(세로)-1-(격자점의 개수)
    if(!isGridPoint) {
        return n+m-1
    } else {
        return n+m-1-gcd
    }

}



fun main() {

}