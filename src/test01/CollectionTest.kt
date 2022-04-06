package test01

import kotlin.math.abs
import kotlin.math.absoluteValue

fun List<String>.getShortWordsTo(shortWords: MutableList<String>, maxLength: Int) {
    this.filterTo(shortWords) { it.length <= maxLength}
    println(this[1])
    val articles = setOf("a", "A", "an", "An", "the", "The")
    shortWords -= articles


}





fun main() {

    val words = "A long time ago in a galaxy far far away".split(" ")
    val shortWords = mutableListOf<String>()
   //val articles = listOf("a", "A", "an", "An", "the", "The")
    words.getShortWordsTo(shortWords, 3)
    println(shortWords)
    //println(words-articles)

    val list = arrayListOf<Int>(0,1,2,3)

    list.drop(1)

    list.remove(0)
    for (i in list) {
        print(i)

    }
    println()

    val longWord = mutableListOf<Int>(1,23,3,4,5,)
    longWord.removeAt(0)

    println(longWord)

    longWord.filterIndexed{ i,v ->
        v==0
    }

    val tmp = intArrayOf(1,23,4)
    val l =tmp.sorted()
    l.forEach{
        println(it)
    }
    tmp.forEach {
        println(it)
    }

    val tmp3 = mutableSetOf<Pair<Int,Int>>()

    tmp3.add(Pair(2,3))

    tmp3.size
    var tmp4 = Array<IntArray>(3){ intArrayOf()}

    tmp4[0] = intArrayOf(3,2,4)


    var tmp6 = arrayOf(intArrayOf())




    val abc = 4
    abc.absoluteValue



    val tmp5 = Array<Array<Int>>(5){Array(5){0}}

    tmp4[0] = tmp5[0].toIntArray()

    tmp4[0].forEach {
        print(it)
    }


    for(i in 0..4) {
        tmp6 += tmp5[i]
    }








    val edges = Array<IntArray>(5){ intArrayOf()}




    longWord.toIntArray()

    longWord.map{
        if(it==3)
        println(it)
    }

    println("asdasd")


    val arrayLis = arrayListOf<List<Int>>()

    val a = mutableListOf<Int>()

    arrayLis.add(a)

    val b = arrayLis.first()


    val arrayLis2 = arrayListOf<MutableList<Int>>()

    val c = listOf<Int>()

    arrayLis2.add(c.toMutableList())


    val list100 = listOf(1,4,3).sorted()

    println(list100)

}
