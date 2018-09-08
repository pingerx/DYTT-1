package com.bzh.dytt.util

import com.bzh.dytt.vo.MovieDetail
import com.google.gson.Gson
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.nio.charset.StandardCharsets

class MovieDetailParseTest {

    @Throws(IOException::class)
    fun getResource(fileName: String): String {
        val inputStream = javaClass!!.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        return source.readString(StandardCharsets.UTF_8)
    }

    @Before
    fun setUp() {

    }


    @Test
    fun parseRiHan() {

        val json = getResource("movie-rihan-tv.json")
        val gson = Gson()
        val movieDetail = gson.fromJson<MovieDetail>(json, MovieDetail::class.java)

        val parse = RiHanTVParase()

        parse.parse(movieDetail)

    }

    @Test
    fun parse() {

        val json = getResource("movie-detail.json")
        val gson = Gson()
        val movieDetail = gson.fromJson<MovieDetail>(json, MovieDetail::class.java)

        val parse = NormalParse()

        parse.parse(movieDetail)

    }
}