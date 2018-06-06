package com.bzh.dytt.vo

import android.arch.persistence.room.Entity

@Entity(tableName = "movie_detail", primaryKeys = ["id", "categoryId"])
data class MovieDetail(
        val id: Int,
        var categoryId: Int,
        val name: String?,
        val simpleName: String?,
        val publishTime: String?,
        val homePicUrl: String?,
        val pics: String?,
        val downloadUrl: String?,
        val content: String?
)