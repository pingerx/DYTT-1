package com.bzh.dytt.ui.detail

import android.arch.lifecycle.*
import android.databinding.ObservableField
import com.bzh.dytt.repository.DataRepository
import com.bzh.dytt.vo.MovieDetail
import com.bzh.dytt.vo.Resource
import com.bzh.dytt.vo.Status
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel(), LifecycleObserver {

    val homePicUrl = ObservableField<String>()

    val paramsLiveData: MutableLiveData<MovieDetail> = MutableLiveData()

    val swipeRefreshStatus: MutableLiveData<Boolean> = MutableLiveData()

    var autoUpdateLiveData: LiveData<Resource<MovieDetail>>? = null

    private val paramsObserver: Observer<MovieDetail> = Observer {
        if (it != null) {
            if (it.isPrefect) {
                homePicUrl.set(it.homePicUrl)
                swipeRefreshStatus.value = false
            } else {
                swipeRefreshStatus.value = true
                autoUpdateLiveData = dataRepository.movieUpdate(it)
                autoUpdateLiveData?.observeForever(detailObserver)
            }
        } else {
            swipeRefreshStatus.value = false
        }
    }

    private val detailObserver = Observer<Resource<MovieDetail>> {
        when (it?.status) {
            Status.SUCCESS -> {
                if (it.data?.isPrefect == true) {
                    homePicUrl.set(it.data.homePicUrl)
                }
                swipeRefreshStatus.value = false
            }
            else -> {
                swipeRefreshStatus.value = false
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun active() {
        paramsLiveData.observeForever(paramsObserver)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun inactive() {
        paramsLiveData.removeObserver(paramsObserver)
    }
}