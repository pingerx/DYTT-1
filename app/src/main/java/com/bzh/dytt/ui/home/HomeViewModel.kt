package com.bzh.dytt.ui.home

import android.arch.lifecycle.*
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel(), LifecycleObserver {

    private val mTabLiveData: MutableLiveData<List<HomeMovieType>> = MutableLiveData()

    val tabLiveData: LiveData<List<HomeMovieType>>
        get() = mTabLiveData


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun active() {
        mTabLiveData.value = arrayListOf(
                HomeMovieType.MOVIE_ZUIXIN_FILM,
                HomeMovieType.MOVIE_ZONGHE_FILM,
                HomeMovieType.MOVIE_HUAYU_FILM,
                HomeMovieType.MOIVE_OUMEI_FILM,
                HomeMovieType.MOIVE_RIHAN_FILM,
                HomeMovieType.MOVIE_HUAYU_TV,
                HomeMovieType.MOVIE_OUTMEI_TV,
                HomeMovieType.MOVIE_RIHAN_TV,
                HomeMovieType.MOVIE_ZENGYIV,
                HomeMovieType.MOVIE_DONGMAN
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun inactive() {
    }

    companion object {
        const val TAG = "HomeViewModel"
    }

    enum class HomeMovieType(val type: Int) {
        MOVIE_ZUIXIN_FILM(9),
        MOVIE_ZONGHE_FILM(10),
        MOVIE_HUAYU_FILM(1),
        MOIVE_OUMEI_FILM(2),
        MOIVE_RIHAN_FILM(3),
        MOVIE_HUAYU_TV(4),
        MOVIE_OUTMEI_TV(5),
        MOVIE_RIHAN_TV(6),
        MOVIE_ZENGYIV(7),
        MOVIE_DONGMAN(8);
    }
}