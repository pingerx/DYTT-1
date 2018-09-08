package com.bzh.dytt.util

import com.bzh.dytt.vo.MovieDetail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailParse @Inject constructor(
//        private val dongManParse: DongManParse,
//        private val zongYiParse: ZongYiParse,
//        private val normalParse: NormalParse,
//        private val riHanTVParase: RiHanTVParase
) {


    fun parse(movie: MovieDetail) {
//        when (movie.categoryId) {
//            HomeViewModel.HomeMovieType.MOVIE_DONGMAN.type -> {
//                dongManParse.parse(movie)
//            }
//            HomeViewModel.HomeMovieType.MOVIE_ZENGYIV.type -> {
//                zongYiParse.parse(movie)
//            }
//            HomeViewModel.HomeMovieType.MOVIE_RIHAN_TV.type -> {
//                riHanTVParase.parse(movie)
//            }
//            else -> {
//                normalParse.parse(movie)
//            }
//        }
    }


    companion object {
        private const val TAG = "MovieDetailParse"


    }
}
//
//@Singleton
//class DongManParse @Inject constructor() {
//
//    fun parse(movie: MovieDetail) {
//        movie.description = movie.content
//    }
//
//    companion object {
//        private const val TAG = "MovieDetailParse"
//    }
//}
//
//@Singleton
//class ZongYiParse @Inject constructor() {
//
//    fun parse(movie: MovieDetail) {
//        movie.description = movie.content
//    }
//
//    companion object {
//        private const val TAG = "ZongYiParse"
//    }
//}
//
//@Singleton
//class RiHanTVParase @Inject constructor() {
//
//    companion object {
//        private const val TAG = "NormalParse"
//    }
//
//    fun parse(movie: MovieDetail) {
//        if (movie.name != null && movie.name.isNotEmpty()) {
//            val startIndex = movie.name.indexOf("《")
//            val lastIndex = movie.name.lastIndexOf("》")
//            if (startIndex != -1 && lastIndex != -1) {
//                movie.simpleName = movie.name.substring(startIndex + 1, lastIndex)
//            }
//        }
//        if (movie.content != null && movie.content.isNotEmpty()) {
//            val contents = movie.content.split("◎")
////            for (content: String in contents) {
////                if (content.startsWith(translateName)) {
////                    movie.translateName = content.substring(translateName.length).replaceIllegal().split("/")[0]
////                }
////                if (content.startsWith(titleName)) {
////                    movie.titleName = content.substring(titleName.length).replaceIllegal().split("/")[0]
////                }
////                if (content.startsWith(diretor)) {
////                    movie.directorName = content.substring(diretor.length).replaceIllegal()
////                }
////                if (content.startsWith(description)) {
////                    movie.description = content.substring(description.length).replaceIllegal()
////                }
////                if (content.startsWith(type)) {
////                    movie.type = content.substring(type.length).replaceIllegal()
////                }
////                if (content.startsWith(language)) {
////                    movie.language = content.substring(language.length).replaceIllegal()
////                }
////                if (content.startsWith(productArea)) {
////                    movie.productArea = content.substring(productArea.length).replaceIllegal()
////                }
////                if (content.startsWith(duration)) {
////                    movie.duration = content.substring(duration.length).replaceIllegal()
////                }
////                if (content.startsWith(diretor)) {
////                    movie.director = content.substring(diretor.length).replaceIllegal()
////                }
////            }
//        }
//    }
//}
//
//
//@Singleton
//class NormalParse @Inject constructor() {
//
//    companion object {
//        private const val TAG = "NormalParse"
//
//        val translateName = "译　　名"
//        val titleName = "片　　名"
//        val publishYear = "年　　代"
//        val productArea = "产　　地"
//        val type = "类　　别"
//        val language = "语　　言"
//        val subtitle = "字　　幕"
//        val publishTime = "上映日期"
//        val imdbGrade = "IMDb评分"
//        val doubanGrade = "豆瓣评分"
//        val fileType = "文件格式"
//        val fileSize = "视频尺寸"
//        val duration = "片　　长"
//        val diretor = "导　　演"
//        val actLead = "主　　演"
//        val description = "简　　介"
//    }
//
//    fun parse(movie: MovieDetail) {
//        if (movie.name != null && movie.name.isNotEmpty()) {
//            val startIndex = movie.name.indexOf("《")
//            val lastIndex = movie.name.lastIndexOf("》")
//            if (startIndex != -1 && lastIndex != -1) {
//                movie.simpleName = movie.name.substring(startIndex + 1, lastIndex)
//            }
//        }
//        if (movie.content != null && movie.content.isNotEmpty()) {
//            val contents = movie.content.split("◎")
//            for (content: String in contents) {
//                if (content.startsWith(translateName)) {
//                    movie.translateName = content.substring(translateName.length).replaceIllegal().split("/")[0]
//                }
//                if (content.startsWith(titleName)) {
//                    movie.titleName = content.substring(titleName.length).replaceIllegal().split("/")[0]
//                }
//                if (content.startsWith(imdbGrade)) {
//                    val imdb = content.substring(imdbGrade.length).replaceIllegal()
//                    val substring = imdb.substring(0, imdb.indexOf("/"))
//                    if (substring.isNotEmpty()) {
//                        movie.imdbGrade = substring.toFloat()
//                    }
//                }
//                if (content.startsWith(doubanGrade)) {
//                    val douban = content.substring(doubanGrade.length).replaceIllegal()
//                    val substring = douban.substring(0, douban.indexOf("/"))
//                    if (substring.isNotEmpty()) {
//                        movie.doubanGrade = substring.toFloat()
//                    }
//                }
//                if (content.startsWith(diretor)) {
//                    movie.directorName = content.substring(diretor.length).replaceIllegal()
//                }
//                if (content.startsWith(description)) {
//                    movie.description = content.substring(description.length).replaceIllegal()
//                }
//                if (content.startsWith(type)) {
//                    movie.type = content.substring(type.length).replaceIllegal()
//                }
//                if (content.startsWith(language)) {
//                    movie.language = content.substring(language.length).replaceIllegal()
//                }
//                if (content.startsWith(productArea)) {
//                    movie.productArea = content.substring(productArea.length).replaceIllegal()
//                }
//                if (content.startsWith(duration)) {
//                    movie.duration = content.substring(duration.length).replaceIllegal()
//                }
//                if (content.startsWith(diretor)) {
//                    movie.director = content.substring(diretor.length).replaceIllegal()
//                }
//            }
//        }
//    }
//}
//
//fun String.replaceIllegal(): String {
//    return replace(Regex("[\\r\\n 　]"), "")
//}
