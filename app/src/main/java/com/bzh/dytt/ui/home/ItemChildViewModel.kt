package com.bzh.dytt.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bzh.dytt.vo.MovieDetail

class ItemChildViewModel(val movieDetail: MovieDetail) : ViewModel() {

    val clickObserver: MutableLiveData<MovieDetail> = MutableLiveData()

    fun onClick() {
        clickObserver.value = movieDetail
    }

    fun description(): String = when (movieDetail.categoryId) {
        HomeViewModel.HomeMovieType.MOVIE_RIHAN_TV.type -> {
            val filter = movieDetail.content?.split("\r\n")?.filter { it.isNotEmpty() }
            filter?.last().orEmpty().replace("[简 介]:", "简 介 : ")

        }
        HomeViewModel.HomeMovieType.MOVIE_OUTMEI_TV.type -> {
            var filter = movieDetail.content?.split("\r\n")?.filter { it.isNotEmpty() }
            if (filter?.size == 1) {
                filter = movieDetail.content?.split("◎")?.filter { it.isNotEmpty() }
            }
            var fromIndex = -1
            filter?.forEachIndexed { index, element ->
                if (element.replace("　", "").replace(" ", "").contains("简介")) {
                    fromIndex = index
                    return@forEachIndexed
                }
            }
            if (fromIndex >= 0) {
                filter?.subList(fromIndex, filter.size)?.joinToString("")
                        .orEmpty()
                        .replace("◎简　　介", "简 介 : ")
                        .replace("　", "")
            } else {
                ""
            }
        }
        else -> {
            val filter = movieDetail.content?.split("◎")?.filter { it.isNotEmpty() }.orEmpty()
            if (filter.isNotEmpty()) {
                filter.last().replace(Regex("[\\r\\n 　]"), "").replace("简介", "简 介 : ")
            } else {
                ""
            }
        }
    }

    companion object {
        private const val TAG = "ItemChildViewModel"
    }
}