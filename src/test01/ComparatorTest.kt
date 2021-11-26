package test01

data class Test(val data:String, val name: String, val idx: Int=0)

fun main() {
    val test1 = Test("a", "chan", 1)
    val test2 = Test("b","seo",3)



    val list = mutableListOf<Test>(test1, test2)

}

val compTest : Comparator<Test> = Comparator<Test>(){

}