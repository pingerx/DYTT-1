package com.bzh.dytt.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "homeareas")
public class HomeArea implements Parcelable {

    @ColumnInfo(name = "id")
    @PrimaryKey
    private int mId;

    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle;

    @NonNull
    @ColumnInfo(name = "type")
    private int mType;

    @ColumnInfo(name = "link")
    private String mDetailLink;

    @ColumnInfo(name = "last_update_time")
    private long mLastUpdateTime;

    public HomeArea() {

    }

    @Ignore
    public HomeArea(@NonNull String title, @NonNull int type) {
        mTitle = title;
        mId = title.hashCode();
        mType = type;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String title) {
        this.mTitle = title;
        this.mId = title.hashCode();
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public String getDetailLink() {
        return mDetailLink;
    }

    public void setDetailLink(String detailLink) {
        mDetailLink = detailLink;
    }

    public long getLastUpdateTime() {
        return mLastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        mLastUpdateTime = lastUpdateTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mTitle);
        dest.writeInt(this.mType);
        dest.writeString(this.mDetailLink);
        dest.writeLong(this.mLastUpdateTime);
    }

    protected HomeArea(Parcel in) {
        this.mId = in.readInt();
        this.mTitle = in.readString();
        this.mType = in.readInt();
        this.mDetailLink = in.readString();
        this.mLastUpdateTime = in.readLong();
    }

    public static final Creator<HomeArea> CREATOR = new Creator<HomeArea>() {
        @Override
        public HomeArea createFromParcel(Parcel source) {
            return new HomeArea(source);
        }

        @Override
        public HomeArea[] newArray(int size) {
            return new HomeArea[size];
        }
    };
}
