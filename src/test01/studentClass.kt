package test01

abstract class Student(val age: Int) {

    abstract fun getAge()
    fun getAgeMinus_1(): Int{
        return age-1
    }
    abstract fun getName()

}


fun main() {
    val chan = object : Student(5) {
        override fun getAge() {

        }

        override fun getName() {
            TODO("Not yet implemented")
        }

    }
}