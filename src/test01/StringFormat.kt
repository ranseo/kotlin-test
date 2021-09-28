package test01

fun main() {
    val result = 37624
    println(String.format("%02d:%02d:%02d",result/3600, (result%3600)/60, ((result%3600)%60)))


}
