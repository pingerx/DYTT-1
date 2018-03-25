package com.bzh.dytt.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(
        tableName = "category_map",
        primaryKeys = {"category", "link"}
)
public class CategoryMap {

    @ColumnInfo(name = "category")
    @NonNull
    private int mCategory;

    @ColumnInfo(name = "link")
    @NonNull
    private String mLink;

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int category) {
        mCategory = category;
    }
}
