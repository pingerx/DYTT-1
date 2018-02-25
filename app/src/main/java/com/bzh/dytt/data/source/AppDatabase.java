package com.bzh.dytt.data.source;


import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.bzh.dytt.data.HomeArea;
import com.bzh.dytt.data.HomeItem;

@Database(entities = {HomeArea.class, HomeItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "dytt-data.db";

    public abstract HomeItemDao homeItemDao();

    public abstract HomeAreaDao homeAreaDAO();

    private static AppDatabase sInstance;

    private static final Object sLock = new Object();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        sInstance.setDatabaseCreated();

                    }
                }).build();
            }
            return sInstance;
        }
    }

    public void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public MutableLiveData<Boolean> getIsDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
