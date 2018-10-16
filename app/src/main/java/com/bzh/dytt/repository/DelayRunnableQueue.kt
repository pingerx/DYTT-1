package com.bzh.dytt.repository

import android.util.Log
import java.util.concurrent.*
import javax.inject.Inject
import javax.inject.Singleton

class DelayObject<F, E : Runnable>(val flag: F, val data: E? = null) : Delayed {

    var startTimeMillis: Long = 0L

    override fun toString(): String {
        return "DelayObject{" +
                "flag=" + flag +
                ", startTime=" + startTimeMillis / 1000L +
                '}'.toString()
    }

    override fun getDelay(unit: TimeUnit): Long {
        val diff = startTimeMillis - System.currentTimeMillis()
        return unit.convert(diff, TimeUnit.MILLISECONDS)
    }

    override fun compareTo(o: Delayed): Int {
        return saturatedCast(this.startTimeMillis - (o as DelayObject<*, *>).startTimeMillis)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DelayObject<*, *>

        if (flag != other.flag) return false

        return true
    }

    override fun hashCode(): Int {
        return flag?.hashCode() ?: 0
    }

    private fun saturatedCast(value: Long): Int {
        if (value > Integer.MAX_VALUE) return Integer.MAX_VALUE

        return if (value < Integer.MIN_VALUE) Integer.MIN_VALUE else value.toInt()
    }
}

@Singleton
class DelayRunnableQueue<F, E : Runnable> @Inject constructor() {

    companion object {
        const val TAG = "DelayRunnableQueue"
    }

    private val delayOfEachProducedMessageMillis: Long = 1000L

    private val delayLinkQueue: ConcurrentLinkedQueue<DelayObject<F, E>> = ConcurrentLinkedQueue()

    private val delayQueue: DelayQueue<DelayObject<F, E>> = DelayQueue()

    private val executor = Executors.newSingleThreadExecutor()!!

    private var lastMills: Long = System.currentTimeMillis()

    init {
        start()
    }

    fun start() {
        executor.execute {
            while (true) {
                try {
                    val item: DelayObject<F, E> = delayQueue.take()
                    item.data?.run()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun addDelay(f: F, runnable: E) {
        val newDelayObject = DelayObject<F, E>(f, runnable)

        if (delayLinkQueue.contains(newDelayObject)) {
            return
        }

        if (delayLinkQueue.size == 0) {
            newDelayObject.startTimeMillis = if (System.currentTimeMillis() > lastMills) System.currentTimeMillis() else lastMills
        } else {
            for (delay in delayLinkQueue.iterator()) {
                newDelayObject.startTimeMillis = delay.startTimeMillis + delayOfEachProducedMessageMillis
            }
        }

        lastMills = newDelayObject.startTimeMillis + delayOfEachProducedMessageMillis

        Log.d(TAG, "addDelay Flag=$f Delay=$newDelayObject ${delayQueue.size} ${delayLinkQueue.size}")

        delayLinkQueue.offer(newDelayObject)
        delayQueue.put(newDelayObject)
    }

    fun removeDelay(f: F) {
        var isFound: Boolean? = null
        val delayObject: DelayObject<F, E> = DelayObject(f)
        for (delay in delayLinkQueue.iterator()) {
            if (delay.flag == f) {
                isFound = true
            }
            if (isFound != null && isFound) {
                delay.startTimeMillis -= delayOfEachProducedMessageMillis
            }
        }
        delayLinkQueue.remove(delayObject)
        delayQueue.remove(delayObject)

        Log.d(TAG, "removeDelay Flag=$f ${delayQueue.size} ${delayLinkQueue.size}")
    }

    fun finishDelay(f: F) {
        val delayObject = DelayObject<F, E>(f)
        delayLinkQueue.remove(delayObject)

        Log.d(TAG, "finishDelay Flag=$f ${delayQueue.size} ${delayLinkQueue.size}")
    }
}
