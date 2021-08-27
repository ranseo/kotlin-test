package test01

fun main() {
    var str1 = ""
    if(str1.length == 0) str1 = "asd"

    var str3 = "......a..b..c"

    //str3 = str3.replace(Regex("[.]+"), ".")
    str3 = str3.replace("..", ".")


   //str1 = str1.filterNot{it == '.'}



    println(str3)


    var str4 = ".asdasdbasd......"

    str4 = str4.filterIndexed{i,v -> (i != str4.length-1) && (v!='.')}


    println(str4)



    var str5 = "abcdefghijklm..p"

    var str6 = "abcde"

    println("str6 = $str6")

    str5 = str5.substring(0..14)
    str5 = str5.filterIndexed{i,v -> (i != str5.lastIndex) || (v != '.')}

    println(str5)

    str1 = str1.filter { it == '.' || it == '_' || it == '-' || (it >= '0' && it <= '9') || (it >= 'a' && it <= 'z')}

    println(str1)

    println(str1.indexOf("..",0,false))
    str1.indexOf("..",0,false)

    while(str1.indexOf("..")>-1) {
        str1 = str1.replace("..", ".")
    }

    println(str1)

    while(str1[0]=="."[0]) {
        str1 = str1.removePrefix(".")
    }
    while(str1[str1.length-1] == "."[0]) {
        str1 = str1.removeSuffix(".")
    }

    println(str1)

    var str2 = ""

    if(str2.length == 0) str2 = "a"

    str1 = "abcdefghijklmn.p"
    println(str1.length)
    if(str1.length >= 16) str1 = str1.removeRange(15..str1.length-1)
    while(str1[str1.length-1] == "."[0]) str1 = str1.removeSuffix(".")
    println(str1)

    class Solution {
        fun solution(new_id: String): String {
            return new_id
                .stageOne()
                .stageTwo()
                .stageThree()
                .stageFour()
                .stageFive()
                .stageSix()
                .stageSeven()
        }

        fun String.stageOne() = this.toLowerCase()
        fun String.stageTwo() = this.filter { it.isLetterOrDigit().or(it == '.').or(it == '_').or(it == '-') }
        fun String.stageThree() = this.replace(Regex("[.]+"), ".")
        fun String.stageFour() = this.filterIndexed { index, c -> (index != 0 || c != '.') && (index != this.lastIndex || c != '.') }
        fun String.stageFive() = if (this == "") this + "a" else this
        fun String.stageSix() = if (this.length >= 16) this.substring(0..14).filterIndexed { index, c -> !(index == 14 && c == '.')} else this
        fun String.stageSeven() = if (this.length <= 2) this + this.last().toString().repeat(3 - this.length) else this
    }

}