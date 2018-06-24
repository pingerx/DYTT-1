package com.bzh.dytt.ui.detail

import android.arch.lifecycle.*
import com.bzh.dytt.repository.DataRepository
import com.bzh.dytt.vo.MovieDetail
import com.bzh.dytt.vo.Resource
import com.bzh.dytt.vo.Status
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel(), LifecycleObserver {

    private var _itemUpdateRepositoryLiveData: LiveData<Resource<MovieDetail>>? = null

    private val paramsObserver: Observer<MovieDetail> = Observer {
        if (it != null) {
            if (it.isPrefect) {
                movieDetailLiveData.value = it
                refreshLiveData.value = false
            } else {
                refreshLiveData.value = true
                _itemUpdateRepositoryLiveData = dataRepository.movieUpdate(it)
                _itemUpdateRepositoryLiveData?.observeForever(detailObserver)
            }
        } else {
            refreshLiveData.value = false
        }
    }

    private val detailObserver = Observer<Resource<MovieDetail>> {
        when (it?.status) {
            Status.SUCCESS -> {
                if (it.data?.isPrefect == true) {
                    movieDetailLiveData.value = it.data
                }
                refreshLiveData.value = false
            }
            Status.ERROR -> {
                refreshLiveData.value = false
            }
            else -> {
                refreshLiveData.value = false
            }
        }
    }

    val paramsLiveData: MutableLiveData<MovieDetail> = MutableLiveData()

    val movieDetailLiveData: MutableLiveData<MovieDetail> = MutableLiveData()

    val refreshLiveData: MutableLiveData<Boolean> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun active() {
        paramsLiveData.observeForever(paramsObserver)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun inactive() {
        paramsLiveData.removeObserver(paramsObserver)
    }
}