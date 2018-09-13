package com.bzh.dytt.workers

import android.util.Log
import androidx.work.Worker
import com.bzh.dytt.db.DyttDB

class FetchVideoDetailWorker : Worker() {

    override fun doWork(): Result {

        val movieDetailDao = DyttDB.getInstance(applicationContext).movieDetailDao()

        val liveData = movieDetailDao.movieListNotPrefect(false)

        for (data in liveData) {
            Log.d(TAG, "FetchVideoDetailWorker doWork $data")
        }

        return Result.SUCCESS
    }

    companion object {
        const val TAG = "FetchVideoDetailWorker"
    }

}