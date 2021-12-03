package test01

fun main() {
    val str = "ZBCDEF"

    var str2 = "AAAAAA"

    if(str[0] ==str2[0]) println("${str2}")

    str2 = str2.replaceRange(0..0, "A")

    while(str[0] != str2[0]) {

        str2 = str2.replaceRange(0..0, "${str2[0]+1}")
        println("${str[0]}, ${str2[0]}")
    }
}