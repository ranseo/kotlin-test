package test01

class SolutionMultipleMatrix {
    fun solution(arr1: Array<IntArray>, arr2: Array<IntArray>) : Array<IntArray> {
        //첫번째 행렬의 행 사이즈
        val width = arr1.size
        //두번째 행렬의 열 사이즈
        val height = arr2[0].size
        //곱하기를 위한 첫번째 행열의 열 사이즈 또는 두번째 행열의 행 사이즈 필요
        val mutilSize = arr2.size // or arr1[0].size

        //새롭게 만들어질 행열 사이즈
        val new = Array(width){IntArray(height){0}}

        //첫번째 행렬의 열 사이즈와 두번째 행렬의 행 사이즈가 같아야 함.
        for(i in 0 until width) {
            for(j in 0 until height) {
                for(k in 0 until mutilSize) {
                    new[i][j] = arr1[i][k]*arr2[k][j]
                }
            }
        }



        return new

    }
}

fun main() {

}