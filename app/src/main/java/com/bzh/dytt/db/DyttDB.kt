package com.bzh.dytt.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.support.annotation.VisibleForTesting
import com.bzh.dytt.vo.MovieDetail

@Database(
        entities = [MovieDetail::class],
        version = 5
)
abstract class DyttDB : RoomDatabase() {

    abstract fun movieDetailDao(): MovieDetailDAO

    companion object {
        @VisibleForTesting
        val DATABASE_NAME = "dytt.db"
    }
}