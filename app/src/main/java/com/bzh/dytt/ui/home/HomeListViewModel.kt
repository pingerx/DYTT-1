package com.bzh.dytt.ui.home

import androidx.lifecycle.*
import androidx.databinding.ObservableField
import android.util.Log
import com.bzh.dytt.repository.DataRepository
import com.bzh.dytt.vo.MovieDetail
import com.bzh.dytt.vo.Resource
import com.bzh.dytt.vo.Status
import javax.inject.Inject

class HomeListViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel(), LifecycleObserver {

    val resourceEmptyStatus: ObservableField<Boolean> = ObservableField()
    val resourceErrorStatus: ObservableField<Boolean> = ObservableField()
    val resourceMessage: ObservableField<String> = ObservableField()

    private var currentPage = FIRST_PAGE

    val movieListLiveData: MutableLiveData<List<MovieDetail>> = MutableLiveData()

    var refreshLiveData: MutableLiveData<Boolean> = MutableLiveData()

    var isRefresh = false

    var isLoadMore = false

    private var movieRepositoryLiveData: LiveData<Resource<List<MovieDetail>>>? = null

    private var itemUpdateRepositoryLiveData: LiveData<Resource<MovieDetail>>? = null

    private val listObserver = Observer<Resource<List<MovieDetail>>> {
        Log.d(TAG, "ListObserver ${it?.status} ${it?.message} ${it?.data?.size}")

        when (it?.status) {
            Status.SUCCESS -> {
                if (isLoadMore) {
                    currentPage += 1
                }

                isLoadMore = false
                isRefresh = false
                refreshLiveData.value = false

                if (!(it.data == null || it.data.isEmpty())) {
                    val list = it.data.filter { it.id != 22066 }
                    if (list.isNotEmpty()) {
                        movieListLiveData.value = list
                    }
                }

                resourceEmptyStatus.set(it.data == null || it.data.isEmpty())
                resourceMessage.set(it.message)

                it.data?.forEach {
                    Log.d(TAG, "home list model movieDetail = [${it.id} ${it.name}]")
                }
            }
            Status.ERROR -> {
                isLoadMore = false
                isRefresh = false
                refreshLiveData.value = false

                resourceErrorStatus.set(true)
                resourceMessage.set(it.message)
            }
            else -> {
                refreshLiveData.value = true
            }
        }
    }

    val moveTypeLiveData: MutableLiveData<HomeViewModel.HomeMovieType> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        moveTypeLiveData.observeForever {
            refreshLiveData.value = true
            doLoadFirstPage()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        unregister()
    }

    fun doLoadMorePage() {
        Log.d(TAG, "doLoadMorePage isLoadMore=$isLoadMore currentPage=$currentPage")
        if (isLoadMore) {
            return
        }
        unregister()
        isLoadMore = true
        movieRepositoryLiveData = dataRepository.movieList(moveTypeLiveData.value!!, currentPage + 1)
        movieRepositoryLiveData?.observeForever(listObserver)
    }

    fun doRefreshFirstPage() {
        Log.d(TAG, "doRefreshFirstPage isRefresh=$isRefresh")
        if (isRefresh || moveTypeLiveData.value == null) {
            return
        }
        unregister()
        isRefresh = true
        currentPage = FIRST_PAGE
        movieRepositoryLiveData = dataRepository.movieList(moveTypeLiveData.value!!, FIRST_PAGE)
        movieRepositoryLiveData?.observeForever(listObserver)
    }

    private fun doLoadFirstPage() {
        Log.d(TAG, "doLoadFirstPage $this")
        currentPage = FIRST_PAGE
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
            itemUpdateRepositoryLiveData?.observeForever { }
        }
    }

    fun doRemoveUpdateMovieDetail(item: MovieDetail) {
        dataRepository.removeMovieUpdate(item)
    }

    companion object {
        const val TAG = "HomeListViewModel"
        var FIRST_PAGE = 1

    }
}


