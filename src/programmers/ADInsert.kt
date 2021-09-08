package programmers

import java.lang.Math.max
import java.util.*

fun main() {
    val play_time = "99:59:59"
    val adv_time = "25:00:00"
    val logs = arrayOf("69:59:59-89:59:59", "01:00:00-21:00:00", "79:59:59-99:59:59", "11:00:00-31:00:00")
    var answer = 0


    val ad = Array<Long>(360_000){0}
    var queue:Queue<Long> = LinkedList<Long>()


    logs.forEach { log ->
        val start = log.substring(0..7)
        val end = log.substring(9..log.length-1)

        //println("start : $start, end : $end")
        val s = timeToSecond(start)
        val e = timeToSecond(end)

        ad[s] = ad[s]+1
        ad[e] = ad[e]-1
    }

    val adRange  = timeToSecond(adv_time)
    val totalIndex  = timeToSecond(play_time)

    if(adRange == totalIndex) {
        print(secondToTime(answer))
        return
    }

    var sum = 0L


    for(i in 1..totalIndex) {
        ad[i] = ad[i] + ad[i-1]
    }

    for(i in 1..totalIndex) {
        ad[i] = ad[i] + ad[i-1]
    }

    ad.map { print("$it, ") }

    for(i in 0..adRange) {
        sum += ad[i]
        queue.add(ad[i])
    }

    var Max = sum

    for(i in adRange+1 .. totalIndex) {
        sum += ad[i]
        queue.add(ad[i])
        sum -= queue.poll()

        if(sum > Max) {
            answer = i - adRange
            Max = sum
        }

    }

    print(secondToTime(answer))
}

fun timeToSecond(_time: String) : Int {
    var time = _time.replace(":","")
    var second  =0

    second += time.substring(0..1).toInt().times(3600)
    second += time.substring(2..3).toInt().times(60)
    second += time.substring(4..5).toInt()


    return second
}


fun secondToTime(_second: Int) : String {
    var time = ""
    var second = _second


    var hour = second.div(3600)
    if(hour < 10) {
        time += "0"+hour.toString()+":"
    } else time += hour.toString()+":"

    second = second.rem(3600)


    var min = second.div(60)
    if(min < 10) {
        time += "0"+min.toString()+":"
    } else time += min.toString()+":"


    second = second.rem(60)
    if(second < 10) {
        time += "0"+second.toString()
    } else time += second.toString()


    return time
}
