package com.bzh.dytt.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.bzh.dytt.di.AppInjector
import com.bzh.dytt.key.KeyUtils
//import com.google.firebase.FirebaseApp
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BasicApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)

//        FirebaseApp.initializeApp(this)

        KeyUtils.init(this)

        Log.d(TAG, "onCreate() called ${getSignature(this)}")
    }

    @SuppressLint("WrongConstant", "PackageManagerGetSignatures")
    private fun getSignature(context: Context): String = if (Build.VERSION.SDK_INT >= 28) {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNING_CERTIFICATES)
        packageInfo.signingInfo.apkContentsSigners[0].toCharsString()
    } else {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
        packageInfo.signatures[0].toCharsString()
    }

    companion object {
        private const val TAG = "BasicApp"
    }
}