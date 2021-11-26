package test01

import java.util.*

data class Test(val data:String, val name: String, val idx: Int=0)

fun main() {
    val test1 = Test("a", "chan", 1)
    val test2 = Test("b","seo",3)




    val list = mutableListOf<Test>(test1, test2)
    println(list)

    list.sortWith(compTest)

    println(list)

}

object compTest : Comparator<Test> {
    override fun compare(o1: Test?, o2: Test?): Int {

        return -((o1?.idx ?:0) - (o2?.idx ?:0))
    }
}


