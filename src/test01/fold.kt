package test01

fun main() {



    var str = "123456789"

    val tmp = str.foldIndexed(""){i,acc,c ->
        //cmp를
        if((i+1)%3==0) {
            val tmp = acc+c
            println(" acc before i%2 : $tmp")
            acc.drop(acc.length)
        }
        else {
            println("acc before else : $acc")
            acc+c
        }
    }


    println(tmp)

    val number = listOf(1,2,3,4,5)

    val relation = arrayOf("a", "b", "c", "d", "e")

    val new = number.fold(""){acc,i -> acc + relation[i-1]}

    println(new)


    println(number.fold(0){ total, i -> total+i})

    println(number.fold(0) {acc,i -> if(acc > i) i else acc})




}