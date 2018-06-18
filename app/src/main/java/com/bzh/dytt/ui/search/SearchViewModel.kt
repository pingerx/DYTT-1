package com.bzh.dytt.ui.search

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import com.bzh.dytt.repository.DataRepository
import com.bzh.dytt.vo.MovieDetail
import com.bzh.dytt.vo.Resource
import com.bzh.dytt.vo.Status
import java.util.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel(), LifecycleObserver {

    private val movieListLiveData: MutableLiveData<Resource<List<MovieDetail>>> = MutableLiveData()

    private var searchLiveData: LiveData<Resource<List<MovieDetail>>>? = null

    private var itemUpdateRepositoryLiveData: LiveData<Resource<MovieDetail>>? = null

    private var query: String? = null

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
        when (it?.status) {
            Status.SUCCESS -> {
                movieListLiveData.value = it
            }
            Status.ERROR -> {
                movieListLiveData.value = it
            }
            else -> {
                movieListLiveData.value = it
            }
        }
    }

    val listLiveData: LiveData<Resource<List<MovieDetail>>>
        get() = movieListLiveData

    fun doUpdateMovieDetail(item: MovieDetail) {
        if (!item.isPrefect) {
            itemUpdateRepositoryLiveData = dataRepository.movieItemUpdate(item)
            itemUpdateRepositoryLiveData?.observeForever(detailObserver)
        }
    }

    fun setQuery(originalInput: String) {
        if (TextUtils.equals(originalInput, query)) {
            return
        }
        query = originalInput
        val input = originalInput.toLowerCase(Locale.CHINESE).trim()
        searchLiveData = dataRepository.search(input)
        searchLiveData?.observeForever(listObserver)
    }

    companion object {
        const val TAG = "SearchViewModel"
    }
}