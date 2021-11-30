package programmers
import java.util.*


class SolutionPrinter {

    val answer = Array<Int>(101){0}
    fun solution(priorities: IntArray, location: Int): Int {

        //fir = idx, sec = prio
        return mutableListOf<P>().initWaitList(priorities).printDoc(location)
    }

    fun MutableList<P>.initWaitList(priorities:IntArray) : MutableList<P>{
        priorities.forEachIndexed{ idx, p ->
            this.add(P(idx,p))
        }

        return this
    }

    //문서 꺼내는 함수.
    fun MutableList<P>.printDoc(location: Int) : Int {
        var count = 1
        while(!this.isEmpty()) {
            val max = this.maxByOrNull{it.second} ?: break
            if(max.second <= this[0].second) {

                //이 부분 수정 - 업그레이드 : 대기목록에서 빠져나가는 즉시, 곧바로 loc과 대조해서 count를 리턴하면 정답을 반환.
                if(location == this[0].first) return count
                this.removeAt(0)
                count++
            } else {
                this.add(this[0])
                this.removeAt(0)
            }
        }

        return -1
    }
}


//다른 사람 풀이.
class SolutionPrinter2() {
    fun solution(priorities: IntArray, location: Int): Int {
        var printerQueue = ArrayDeque<Pair<Int,Int>>()
        priorities.forEachIndexed{index, i ->
            printerQueue.offer(Pair(index,i))
        }

        var count = 0
        while (!printerQueue.isEmpty()){
            var first = printerQueue.poll()

            if(printerQueue.filter { first.second < it.second }.isNotEmpty()){
                printerQueue.offer(first)
            }else{
                count++
                if(first.first == location) return count
            }
        }
        return 0
    }
}