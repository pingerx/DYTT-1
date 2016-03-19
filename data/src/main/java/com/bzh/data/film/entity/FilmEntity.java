package com.bzh.data.film.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-16<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class FilmEntity implements Parcelable {

    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "FilmEntity{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
    }

    public FilmEntity() {
    }

    protected FilmEntity(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
    }

    public static final Creator<FilmEntity> CREATOR = new Creator<FilmEntity>() {
        @Override
        public FilmEntity createFromParcel(Parcel source) {
            return new FilmEntity(source);
        }

        @Override
        public FilmEntity[] newArray(int size) {
            return new FilmEntity[size];
        }
    };
}
