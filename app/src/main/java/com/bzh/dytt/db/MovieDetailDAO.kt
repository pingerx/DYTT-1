package com.bzh.dytt.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.Query
import com.bzh.dytt.vo.MovieDetail

@Dao
interface MovieDetailDAO {

    @Query("SELECT * FROM movie_detail WHERE categoryId=:type  ORDER BY id DESC")
    fun movieList(type: Int?): LiveData<List<MovieDetail>>

    @Insert(onConflict = IGNORE)
    fun insertMovieList(movieList: List<MovieDetail>)

}
