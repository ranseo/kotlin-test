package programmers

typealias P = Pair<Int,Int>

class SolutionLockAndKeys {
    var flag = false
    val hashKey = hashMapOf<String,Boolean>()
    var keyName = ""
    val direction = arrayOf(P(1,0), P(-1,0), P(0,1), P(0,-1))
    fun solution(key: Array<IntArray>, lock: Array<IntArray>): Boolean {
        var answer = true

        val keys = mutableListOf<P>()
        key.mapIndexed { i,v-> v.mapIndexed { j, elem -> if(elem > 0) keys.add(P(i,j))}  }

        var holl = 0
        lock.map{ holl += it.count{v->v==0}}
        if(holl ==0) return true


        //dfs 실시 . dfs rotate할 수 있도록 실시하자.
        for(i in 0 until 4) {
            keyName = ""
            move(keys,P(0,0))
//            println("move : 현재")
//            println(keyName)
            hashKey.put(keyName, true)
            dfs(keys,lock,lock.size, holl)
            if(flag) return true
            rotate(keys, keys.size)
//            println()
//            println("Rotate!")
//            println()

        }

        return if(flag) true else false
    }

    //키에 해당하는 돌기들이 모두 한칸씩 움직이게 할 수 있는 함수 만들기
    fun move(keys: MutableList<P>, dict: P) {

        keys.mapIndexed { i, key ->
            val i_move = key.first + dict.first
            val j_move = key.second + dict.second
            keys[i] = P(i_move, j_move)


            keyName += "["+i_move.toString() + ", "+ j_move.toString()+"]"

        }
        println("move : $keyName")
    }

    fun backMove(keys: MutableList<P>, dict: P) {
        var backName = ""
        keys.mapIndexed { i, key ->
            val i_move = key.first - dict.first
            val j_move = key.second - dict.second
            keys[i] = P(i_move, j_move)
            backName += "["+i_move.toString() + ", "+ j_move.toString()+"]"
        }
        println("backMove : $backName")
        println()
    }

    //90도 시계방향 회전 함수 만들기
    fun rotate(keys:MutableList<P>, keySize:Int){
        keys.mapIndexed { i, key ->
            val (i_rotate, j_rotate) = key
            keys[i] = P(keySize - j_rotate - 1 ,i_rotate)

            keyName += "["+(keySize - j_rotate - 1).toString() + ", " + i_rotate.toString()+"]"
        }
    }

    //현재 KEY가 LOCK에서 벗어나있는지 확인하는 함수 만들기 - key들 중 하나라도 lock안에 있다면 true리턴
    fun checkInLock(keys: MutableList<P>, lockSize:Int) : Boolean {
        keys.map { key->
            val (i,j) = key
            if( (i > -1 && j > -1) && (i < lockSize && j < lockSize)) return true
        }
        return false
    }

    //키에 해당하는 돌기들이 현재 LOCK의 홈과 일치하는지 확인하는 함수 만들기
    //단, 열쇠의 돌기부분과 자물쇠의 돌기부분이 만나면 안된다.
    fun lockOff(keys:MutableList<P>, lock:Array<IntArray>, lockSize:Int, holl : Int) : Boolean {
        var count = 0
        keys.map{ key ->
            val (i,j) = key
            if((i < 0 || j < 0) || (i >= lockSize || j >= lockSize)) return@map
            if(lock[i][j] == 0) count++ else return false
        }

         return if(count==holl) true else false
    }

    fun dfs(keys: MutableList<P>, lock:Array<IntArray>, lockSize:Int ,holl:Int) {
        keyName = ""
        if(lockOff(keys, lock, lockSize, holl)) {
            flag= true
            return
        }

        for(i in 0 until 4) {
            println("direction : ${direction[i]}")
            move(keys, direction[i])

            if(checkInLock(keys,lockSize)) {
                if(hashKey.containsKey(keyName)) {
                    println("${keyName}은 방문한 자리 입니다.")
                    backMove(keys,direction[i])
                    keyName = ""
                    continue
                }

                hashKey.put(keyName, true)

                println(keyName)
                dfs(keys, lock, lockSize, holl)
                hashKey.remove(keyName)
            } else println("lock안에 key가 존재하지 않습니다.")


            if(flag) return
            backMove(keys, direction[i])
            keyName = ""
        }
    }

}

fun main() {
    val solutionLockAndKey = SolutionLockAndKeys()
    val key = arrayOf(intArrayOf(0,0,0),intArrayOf(1,0,0),intArrayOf(0,1,1))
    val lock = arrayOf(intArrayOf(1,1,1),intArrayOf(1,1,0),intArrayOf(1,0,1))
    println(solutionLockAndKey.solution(key,lock))


}


//typealias P = Pair<Int,Int>

class SolutionLockAndKeys2 {
    val direction = arrayOf(P(1,0), P(-1,0), P(0,1), P(0,-1))
    fun solution(key: Array<IntArray>, lock: Array<IntArray>): Boolean {
        var answer = true

        val keys = mutableListOf<P>()
        key.mapIndexed { i,v-> v.mapIndexed { j, elem -> if(elem > 0) keys.add(P(i,j))}  }

        var holl = 0
        lock.map{ holl += it.count{v->v==0}}
        if(holl ==0) return true

        //dfs 실시 . dfs rotate할 수 있도록 실시하자.
        for(i in 0 until 4) {
            if(scanLocks(keys.toMutableList(), key.size, lock, lock.size, holl)) return true
            rotate(keys, key.size)
        }

        return false
    }

    //키에 해당하는 돌기들이 모두 한칸씩 움직이게 할 수 있는 함수 만들기
    fun move(keys: MutableList<P>, dict: P) {

        keys.mapIndexed { i, key ->
            val i_move = key.first + dict.first
            val j_move = key.second + dict.second
            keys[i] = P(i_move, j_move)
        }
    }

    fun backMove(keys: MutableList<P>, dict: P) {
        keys.mapIndexed { i, key ->
            val i_move = key.first - dict.first
            val j_move = key.second - dict.second
            keys[i] = P(i_move, j_move)
        }
    }

    //90도 시계방향 회전 함수 만들기
    fun rotate(keys:MutableList<P>, keySize:Int){
        keys.mapIndexed { i, key ->
            val (i_rotate, j_rotate) = key
            keys[i] = P(keySize - j_rotate - 1 ,i_rotate)
        }
    }


    //키에 해당하는 돌기들이 현재 LOCK의 홈과 일치하는지 확인하는 함수 만들기
    //단, 열쇠의 돌기부분과 자물쇠의 돌기부분이 만나면 안된다.
    fun lockOff(keys:MutableList<P>, lock:Array<IntArray>, lockSize:Int, holl : Int) : Boolean {
        var count = 0
        keys.map{ key ->
            val (i,j) = key
            if((i < 0 || j < 0) || (i >= lockSize || j >= lockSize)) return@map
            if(lock[i][j] == 0) count++ else return false
        }

        return if(count==holl) true else false
    }

    fun initKeys(keys:MutableList<P>, keySize:Int) {
        keys.mapIndexed{ index, key ->
            val i_init = key.first - (keySize -1)
            val j_init = key.second - (keySize -1)
            keys[index] = P(i_init, j_init)
        }
    }

    fun scanLocks(keys: MutableList<P>, keySize: Int, lock:Array<IntArray>, lockSize:Int, holl: Int) : Boolean {
        initKeys(keys, keySize)
        if(lockOff(keys, lock, lockSize, holl)) return true
        val max = (keySize) + (lockSize)

        for(i in 0 until max) {
            for(j in 0 until max) {
                //위로
                move(keys, direction[2])
                if(lockOff(keys, lock, lockSize, holl)) return true
            }
            //처음으로
            backMove(keys, P(0,max))

            //아래로
            move(keys,direction[0])
            if(lockOff(keys, lock, lockSize, holl)) return true
        }

        return false
    }
}