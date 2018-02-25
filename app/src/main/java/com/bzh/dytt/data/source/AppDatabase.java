package com.bzh.dytt.data.source;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.VisibleForTesting;

import com.bzh.dytt.data.HomeArea;
import com.bzh.dytt.data.HomeItem;

@Database(entities = {HomeArea.class, HomeItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "dytt-data.db";

    public abstract HomeItemDao homeItemDao();

    public abstract HomeAreaDao homeAreaDAO();
}
