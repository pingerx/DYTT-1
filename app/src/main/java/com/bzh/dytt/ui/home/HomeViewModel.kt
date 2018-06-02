package com.bzh.dytt.ui.home

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.bzh.dytt.repository.DataRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(dataRepository: DataRepository) : ViewModel(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun active() {
        Log.d(TAG, "active")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun inactive() {
        Log.d(TAG, "inactive")
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}