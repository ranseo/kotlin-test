package test01

fun main() {
    val queue:ArrayList<Int> = arrayListOf()

    queue.add(1)
    queue.add(2)
    queue.add(3)

    println(queue)

    queue.removeAt(0)
    queue.removeFirst()

    println(queue)
}