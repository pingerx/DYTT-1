package com.bzh.dytt.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.bzh.dytt.AppExecutors
import com.bzh.dytt.api.ApiEmptyResponse
import com.bzh.dytt.api.ApiErrorResponse
import com.bzh.dytt.api.ApiResponse
import com.bzh.dytt.api.ApiSuccessResponse
import com.bzh.dytt.vo.Resource


@MainThread
abstract class DelayNetworkBoundResource<ResultType, RequestType> constructor(private val appExecutors: AppExecutors) : Runnable {

    private val result = MediatorLiveData<Resource<ResultType>>()

    override fun run() {
        appExecutors.mainThread().execute {
            result.value = Resource.loading(null)
            val dbSource = loadFromDb()
            result.addSource(dbSource) { data ->

                result.removeSource(dbSource)

                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource)
                } else {
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.success(newData))
                        finish()
                    }
                }
            }
        }
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
                                onFetchSuccess(newData)
                                setValue(Resource.success(newData))
                                finish()
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                            finish()
                        }
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed(response)
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                        finish()
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

    protected open fun onFetchSuccess(data: ResultType?) {

    }

    protected open fun onFetchFailed(response: ApiErrorResponse<RequestType>) {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @MainThread
    protected open fun finish() {
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}
