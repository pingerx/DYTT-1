package com.bzh.dytt.base

import android.app.Activity
import android.app.Application
import com.bzh.dytt.BuildConfig
import com.bzh.dytt.di.AppInjector
import com.bzh.dytt.key.KeyUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.umeng.commonsdk.UMConfigure
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BasicApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        AppInjector.init(this)

        KeyUtils.init(this)

        UMConfigure.init(this, "5acda2b4f29d98253600000c", "Kuan", UMConfigure.DEVICE_TYPE_PHONE, null)
    }
}