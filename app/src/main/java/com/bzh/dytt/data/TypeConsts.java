package com.bzh.dytt.data;

public class TypeConsts {

    public enum MovieCategory {
        NEW_MOVIE(1),
        CHINA_MOVIE(2),
        OUMEI_MOVIE(3),
        RIHAN_MOVIE(4),
        HOME_LATEST_MOVIE(5);

        private int id;

        MovieCategory(int id) {
            this.id = id;
        }

        public String getTitle() {
            switch (id) {
                case 1:
                    return "最新电影";
                case 2:
                    return "国内电影";
                case 3:
                    return "欧美电影";
                case 4:
                    return "日韩电影";
                default:
                    return "未知";
            }
        }
    }

    public static String getMoviePageByCategory(MovieCategory movieCategory) {
        switch (movieCategory) {
            case NEW_MOVIE:
                return "/html/gndy/dyzz/index.html";
            case CHINA_MOVIE:
                return "/html/gndy/china/index.html";
            case OUMEI_MOVIE:
                return "/html/gndy/oumei/index.html";
            case RIHAN_MOVIE:
                return "/html/gndy/rihan/index.html";
        }
        return null;
    }

    public static String getCategoryTitle(MovieCategory movieCategory) {
        switch (movieCategory) {
            case NEW_MOVIE:
                return "最新电影";
            case CHINA_MOVIE:
                return "国内电影";
            case OUMEI_MOVIE:
                return "欧美电影";
            case RIHAN_MOVIE:
                return "日韩电影";
        }
        return null;
    }

}
