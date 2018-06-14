package com.bzh.dytt.util

import com.bzh.dytt.vo.MovieDetail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailParse @Inject constructor() {

    fun parse(movie: MovieDetail) {
        if (movie.name != null && movie.name.isNotEmpty()) {
            val startIndex = movie.name.indexOf("《")
            val lastIndex = movie.name.lastIndexOf("》")
            if (startIndex != -1 && lastIndex != -1) {
                movie.simpleName = movie.name.substring(startIndex + 1, lastIndex)
            }
        }
        if (movie.content != null && movie.content.isNotEmpty()) {
            val contents = movie.content.split("◎")
            for (content: String in contents) {
                if (content.startsWith(translateName)) {
                    movie.translateName = content.substring(translateName.length).replaceIllegal().split("/")[0]
                }
                if (content.startsWith(titleName)) {
                    movie.titleName = content.substring(titleName.length).replaceIllegal().split("/")[0]
                }
                if (content.startsWith(imdbGrade)) {
                    val imdb = content.substring(imdbGrade.length).replaceIllegal()
                    movie.imdbGrade = imdb.substring(0, imdb.indexOf("/")).toFloat()
                }
                if (content.startsWith(doubanGrade)) {
                    val douban = content.substring(doubanGrade.length).replaceIllegal()
                    movie.doubanGrade = douban.substring(0, douban.indexOf("/")).toFloat()
                }
                if (content.startsWith(diretor)) {
                    movie.diretorName = content.substring(diretor.length).replaceIllegal()
                }
                if (content.startsWith(description)) {
                    movie.description = content.substring(description.length).replaceIllegal()
                }
                if (content.startsWith(type)) {
                    movie.type = content.substring(type.length).replaceIllegal()
                }
                if (content.startsWith(language)) {
                    movie.language = content.substring(language.length).replaceIllegal()
                }
                if (content.startsWith(productArea)) {
                    movie.productArea = content.substring(productArea.length).replaceIllegal()
                }
                if (content.startsWith(duration)) {
                    movie.duration = content.substring(duration.length).replaceIllegal()
                }
                if (content.startsWith(diretor)) {
                    movie.diretor = content.substring(diretor.length).replaceIllegal()
                }
            }
        }
    }

    companion object {
        val translateName = "译　　名"
        val titleName = "片　　名"
        val publishYear = "年　　代"
        val productArea = "产　　地"
        val type = "类　　别"
        val language = "语　　言"
        val subtitle = "字　　幕"
        val publishTime = "上映日期"
        val imdbGrade = "IMDb评分"
        val doubanGrade = "豆瓣评分"
        val fileType = "文件格式"
        val fileSize = "视频尺寸"
        val duration = "片　　长"
        val diretor = "导　　演"
        val actLead = "主　　演"
        val description = "简　　介"
    }
}

fun String.replaceIllegal(): String {
    return replace(Regex("[\\r\\n 　]"), "")
}
