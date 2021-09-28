package test01

import java.lang.NumberFormatException
import java.util.*

fun main()  {
    val s = Scanner(System.`in`)

    val name = s.nextLine()
    var age = 0
    val str = s.nextLine()

    val alias = s.next()

        try {
             age = str.toInt()
        } catch (e: Exception) {
            age = 99
        }


    println("$name is $age years old\nand his alias is $alias")

}