package com.bzh.dytt.ui.search

import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import android.databinding.ObservableField
import android.util.Log
import com.bzh.dytt.repository.DataRepository
import com.bzh.dytt.vo.MovieDetail
import com.bzh.dytt.vo.Resource
import com.bzh.dytt.vo.Status
import java.util.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel(), LifecycleObserver {

    val resourceEmptyStatus: ObservableField<Boolean> = ObservableField()
    val resourceErrorStatus: ObservableField<Boolean> = ObservableField()
    val resourceMessage: ObservableField<String> = ObservableField()

    var refreshLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val movieListLiveData: MutableLiveData<List<MovieDetail>> = MutableLiveData()

    private var searchLiveData: LiveData<Resource<List<MovieDetail>>>? = null

    private val listObserver = Observer<Resource<List<MovieDetail>>> {
        when (it?.status) {
            Status.SUCCESS -> {
                if (!(it.data == null || it.data.isEmpty())) {
                    val list = it.data.filter { it.id != 22066 }
                    if (list.isNotEmpty()) {
                        movieListLiveData.value = list
                    }
                }
                refreshLiveData.value = false
                resourceEmptyStatus.set(it.data == null || it.data.isEmpty())
                resourceMessage.set("该关键字没有搜到结果，换个试试吧！")
            }
            Status.ERROR -> {
                refreshLiveData.value = false
                resourceErrorStatus.set(true)
                resourceMessage.set("数据异常，稍后重试吧")
            }
            else -> {

            }
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        resourceEmptyStatus.set(true)
        resourceErrorStatus.set(false)
        resourceMessage.set("搜索一下吧")
        refreshLiveData.value = false

        Log.d(TAG, "create() called")
    }


    fun setQuery(originalInput: String) {

        Log.d(TAG, "setQuery() called with: originalInput = [$originalInput]")

        val input = originalInput.toLowerCase(Locale.CHINESE).trim()
        searchLiveData = dataRepository.search(input)
        searchLiveData?.observeForever(listObserver)
        refreshLiveData.value = true
    }

    fun doUpdateMovieDetail(item: MovieDetail) {

        Log.d(TAG, "doUpdateMovieDetail() called with: item = [$item]")

        if (!item.isPrefect) {
            dataRepository.movieUpdate(item).observeForever { }
        }
    }

    fun doRemoveUpdateMovieDetail(item: MovieDetail) {

        Log.d(TAG, "doRemoveUpdateMovieDetail() called with: item = [$item]")

        dataRepository.removeMovieUpdate(item)
    }

    companion object {
        const val TAG = "SearchViewModel"
    }
}