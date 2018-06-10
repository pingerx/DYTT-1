package com.bzh.dytt.repository

import android.arch.lifecycle.LiveData
import com.bzh.dytt.AppExecutors
import com.bzh.dytt.api.ApiResponse
import com.bzh.dytt.api.NetworkBoundResource
import com.bzh.dytt.api.NetworkResource
import com.bzh.dytt.api.NetworkService
import com.bzh.dytt.db.MovieDetailDAO
import com.bzh.dytt.key.KeyUtils
import com.bzh.dytt.ui.home.HomeViewModel
import com.bzh.dytt.util.MovieDetailParse
import com.bzh.dytt.util.RateLimiter
import com.bzh.dytt.vo.MovieDetail
import com.bzh.dytt.vo.MovieDetailResponse
import com.bzh.dytt.vo.Resource
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val networkService: NetworkService,
        private val movieDetailDAO: MovieDetailDAO,
        private val movieDetailParse: MovieDetailParse
) {

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun movieList(
            movieType: HomeViewModel.HomeMovieType?,
            page: Int,
            force: Boolean = false): LiveData<Resource<List<MovieDetail>>> {

        return object : NetworkBoundResource<List<MovieDetail>, MovieDetailResponse>(appExecutors) {

            override fun saveCallResult(item: MovieDetailResponse) {
                for (movie in item.rows) {
                    movie.categoryId = movieType?.type ?: -1
                    movieDetailParse.parse(movie)
                }
                movieDetailDAO.insertMovieList(item.rows)
            }

            override fun shouldFetch(data: List<MovieDetail>?): Boolean {
                return force || data == null || data.isEmpty() || repoListRateLimit.shouldFetch("MOVIE_LIST_" + movieType?.type)
            }

            override fun loadFromDb(): LiveData<List<MovieDetail>> {
                return movieDetailDAO.movieList(movieType?.type)
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> {
                val timeStamp = System.currentTimeMillis() / 1000L
                val imei = ""
                val key = KeyUtils.getHeaderKey(timeStamp)
                return networkService.movieList(
                        headerKey = key,
                        headerTimestamp = "$timeStamp",
                        headerImei = imei,
                        categoryId = movieType?.type,
                        page = page,
                        searchContent = ""
                )
            }

        }.asLiveData()
    }

    fun movieItemUpdate(oldItem: MovieDetail): LiveData<Resource<MovieDetail>> {

        return object : NetworkResource<MovieDetail, MovieDetail>(appExecutors) {

            override fun saveCallResult(item: MovieDetail): MovieDetail {
                item.categoryId = oldItem.categoryId
                item.isPrefect = true
                movieDetailParse.parse(item)
                movieDetailDAO.updateMovie(item)
                return item
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetail>> {
                val timeStamp = System.currentTimeMillis() / 1000L
                val imei = ""
                val key = KeyUtils.getHeaderKey(timeStamp)
                return networkService.movieDetail(
                        headerKey = key,
                        headerTimestamp = "$timeStamp",
                        headerImei = imei,
                        categoryId = oldItem.categoryId,
                        movieDetailId = oldItem.id
                )
            }

        }.asLiveData()
    }
}