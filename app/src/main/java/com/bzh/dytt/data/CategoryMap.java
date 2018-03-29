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

    @ColumnInfo(name = "is_parsed")
    private boolean mIsParsed;

    @ColumnInfo(name = "serial_number")
    private int mSN;

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

    public void setIsParsed(boolean mIsParsed) {
        this.mIsParsed = mIsParsed;
    }

    public boolean getIsParsed() {
        return mIsParsed;
    }

    public int getSN() {
        return mSN;
    }

    public void setSN(int SN) {
        mSN = SN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryMap that = (CategoryMap) o;

        if (mCategory != that.mCategory) return false;
        return mLink.equals(that.mLink);
    }

    @Override
    public int hashCode() {
        int result = mCategory;
        result = 31 * result + mLink.hashCode();
        return result;
    }
}
