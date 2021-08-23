package test01

import java.util.*

fun main() {
    var str1: String = "HI"
    var str2 = "hi"

    var str3 = "Hi"
    var str4 = "hI"
    val strList = listOf<String>(str1, str2, str3, str4)
    for(i in strList) {
        println("$i")
        checkUpperAndLowerCase(i)
        println()
    }

    println("Change Str ToUpperCase")
    for(i in strList) {
        changeToUpperCase(i)
    }

    println("Change Str ToLowerCase")
    for(i in strList) {
        changeToLowerCase(i)
    }


}

fun checkUpperAndLowerCase(str : String) {
    val strLength = str.length
    var upperNum = 0
    for(i in str) {
        if(i.isUpperCase()) upperNum++
    }

    if(strLength == upperNum) println("$str is UpperCase")
    else if(0 == upperNum) println("$str is LowerCase")
    else println("$str is mixedCase")
}

fun changeToUpperCase(str : String) {
    print("$str is changed to ")
    println( str.uppercase(Locale.getDefault()))
}

fun changeToLowerCase(str : String) {
    print("$str is changed to ")
    println(str.lowercase(Locale.getDefault()))
}
