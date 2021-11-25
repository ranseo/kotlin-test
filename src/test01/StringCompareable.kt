package test01


fun main() {
    var strList = listOf("1","2","34","95","12","23")

    var intList = listOf(1,2,34,95,12,23)


    val comparator = Comparator<String> { o1, o2 ->
        val a = o1+o2
        val b = o2+o1

        if(a>=b) -1
        else 1
    }

    val comparator2 = Comparator<Int> { o1, o2 ->
        val a = "$o1"+"$o2"
        val b = "$o2"+"$o1"

        if(a>=b) -1
        else 1
    }

    strList = strList.sortedWith(comparator)
    println(strList)

    intList = intList.sortedWith(comparator2)
    println(intList)

    println(intList.joinToString(""))


}


fun String.Compare(a:String) : String{
    return  if(this > a) this
    else a
}

