package com.bzh.dytt.repository

import android.arch.lifecycle.LiveData
import android.util.Log
import com.bzh.dytt.AppExecutors
import com.bzh.dytt.api.ApiResponse
import com.bzh.dytt.api.DelayNetworkBoundResource
import com.bzh.dytt.api.DelayRunnableQueue
import com.bzh.dytt.api.NetworkService
import com.bzh.dytt.db.MovieDetailDAO
import com.bzh.dytt.key.KeyUtils
import com.bzh.dytt.ui.home.HomeViewModel
import com.bzh.dytt.util.MovieDetailParse
import com.bzh.dytt.util.NetworkHelper
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
        private val delayRunnableQueue: DelayRunnableQueue<Any, Runnable>,
        private val movieDetailParse: MovieDetailParse,
        private val networkHelper: NetworkHelper
) {

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.SECONDS)

    fun getFirstPageByType(movieType: HomeViewModel.HomeMovieType, page: Int): LiveData<Resource<List<MovieDetail>>> {

        val value = object : DelayNetworkBoundResource<List<MovieDetail>, MovieDetailResponse>(appExecutors) {

            override fun saveCallResult(item: MovieDetailResponse) {
                if (item.rows == null || item.rows.isEmpty()) {
                    return
                }
                for (movie in item.rows) {
                    movie.categoryId = movieType.type ?: -1
                    movieDetailParse.parse(movie)
                }
                movieDetailDAO.insertMovieList(item.rows)
            }

            override fun shouldFetch(data: List<MovieDetail>?): Boolean {
                return networkHelper.isNetworkConnected() && (data == null || data.isEmpty() || repoListRateLimit.shouldFetch("MOVIE_LIST_" + movieType.type))
            }

            override fun loadFromDb(): LiveData<List<MovieDetail>> {
                return movieDetailDAO.getFirstPage(movieType.type)
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> {
                val timeStamp = System.currentTimeMillis() / 1000L
                val imei = ""
                val key = KeyUtils.getHeaderKey(timeStamp)

                Log.d(TAG, "Request Header timeStamp=$timeStamp imei=$imei key=$key categoryId=${movieType.type} page=$page")

                return networkService.movieList(
                        headerKey = key,
                        headerTimestamp = "$timeStamp",
                        headerImei = imei,
                        categoryId = movieType.type,
                        page = page,
                        searchContent = ""
                )
            }

            override fun finish() {
                super.finish()
                delayRunnableQueue.finishDelay(movieType.type)
            }
        }

        delayRunnableQueue.removeDelay(movieType.type)
        delayRunnableQueue.addDelay(movieType.type, value)
        return value.asLiveData()
    }

    fun removeMovieUpdate(oldItem: MovieDetail) {
        delayRunnableQueue.removeDelay(oldItem.id)
    }

    fun movieUpdate(oldItem: MovieDetail): LiveData<Resource<MovieDetail>> {

        val value = object : DelayNetworkBoundResource<MovieDetail, MovieDetail>(appExecutors) {

            override fun shouldFetch(data: MovieDetail?): Boolean {
                return networkHelper.isNetworkConnected() && data?.isPrefect == false
            }

            override fun loadFromDb(): LiveData<MovieDetail> {
                return movieDetailDAO.getMovieByCategoryIdAndId(oldItem.categoryId, oldItem.id)
            }

            override fun saveCallResult(item: MovieDetail) {
                item.categoryId = oldItem.categoryId
                item.isPrefect = true
                movieDetailParse.parse(item)
                movieDetailDAO.updateMovie(item)
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

            override fun finish() {
                super.finish()
                delayRunnableQueue.finishDelay(oldItem.id)
            }
        }

        delayRunnableQueue.removeDelay(oldItem.id)
        delayRunnableQueue.addDelay(oldItem.id, value)

        return value.asLiveData()
    }

    fun search(input: String): LiveData<Resource<List<MovieDetail>>> {

        return object : DelayNetworkBoundResource<List<MovieDetail>, MovieDetailResponse>(appExecutors) {

            override fun loadFromDb(): LiveData<List<MovieDetail>> {
                return movieDetailDAO.getFirstPage(1)
            }

            override fun shouldFetch(data: List<MovieDetail>?): Boolean {
                return true
            }

            override fun saveCallResult(item: MovieDetailResponse) {
                for (movie in item.rows) {
                    movieDetailParse.parse(movie)
                }
                movieDetailDAO.insertMovieList(item.rows)
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