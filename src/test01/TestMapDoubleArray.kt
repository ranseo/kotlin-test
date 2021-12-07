package test01

fun main() {
    val list = listOf(listOf(0,2,3), listOf(3,2,0), listOf(2,4))

    val list2 = listOf(1,2,3,4,5)

    val tmp1 = list2.map { if(it%2 ==0) it+3 else 0 }

    val tmp2 = list.map{  it.map{ v-> if(v!=0) v+1 else v}}.flatten()

    val tmp3 = list.flatMap { it.map{v->if(v!=0) v+1 } }


    while (true) {
        list2.forEach {
            if(it==2)
            println(it)
        }
    }

    println(tmp2)
    println(tmp3)
}