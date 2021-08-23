package chap08.section01

fun <T> find(a: Array<T>, Target: T): Int {
    for(i in a.indices) {
        if(a[i] == Target) return i
    }
    return -1
}

fun main() {
    val arr1: Array<String> = arrayOf("Apple", "Bannana", "PineApple","Durian")
    val arr2: Array<Int> = arrayOf(1,2,3,4)

    println("arr.indices ${arr1.indices}")
    println(find(arr1, "PineApple"))
    println(find(arr2,1))

}

