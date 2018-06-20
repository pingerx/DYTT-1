package com.bzh.dytt.repository

import android.arch.lifecycle.LiveData
import android.util.Log
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
class DataRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val networkService: NetworkService,
        private val movieDetailDAO: MovieDetailDAO,
        private val movieDetailParse: MovieDetailParse
) {

    private val repoListRateLimit = RateLimiter<String>(5, TimeUnit.SECONDS)

    fun movieList(
            movieType: HomeViewModel.HomeMovieType?,
            page: Int): LiveData<Resource<List<MovieDetail>>> {

        return object : NetworkBoundResource<List<MovieDetail>, MovieDetailResponse>(appExecutors) {

            override fun saveCallResult(item: MovieDetailResponse) {
                for (movie in item.rows) {
                    movie.categoryId = movieType?.type ?: -1
                    movieDetailParse.parse(movie)
                    Log.d(TAG, "movieList saveCallResult id=${movie.id} categoryId=${movie.categoryId} name=${movie.name} ")
                }
                movieDetailDAO.insertMovieList(item.rows)
            }

            override fun shouldFetch(data: List<MovieDetail>?): Boolean {
                val fetch = data == null || data.isEmpty() || repoListRateLimit.shouldFetch("MOVIE_LIST_" + movieType?.type)
                Log.d(TAG, "movieList shouldFetch $fetch ${data?.size}")
                return fetch
            }

            override fun loadFromDb(): LiveData<List<MovieDetail>> {
                return movieDetailDAO.movieList(movieType?.type)
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> {
                val timeStamp = System.currentTimeMillis() / 1000L
                val imei = ""
                val key = KeyUtils.getHeaderKey(timeStamp)

                Log.d(TAG, "Request Header timeStamp=$timeStamp imei=$imei key=$key categoryId=${movieType?.type} page=$page")

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

                Log.d(TAG, "Request Header timeStamp=$timeStamp imei=$imei key=$key id=${oldItem.id} categoryId=${oldItem.categoryId} name=${oldItem.name}")

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

    fun search(input: String): LiveData<Resource<List<MovieDetail>>> {
        return object : NetworkResource<List<MovieDetail>, MovieDetailResponse>(appExecutors) {
            override fun saveCallResult(item: MovieDetailResponse): List<MovieDetail> {
                for (movie in item.rows) {
                    movieDetailParse.parse(movie)
                }
                movieDetailDAO.insertMovieList(item.rows)
                return item.rows
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> {
                val timeStamp = System.currentTimeMillis() / 1000L
                val imei = ""
                val key = KeyUtils.getHeaderKey(timeStamp)

                Log.d(TAG, "Request Header timeStamp=$timeStamp imei=$imei key=$key")

                return networkService.movieList(
                        headerKey = key,
                        headerTimestamp = "$timeStamp",
                        headerImei = imei,
                        categoryId = 1,
                        page = 1,
                        searchContent = input
                )
            }

        }.asLiveData()
    }

    companion object {
        const val TAG = "DataRepository"
    }
}