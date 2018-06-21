package com.bzh.dytt.api

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import android.util.Log
import com.bzh.dytt.AppExecutors
import com.bzh.dytt.vo.Resource
import java.util.concurrent.DelayQueue
import java.util.concurrent.Delayed
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class DelayObject<E : Runnable>(val data: E, delayInMilliseconds: Long) : Delayed {

    private val startTime: Long = System.currentTimeMillis() + delayInMilliseconds

    override fun toString(): String {
        return "DelayObject{" +
                "data='" + data + '\''.toString() +
                ", startTime=" + startTime +
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

class DelayQueueProducer(private val queue: DelayQueue<DelayObject<Runnable>>, private val delayInMilliseconds: Long) {

    fun produce(runnable: Runnable, index: Int) {
        Log.d("Delay", "DelayQueueProducer produce $index")
        queue.put(DelayObject(runnable, delayInMilliseconds * index))
    }
}

@MainThread
abstract class DelayNetworkBoundResource<ResultType, RequestType> constructor(private val appExecutors: AppExecutors) {

    companion object {
        val TAG = "Delay"

        const val delayOfEachProducedMessageMilliseconds = 1000L

        val delayQueue: DelayQueue<DelayObject<Runnable>> = DelayQueue()

        val consumer = DelayQueueConsumer(delayQueue)

        val producer = DelayQueueProducer(delayQueue, delayOfEachProducedMessageMilliseconds)

        val executor = Executors.newSingleThreadExecutor()

        var elements = AtomicInteger()

        init {
            executor.execute(consumer)
        }
    }

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {

        elements.incrementAndGet()

        producer.produce(Runnable {

            Log.d(TAG, "init producer task")

            appExecutors.mainThread().execute {

                Log.d(TAG, "execute task")
                result.value = Resource.loading(null)
                val dbSource = loadFromDb()
                result.addSource(dbSource) { data ->

                    result.removeSource(dbSource)

                    if (shouldFetch(data)) {
                        fetchFromNetwork(dbSource)
                    } else {
                        result.addSource(dbSource) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }

                    elements.decrementAndGet()
                }
            }
        }, elements.get() - 1)
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                        appExecutors.mainThread().execute {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
