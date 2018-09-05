package com.bzh.dytt.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.bzh.dytt.vo.MovieDetail

class ItemChildViewModel(private val movieDetail: MovieDetail) : ViewModel() {

    val imageUrl = ObservableField<String>(movieDetail.homePicUrl)

    val doubanGrade = ObservableField<String>("DB/${movieDetail.doubanGrade}")

    val imdbGrade = ObservableField<String>("IMDB/${movieDetail.imdbGrade}")

    val publishTime = ObservableField<String>(movieDetail.publishTime)

    val description = ObservableField<String>(movieDetail.description)

    val title = ObservableField<String>(when {
        movieDetail.translateName?.contains(Regex(HomeListFragment.PATTERN)) == true -> {
            movieDetail.translateName
        }
        movieDetail.titleName?.contains(Regex(HomeListFragment.PATTERN)) == true -> {
            movieDetail.titleName
        }
        else -> {
            movieDetail.simpleName
        }
    })

    val clickObserver: MutableLiveData<MovieDetail> = MutableLiveData()

    fun onClick() {
        clickObserver.value = movieDetail
    }

    companion object {
        private const val TAG = "ItemChildViewModel";
    }
}