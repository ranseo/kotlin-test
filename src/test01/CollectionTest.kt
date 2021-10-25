package test01

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

    longWord.map{
        if(it==3)
        println(it)
    }

    println("asdasd")


}
