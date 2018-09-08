package com.bzh.dytt.vo

import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "movie_detail", primaryKeys = ["id", "categoryId"])
data class MovieDetail(
        val id: Int,
        val name: String?,
        val publishTime: String?,
        val homePicUrl: String?,
        val pics: String?,
        val downloadUrl: String?,
        val content: String?,
        var categoryId: Int,
        var isPrefect: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(publishTime)
        parcel.writeString(homePicUrl)
        parcel.writeString(pics)
        parcel.writeString(downloadUrl)
        parcel.writeString(content)
        parcel.writeInt(categoryId)
        parcel.writeByte(if (isPrefect) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieDetail> {
        override fun createFromParcel(parcel: Parcel): MovieDetail {
            return MovieDetail(parcel)
        }

        override fun newArray(size: Int): Array<MovieDetail?> {
            return arrayOfNulls(size)
        }
    }

}