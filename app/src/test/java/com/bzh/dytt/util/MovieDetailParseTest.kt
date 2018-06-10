package com.bzh.dytt.util

import com.bzh.dytt.TestUtils
import com.bzh.dytt.vo.MovieDetail
import com.google.gson.Gson
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieDetailParseTest {

    lateinit var movieDetail: MovieDetail
    lateinit var movieDetailParse: MovieDetailParse
    @Before
    fun setUp() {
        val json = TestUtils.getResource(javaClass, "movie-detail.json")
        val gson = Gson()
        movieDetail = gson.fromJson<MovieDetail>(json, MovieDetail::class.java)

        movieDetailParse = MovieDetailParse()
    }


    @After
    fun tearDown() {
    }

    @Test
    fun parse() {
        movieDetailParse.parse(movieDetail)
    }
}