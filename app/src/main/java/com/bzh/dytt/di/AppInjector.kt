package com.bzh.dytt.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.bzh.dytt.base.BasicApp
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector {

    fun init(basicApp: BasicApp) {

        // Injector App
        DaggerAppComponent.builder().application(basicApp).appModule(AppModule("http://www.dytt8.net")).build().inject(basicApp)

        // Injector Activities
        basicApp
                .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityStarted(activity: Activity?) {

                    }

                    override fun onActivityDestroyed(activity: Activity?) {
                    }

                    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                    }

                    override fun onActivityStopped(activity: Activity?) {
                    }

                    override fun onActivityResumed(activity: Activity?) {

                    }

                    override fun onActivityPaused(activity: Activity?) {

                    }

                    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                        handleActivity(activity)
                    }
                })
    }

    private fun handleActivity(activity: Activity?) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(
                            object : FragmentManager.FragmentLifecycleCallbacks() {
                                override fun onFragmentCreated(fm: FragmentManager?, f: Fragment?, savedInstanceState: Bundle?) {
                                    super.onFragmentCreated(fm, f, savedInstanceState)
                                    if (f is Injectable) {
                                        AndroidSupportInjection.inject(f)
                                    }
                                }
                            }, true
                    )
        }
    }
}
