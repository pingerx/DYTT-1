package com.bzh.dytt.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.bzh.dytt.BuildConfig
import com.bzh.dytt.di.AppInjector
import com.bzh.dytt.key.KeyUtils
import com.google.firebase.FirebaseApp
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BasicApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        AppInjector.init(this)

        KeyUtils.init(this)

        Log.d("BasicApp", getSignature(this))
    }

    @SuppressLint("PackageManagerGetSignatures")
    private fun getSignature(context: Context): String {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
        return packageInfo.signatures[0].toCharsString()
    }
}