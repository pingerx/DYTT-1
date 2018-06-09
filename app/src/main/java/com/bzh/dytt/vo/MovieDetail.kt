package com.bzh.dytt.vo

import android.arch.persistence.room.Entity

@Entity(tableName = "movie_detail", primaryKeys = ["id", "categoryId"])
data class MovieDetail(
        var id: Int,
        var categoryId: Int,
        var name: String?,
        var simpleName: String?,
        var publishTime: String?,
        var homePicUrl: String?,
        var pics: String?,
        var downloadUrl: String?,
        var content: String?,
        var isPrefect: Boolean = false
)