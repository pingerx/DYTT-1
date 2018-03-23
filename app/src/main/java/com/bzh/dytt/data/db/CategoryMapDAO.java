package com.bzh.dytt.data.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bzh.dytt.data.CategoryMap;
import com.bzh.dytt.data.VideoDetail;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CategoryMapDAO {

    @Insert(onConflict = REPLACE)
    void insertCategoryMap(CategoryMap categoryMap);

    @Insert(onConflict = REPLACE)
    void insertCategoryMapList(List<CategoryMap> categoryMapList);

    @Query("SELECT * FROM category_map WHERE category = :category ORDER BY rowid ASC")
    LiveData<List<CategoryMap>> getMovieLinksByCategory(int category);

}
