package test01

fun main() {
    val mList : MutableList<Int> = mutableListOf(100,10,9,12,47,2,1)
    println(mList)
    mList.sort()
    println(mList)

    val list : List<Int> = listOf(100,10,9,12,47,2,1)
    println(list)
    val tmp = list.sorted()
    println(tmp)

    println()
    println()

    val mListR : MutableList<Int> = mutableListOf(100,10,9,12,47,2,1)
    println(mListR)

    //단지 역순으로만
    mListR.reverse()
    println(mListR)

    //정렬 후 역순.
    mListR.sort()
    mListR.reverse()
    println(mListR)


    val listR : List<Int> = listOf(100,10,9,12,47,2,1)
    println(list)
    val tmpR = list.sorted().reversed()
    println(tmpR)




}