package com.bzh.dytt.ui.home

import android.arch.lifecycle.*
import com.bzh.dytt.repository.Repository
import com.bzh.dytt.vo.MovieDetail
import com.bzh.dytt.vo.Resource
import com.bzh.dytt.vo.Status
import javax.inject.Inject

class HomeListViewModel @Inject constructor(private val repository: Repository) : ViewModel(), LifecycleObserver, Observer<Resource<List<MovieDetail>>> {

    private var _isRefresh = false

    private var _isLoadMore = false

    private val _movieListLiveData: MutableLiveData<Resource<List<MovieDetail>>> = MutableLiveData()

    private var _movieRepositoryLiveData: LiveData<Resource<List<MovieDetail>>>? = null

    val movieListLiveData: LiveData<Resource<List<MovieDetail>>>
        get() = _movieListLiveData

    val moveTypeLiveData: MutableLiveData<HomeViewModel.HomeMovieType> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun active() {
        register()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun inactive() {
        unregister()
    }

    fun loadMore() {
        if (_isLoadMore) {
            return
        }
        unregister()
    }

    fun refresh() {
        if (_isRefresh) {
            return
        }
        unregister()
        _movieRepositoryLiveData = repository.movieList(moveTypeLiveData.value, 1, true)
        _movieRepositoryLiveData?.observeForever(this)
    }

    private fun register() {
        _movieRepositoryLiveData = repository.movieList(moveTypeLiveData.value, 1)
        _movieRepositoryLiveData?.observeForever(this)
    }

    private fun unregister() {
        _isRefresh = false
        _movieRepositoryLiveData?.removeObserver(this)
    }

    override fun onChanged(result: Resource<List<MovieDetail>>?) {
        when (result?.status) {
            Status.SUCCESS -> {
                _isRefresh = false
                _movieListLiveData.value = result
            }
            Status.ERROR -> {
                _isRefresh = false
                _movieListLiveData.value = result
            }
            else -> {
                _movieListLiveData.value = result
            }
        }
    }

    fun doUpdateMovieDetail(item: MovieDetail) {

    }
}