package com.bzh.dytt.di


import android.app.Application
import android.arch.persistence.room.Room
import com.bzh.dytt.api.NetworkService
import com.bzh.dytt.db.DyttDb
import com.bzh.dytt.db.MovieDetailDAO
import com.bzh.dytt.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * https://github.com/codepath/android_guides/wiki/Dependency-Injection-with-Dagger-2
 */
@Module(includes = [ViewModelModule::class])
class AppModule(private val mBaseUrl: String) {

    @Singleton
    @Provides
    fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=1")
                    .removeHeader("Pragma")
                    .build()
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(okhttpCache: Cache, okHttpCacheInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(okhttpCache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(okHttpCacheInterceptor)
                .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build()
    }


    @Singleton
    @Provides
    fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

    @Singleton
    @Provides
    fun provideDyttDb(app: Application): DyttDb {
        return Room.databaseBuilder(app, DyttDb::class.java, DyttDb.DATABASE_NAME).fallbackToDestructiveMigration().build()
    }


    @Singleton
    @Provides
    fun provideMovieDetailDao(db: DyttDb): MovieDetailDAO {
        return db.movieDetailDao()
    }
}
