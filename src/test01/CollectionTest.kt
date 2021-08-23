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
}