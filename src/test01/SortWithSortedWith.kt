package test01

fun main() {
    val mList : MutableList<String> = mutableListOf("Oct", "Jan", "Jun", "Sep", "Feb", "Dec")

    //아스키 순서로 정렬
    mList.sort()
    println(mList)
    //[Dec, Feb, Jan, Jun, Oct, Sep]

    //문자열의 맨 마지막 스펠링으로 정렬
    mList.sortWith(compareBy { it.last()})
    println(mList)
    //[Feb, Dec, Jan, Jun, Sep, Oct]


    val list : List<String> = listOf("Oct", "Jan", "Jun", "Sep", "Feb", "Dec")

    //아스키 순서로 정렬
    list.sorted()
    println(list)
    //[Dec, Feb, Jan, Jun, Oct, Sep]

    //문자열의 맨 마지막 스펠링으로 정렬
    list.sortedWith(compareBy { it.last()})
    println(list)
    //[Feb, Dec, Jan, Jun, Sep, Oct]


    //두 개 이상의 조건을 설정할 때!
    val libaray = mutableListOf<Book>(Book("드미트리의 신나는 러시아 모험", 541, 2021),
                                    Book("생각하는 것들의 생각", 11, 1988),
                                    Book("드미트리의 신나는 러시아 모험",500, 2021),
                                    Book("생각하는 것들의 생각", 11, 2020))
    println(libaray)
    //[Book(name=드미트리의 신나는 러시아 모험, num=541, publication=2021), Book(name=생각하는 것들의 생각, num=11, publication=1988),
    // Book(name=드미트리의 신나는 러시아 모험, num=500, publication=2021), Book(name=생각하는 것들의 생각, num=11, publication=2020)]

    libaray.sortWith(compareBy<Book> { it.name}.thenBy{it.num}.thenBy { it.publication })
    println(libaray)
    //[Book(name=드미트리의 신나는 러시아 모험, num=500, publication=2021), Book(name=드미트리의 신나는 러시아 모험, num=541, publication=2021),
    // Book(name=생각하는 것들의 생각, num=11, publication=1988), Book(name=생각하는 것들의 생각, num=11, publication=2020)]

    val pList = mutableListOf(Pair(4,"c"), Pair(2,"b"), Pair(1,"f"))

    println(pList)
    //[(4, c), (2, b), (1, f)]

    pList.sortBy{it.first} //pList.sortWith(compareBy<Pair<Int, String>> { it.first}.thenBy { it.second })
    println(pList)
    //[(1, f), (2, b), (4, c)]

    pList.sortBy{it.second}
    println(pList)
    //[(2, b), (4, c), (1, f)]


}

data class Book(val name : String, val num : Int, val publication: Int)