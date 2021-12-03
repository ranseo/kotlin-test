package test01

fun main() {
    val arr = arrayOf(1,2,3,4)
    println(arr[0])
    arrTest(arr.clone())
    println(arr[0])

}

fun arrTest(arr:Array<Int>) {
    arr[0]++
}