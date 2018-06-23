package com.bzh.dytt.api

import android.util.Log
import java.util.concurrent.DelayQueue
import java.util.concurrent.Delayed
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


class DelayObject<E : Runnable>(val data: E, delayInMilliseconds: Long) : Delayed {

    private val startTime: Long = System.currentTimeMillis() + delayInMilliseconds

    override fun toString(): String {
        return "DelayObject{" +
                "data='" + data +
                ", startTime=" + startTime / 1000L +
                '}'.toString()
    }

    override fun getDelay(unit: TimeUnit): Long {
        val diff = startTime - System.currentTimeMillis()
        return unit.convert(diff, TimeUnit.MILLISECONDS)
    }

    override fun compareTo(o: Delayed): Int {
        return saturatedCast(this.startTime - (o as DelayObject<*>).startTime)
    }

    companion object {

        internal fun saturatedCast(value: Long): Int {
            if (value > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE
            }
            return if (value < Integer.MIN_VALUE) {
                Integer.MIN_VALUE
            } else value.toInt()
        }
    }
}

class DelayQueueConsumer(private val queue: DelayQueue<DelayObject<Runnable>>) : Runnable {

    override fun run() {
        while (true) {
            try {
                val item: DelayObject<Runnable> = queue.take() as DelayObject<Runnable>
                Log.d("Delay", "DelayQueueConsumer run $item")
                item.data.run()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}

class DelayQueueProducer(private val queue: DelayQueue<DelayObject<Runnable>>) {

    fun produce(runnable: Runnable) {

        val curTime: Long = System.currentTimeMillis() / 1000L

        if (curTime > DelayQueueObject.nextTime) {
            DelayQueueObject.nextTime = curTime
        } else {
            DelayQueueObject.nextTime = DelayQueueObject.nextTime + DelayQueueObject.delayOfEachProducedMessageSeconds
        }

        val delayMill = Math.abs(System.currentTimeMillis() - DelayQueueObject.nextTime * 1000L)

        Log.d("Delay", "DelayQueueProducer produce curTime=$curTime nextTime=${DelayQueueObject.nextTime} delayMill=$delayMill")

        queue.put(DelayObject(runnable, delayMill))
    }
}

class DelayQueueObject {
    companion object {
        val TAG = "Delay"

        const val delayOfEachProducedMessageSeconds = 1L

        val delayQueue: DelayQueue<DelayObject<Runnable>> = DelayQueue()

        val consumer = DelayQueueConsumer(delayQueue)

        val producer = DelayQueueProducer(delayQueue)

        val executor = Executors.newSingleThreadExecutor()

        var elements = AtomicInteger()

        var nextTime = 0L

        init {
            executor.execute(consumer)
        }
    }
}