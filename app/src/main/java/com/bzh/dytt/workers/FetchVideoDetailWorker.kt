package com.bzh.dytt.workers

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.work.Worker
import com.bzh.dytt.api.NetworkService
import com.bzh.dytt.db.DyttDB
import com.bzh.dytt.key.KeyUtils
import com.bzh.dytt.vo.MovieDetail
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class FetchVideoDetailWorker : Worker() {

    private fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=1")
                    .removeHeader("Pragma")
                    .build()
        }
    }

    private fun provideOkHttpCache(application: Context): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    private fun provideOkHttpClient(okhttpCache: Cache, okHttpCacheInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(okhttpCache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(okHttpCacheInterceptor)
                .build()
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://m.dydytt.net:8080")
                .client(okHttpClient)
                .build()
    }

    private fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    override fun doWork(): Result {

        val networkService = provideNetworkService(provideRetrofit(provideOkHttpClient(provideOkHttpCache(applicationContext), provideCacheInterceptor())))

        val movieDetailDao = DyttDB.getInstance(applicationContext).movieDetailDao()

        Log.d(TAG, "doWork() called isMainThread ${Looper.myLooper() == Looper.getMainLooper()} networkService $networkService movieDetailDao $movieDetailDao  ")

        val listByPrefect = movieDetailDao.getMovieListByPrefect(false)

        Log.d(TAG, "doWork() called listByPrefect ${listByPrefect}")

        for (movieDetail in listByPrefect) {

            val timeStamp = System.currentTimeMillis() / 1000L
            val imei = ""
            val key = KeyUtils.getHeaderKey(timeStamp)

            Log.d(TAG, "doWork() called before ${movieDetail.name} $key $imei $timeStamp ${movieDetail.categoryId} ${movieDetail.id} ")

            val response: Response<MovieDetail> = networkService.movieDetailNormal(headerKey = key,
                    headerTimestamp = "$timeStamp",
                    headerImei = imei,
                    categoryId = movieDetail.categoryId,
                    movieDetailId = movieDetail.id).execute()

            if (response.isSuccessful) {
                if (response.body() != null) {
                    val movie = response.body()!!
                    movieDetailDao.updateMovie(movie)
                    Log.d(TAG, "doWork() called response success ${movie.name}")
                } else {
                    Log.d(TAG, "doWork() called response body is null")
                }
            } else {
                Log.d(TAG, "doWork() called response not success")
            }

            Thread.sleep(1000L)
        }

        return Result.SUCCESS
    }

    companion object {
        const val TAG = "FetchVideoDetailWorker"
    }

}