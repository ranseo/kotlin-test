package programmers

import kotlin.math.ceil

class SolutionDevelopFunc() {
    fun solution(progresses: IntArray, speeds: IntArray): IntArray {
        var answer = intArrayOf()

        //val (Math.ceil(100-progresses[i].toFloat()/speeds.toFloat())).toInt()
        val release = mutableListOf<Int>()
        val isRelease = Array<Boolean>(101) { false }


        for (i in progresses.indices) {
            if (isRelease[i]) continue
            var cnt = 1
            isRelease[i] = true

            var leftDay = computeLeftDay(progresses[i], speeds[i])
            //println(leftDay)
            var idx = i + 1
            while (idx < progresses.size && leftDay >= computeLeftDay(progresses[idx], speeds[idx])) {
                isRelease[idx] = true
                cnt++
                idx++
            }

            release.add(cnt)
        }

        return release.toIntArray()
    }

    fun computeLeftDay(p: Int, speed: Int): Int {
        val day = (Math.ceil((100 - p).toDouble() / speed.toDouble())).toInt()
        return if (day == 0) 1 else day
    }
}


class SolutionDevelopFunc2 {
    fun solution(progresses: IntArray, speeds: IntArray): IntArray {
        var answer = intArrayOf()

        var lastDay = 0
        var cnt = 0
        progresses
            .mapIndexed {idx, progress -> Pair(progress, speeds[idx].toDouble())}
            .map { (100 - it.first) / it.second }
            .map { ceil(it) }
            .map { it.toInt() }
            .asSequence()
            .forEach { curDay ->
                if (lastDay >= curDay) {
                    cnt++
                } else {
                    if (lastDay != 0)
                        answer = answer.plus(cnt)
                    lastDay = curDay
                    cnt = 1
                }
            }
        answer = answer.plus(cnt)

        return answer
    }
}
