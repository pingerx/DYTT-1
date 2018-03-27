package com.bzh.dytt.data.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bzh.dytt.data.CategoryMap;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CategoryMapDAO {

    @Insert(onConflict = REPLACE)
    void insertCategoryMap(CategoryMap categoryMap);

    @Insert(onConflict = IGNORE)
    void insertCategoryMapList(List<CategoryMap> categoryMapList);

    @Query("SELECT * FROM category_map WHERE category = :category ORDER BY serial_number DESC")
    LiveData<List<CategoryMap>> getMovieLinksByCategory(int category);

    @Update
    void updateCategory(CategoryMap mCategoryMap);

    @Query("SELECT is_parsed FROM category_map WHERE link = :link")
    boolean IsParsed(String link);
}
