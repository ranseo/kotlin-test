package chap08.section01

interface Interface1 {
    fun output() : Int
}

class A : Interface1 {
    private var a : Int

    constructor(_a:Int) {
        this.a=_a
    }

    fun input(_a: Int) {
        this.a = _a
    }

    override fun output(): Int {
        return a
    }

}

class Calc<T: Number> {
    fun plus(arg1: T, arg2: T) : Double {
        return arg1.toDouble()+ arg2.toDouble()
    }
}

fun main() {
    val calc = Calc<Int>()
    println(calc.plus(10,20))

    val calc2 = Calc<Double>()
    val calc3 = Calc<Long>()
    //val calcString = Calc<String>() //제한된 자료형(현재 Number로 설정) 으로 인해 오류

    println(calc2.plus(2.5,3.5))
    println(calc3.plus(5L, 10L))


    val InterFace : Interface1 = A(3)
    println(InterFace.output())

}