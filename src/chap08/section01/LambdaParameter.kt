package chap08.section01



fun <T> add(a:T, b:T, op: (T,T)->T) : T {
    return op(a,b)
}

//람다식 매개변수를 가독성 있게 바꾸기 위해 typelias를 사용해 다른 이름을 준다.
typealias arithmetic<T> = (T,T) -> T
fun <T> add2(a:T, b:T, op:arithmetic<T>) : T {
    return op(a,b)
}

fun main(){

    val result = add(2,3, {a,b -> a+b})
    val result2 = add(12,13) {a,b -> a+b} //함수에서 마지막 인자가 람다식이면 마지막 람다식을 소괄호 바깥으로 옮길 수 있다.

    //람다식을 변수로 따로 정의해 add()함수의 인자로 넣는다.
    var sumInt: (Int,Int) -> Int = {a,b -> a+b}
    var sumInt2 ={a:Int, b:Int -> a+b}

    println(result)
    println(result2)
    println(add(2,3,sumInt))
    println(add(4,5,sumInt2))


    println(add2(2,3,sumInt))
    println(add2(4,5,sumInt2))
}