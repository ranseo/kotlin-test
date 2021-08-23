package chap08.section01

class Car(val modelVer: Int) : Comparable<Car> {

    override fun compareTo(other: Car): Int {
        if(this.modelVer > other.modelVer) return 1
        else if(this.modelVer < other.modelVer) return -1
        else return 0
    }
}

fun <T> findGreater(a:T, b:T) : T where T:Number, T:Comparable<T> {
    return if(a > b) a else b
}

fun main() {
    val car1  = Car(1)
    val car2  = Car(2)

    //println(findGreater(car1,car2).modelVer) //where는 모두 포함하는 경우이기 때문에T: Number를 없애고 T: Comparable<T> 만 남기면 해결.
    println(findGreater(car1.compareTo(car2),car2.compareTo(car1)))

    if(car1 > car2) println("car1 model Version : ${car1.modelVer}") else println("car2 model Version : ${car2.modelVer}")

    val nothing: Nothing

}


