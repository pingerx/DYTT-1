package com.bzh.dytt.di


import android.app.Application
import android.arch.persistence.room.Room
import com.bzh.dytt.db.AppDatabase
import com.bzh.dytt.db.CategoryMapDAO
import com.bzh.dytt.db.CategoryPageDAO
import com.bzh.dytt.db.VideoDetailDAO
import com.bzh.dytt.api.DyttService
import com.bzh.dytt.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
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
    internal fun provideCacheInterceptor(): Interceptor {
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
    internal fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(okhttpCache: Cache, okHttpCacheInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(okhttpCache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(okHttpCacheInterceptor)
                .build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
    internal fun provideDyttService(retrofit: Retrofit): DyttService {
        return retrofit.create(DyttService::class.java)
    }

    @Singleton
    @Provides
    internal fun provideDb(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    internal fun provideCategoryMapDao(db: AppDatabase): CategoryMapDAO {
        return db.categoryMapDAO()
    }

    @Singleton
    @Provides
    internal fun provideCategroyPageDao(db: AppDatabase): CategoryPageDAO {
        return db.categoryPageDAO()
    }

    @Singleton
    @Provides
    internal fun provideVideoDetailDao(db: AppDatabase): VideoDetailDAO {
        return db.videoDetailDAO()
    }
}
