package chap08.section01

class GenereicNull<T> {
    fun EqualityFunc(arg1 :T , arg2 :T) {
        println(arg1?.equals(arg2))
    }
}


//null 허용하지 않음 .T에 타입을 any로 제한하였기 때문
class GenereicNotNull<T : Any> {
    fun EqualityFunc(arg1 :T , arg2 :T) {
        println(arg1.equals(arg2))
    }
}

fun main() {
    val obj = GenereicNull<String>()
    obj.EqualityFunc("Hello", "World")

    val obj2 = GenereicNull<Int?>()
    obj2.EqualityFunc(null, 2)

    val obj3 = GenereicNotNull<String>()
    obj3.EqualityFunc("Hello", "World")

    val obj4 = GenereicNotNull<Int>()
    obj4.EqualityFunc(2, 2)
}
