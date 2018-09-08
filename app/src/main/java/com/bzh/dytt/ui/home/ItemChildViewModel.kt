package com.bzh.dytt.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bzh.dytt.vo.MovieDetail

class ItemChildViewModel(val movieDetail: MovieDetail) : ViewModel() {

    val clickObserver: MutableLiveData<MovieDetail> = MutableLiveData()

    fun onClick() {
        clickObserver.value = movieDetail
    }

    fun description(): String = when (movieDetail.categoryId) {
        HomeViewModel.HomeMovieType.MOVIE_RIHAN_TV.type -> {
            val filter = movieDetail.content?.split("\r\n")?.filter { it.isNotEmpty() }
            filter?.last().orEmpty().replace("[简 介]:", "[简 介]: ")

        }
        else -> {
            val filter = movieDetail.content?.split("◎")?.filter { it.isNotEmpty() }
            filter?.last().orEmpty().replace(Regex("[\\r\\n 　]"), "").replace("简介", "[简 介]: ")
        }
    }

    companion object {
        private const val TAG = "ItemChildViewModel"
    }
}