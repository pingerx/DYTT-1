package com.bzh.dytt.ui.home

import android.arch.lifecycle.*
import android.util.Log
import com.bzh.dytt.repository.DataRepository
import com.bzh.dytt.vo.MovieDetail
import com.bzh.dytt.vo.Resource
import com.bzh.dytt.vo.Status
import javax.inject.Inject

class HomeListViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel(), LifecycleObserver {

    private var isRefresh = false

    private var isLoadMore = false

    val movieListLiveData: MutableLiveData<Resource<List<MovieDetail>>> = MutableLiveData()

    private var movieRepositoryLiveData: LiveData<Resource<List<MovieDetail>>>? = null

    private var itemUpdateRepositoryLiveData: LiveData<Resource<MovieDetail>>? = null

    var refresLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val detailObserver = Observer<Resource<MovieDetail>> {
        when (it?.status) {
            Status.SUCCESS -> {
            }
            Status.ERROR -> {
            }
            else -> {
            }
        }
    }

    private val listObserver = Observer<Resource<List<MovieDetail>>> {
        Log.d(TAG, "ListObserver ${it?.status} ${it?.message} ${it?.data?.size}")
        when (it?.status) {
            Status.SUCCESS -> {
                if (isLoadMore) {
                    CURRENT_PAGE += 1
                }
                isLoadMore = false
                isRefresh = false
                movieListLiveData.value = it

                it.data?.forEach {
                    Log.d(TAG, "home list model viewModel ${it.name}")
                }
            }
            Status.ERROR -> {
                isLoadMore = false
                isRefresh = false
                movieListLiveData.value = it
            }
            else -> {
                movieListLiveData.value = it
            }
        }
    }

    val moveTypeLiveData: MutableLiveData<HomeViewModel.HomeMovieType> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun active() {
        refresLiveData.value = true
        doLoadFirstPage()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun inactive() {
        unregister()
    }

    fun doLoadMorePage() {
        Log.d(TAG, "doLoadMorePage isLoadMore=$isLoadMore CURRENT_PAGE=$CURRENT_PAGE")
        if (isLoadMore) {
            return
        }
        unregister()
        isLoadMore = true
        movieRepositoryLiveData = dataRepository.movieList(moveTypeLiveData.value!!, CURRENT_PAGE + 1)
        movieRepositoryLiveData?.observeForever(listObserver)
    }

    fun doRefreshFirstPage() {
        Log.d(TAG, "doRefreshFirstPage isRefresh=$isRefresh")
        if (isRefresh || moveTypeLiveData.value == null) {
            return
        }
        unregister()
        isRefresh = true
        CURRENT_PAGE = FIRST_PAGE
        movieRepositoryLiveData = dataRepository.movieList(moveTypeLiveData.value!!, FIRST_PAGE)
        movieRepositoryLiveData?.observeForever(listObserver)
    }

    private fun doLoadFirstPage() {
        Log.d(TAG, "doLoadFirstPage $this")
        CURRENT_PAGE = FIRST_PAGE
        movieRepositoryLiveData = dataRepository.movieList(moveTypeLiveData.value!!, FIRST_PAGE)
        movieRepositoryLiveData?.observeForever(listObserver)
    }

    private fun unregister() {
        isRefresh = false
        isLoadMore = false
        movieRepositoryLiveData?.removeObserver(listObserver)
    }

    fun doUpdateMovieDetail(item: MovieDetail) {
        if (!item.isPrefect) {
            itemUpdateRepositoryLiveData = dataRepository.movieUpdate(item)
            itemUpdateRepositoryLiveData?.observeForever(detailObserver)
        }
    }

    fun doRemoveUpdateMovieDetail(item: MovieDetail) {
        dataRepository.removeMovieUpdate(item)
    }

    companion object {
        const val TAG = "HomeListViewModel"
        var FIRST_PAGE = 1
        var CURRENT_PAGE = FIRST_PAGE
    }
}


