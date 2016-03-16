package com.bzh.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-14<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class FilmDetailEntity implements Parcelable {

    private String title;               // 标题
    private String publishTime;         // 发布时间
    private String coverUrl;            // 封面
    private String name;                 // 电影名
    private String translationName;     // 译名
    private String years;               // 年代
    private String country;             // 国家
    private String category;            // 类别
    private String language;            // 语言
    private String subtitle;            // 字幕
    private String fileFormat;          // 文件格式
    private String videoSize;           // 视频尺寸
    private String fileSize;            // 文件大小
    private String showTime;            // 片长
    private String director;            // 导演
    private ArrayList<String> leadingPlayers;   // 主演
    private String description;         // 简介
    private String previewImage;     // 预览图
    private String downloadUrl;         // 下载地址

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslationName() {
        return translationName;
    }

    public void setTranslationName(String translationName) {
        this.translationName = translationName;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(String videoSize) {
        this.videoSize = videoSize;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public ArrayList<String> getLeadingPlayers() {
        return leadingPlayers;
    }

    public void setLeadingPlayers(ArrayList<String> leadingPlayers) {
        this.leadingPlayers = leadingPlayers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }


    @Override
    public String toString() {
        return "FilmDetailEntity{\n" +
                "title='" + title + '\'' + "\n" +
                ", publishTime='" + publishTime + '\'' + "\n" +
                ", coverUrl='" + coverUrl + '\'' + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", translationName='" + translationName + '\'' + "\n" +
                ", years='" + years + '\'' + "\n" +
                ", country='" + country + '\'' + "\n" +
                ", category='" + category + '\'' + "\n" +
                ", language='" + language + '\'' + "\n" +
                ", subtitle='" + subtitle + '\'' + "\n" +
                ", fileFormat='" + fileFormat + '\'' + "\n" +
                ", videoSize='" + videoSize + '\'' + "\n" +
                ", fileSize='" + fileSize + '\'' + "\n" +
                ", showTime='" + showTime + '\'' + "\n" +
                ", director='" + director + '\'' + "\n" +
                ", leadingPlayers=" + leadingPlayers + "\n" +
                ", description='" + description + '\'' + "\n" +
                ", previewImage='" + previewImage + '\'' + "\n" +
                ", downloadUrl='" + downloadUrl + '\'' + "\n" +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.publishTime);
        dest.writeString(this.coverUrl);
        dest.writeString(this.name);
        dest.writeString(this.translationName);
        dest.writeString(this.years);
        dest.writeString(this.country);
        dest.writeString(this.category);
        dest.writeString(this.language);
        dest.writeString(this.subtitle);
        dest.writeString(this.fileFormat);
        dest.writeString(this.videoSize);
        dest.writeString(this.fileSize);
        dest.writeString(this.showTime);
        dest.writeString(this.director);
        dest.writeStringList(this.leadingPlayers);
        dest.writeString(this.description);
        dest.writeString(this.previewImage);
        dest.writeString(this.downloadUrl);
    }

    public FilmDetailEntity() {
    }

    protected FilmDetailEntity(Parcel in) {
        this.title = in.readString();
        this.publishTime = in.readString();
        this.coverUrl = in.readString();
        this.name = in.readString();
        this.translationName = in.readString();
        this.years = in.readString();
        this.country = in.readString();
        this.category = in.readString();
        this.language = in.readString();
        this.subtitle = in.readString();
        this.fileFormat = in.readString();
        this.videoSize = in.readString();
        this.fileSize = in.readString();
        this.showTime = in.readString();
        this.director = in.readString();
        this.leadingPlayers = in.createStringArrayList();
        this.description = in.readString();
        this.previewImage = in.readString();
        this.downloadUrl = in.readString();
    }

    public static final Creator<FilmDetailEntity> CREATOR = new Creator<FilmDetailEntity>() {
        @Override
        public FilmDetailEntity createFromParcel(Parcel source) {
            return new FilmDetailEntity(source);
        }

        @Override
        public FilmDetailEntity[] newArray(int size) {
            return new FilmDetailEntity[size];
        }
    };
}
