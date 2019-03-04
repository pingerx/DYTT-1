package com.bzh.dytt.ui.detail

import androidx.lifecycle.*
import androidx.databinding.ObservableField
import com.bzh.dytt.repository.DataRepository
import com.bzh.dytt.ui.home.HomeViewModel
import com.bzh.dytt.vo.MovieDetail
import com.bzh.dytt.vo.Resource
import com.bzh.dytt.vo.Status
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel(), LifecycleObserver {

    var movieDetail: MovieDetail? = null

    val homePicUrl = ObservableField<String>()

    val paramsLiveData: MutableLiveData<MovieDetail> = MutableLiveData()

    val detailList: MutableLiveData<List<String>> = MutableLiveData()

    val swipeRefreshStatus: MutableLiveData<Boolean> = MutableLiveData()

    var autoUpdateLiveData: LiveData<Resource<MovieDetail>>? = null

    private val paramsObserver: Observer<MovieDetail> = Observer {
        if (it != null) {
            if (it.isPrefect) {
                movieDetail = it
                homePicUrl.set(it.homePicUrl)
                setDetailList(it)
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

    private fun setDetailList(movieDetail: MovieDetail) {
        when (movieDetail.categoryId) {
            HomeViewModel.HomeMovieType.MOVIE_RIHAN_TV.type -> {
                detailList.value = movieDetail.content?.split("\r\n")?.filter { it.isNotEmpty() }
            }
            else -> {
                detailList.value = movieDetail.content?.split("◎")?.filter { it.isNotEmpty() }
            }
        }
    }

    private val detailObserver = Observer<Resource<MovieDetail>> {
        when (it?.status) {
            Status.SUCCESS -> {
                if (it.data?.isPrefect == true) {
                    movieDetail = it.data
                    setDetailList(it.data)
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