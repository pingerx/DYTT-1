package com.bzh.dytt.data;

import java.util.HashMap;
import java.util.Map;

public class TypeConsts {

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

    public enum MovieCategory {
        NEW_MOVIE(1),
        CHINA_MOVIE(2),
        OUMEI_MOVIE(3),
        RIHAN_MOVIE(4),
        HOME_LATEST_MOVIE(5);

        private static Map<MovieCategory, Integer> mNextPageUrl = new HashMap<>();

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

        public String getDefaultUrl() {
            int index = 1;
            mNextPageUrl.put(this, index);
            return String.format(getUnformatUrl(), index);
        }

        public String getNextPageUrl() {
            int index = mNextPageUrl.get(this) + 1;
            mNextPageUrl.put(this, index);
            return String.format(getUnformatUrl(), index);
        }

        public String getUnformatUrl() {
            String result;
            switch (id) {
                case 1:
                    result = "dyzz/list_23_%d.html";
                    break;
                case 2:
                    result = "china/list_4_%d.html";
                    break;
                case 3:
                    result = "oumei/list_7_%d.html";
                    break;
                case 4:
                    result = "rihan/list_6_%d.html";
                    break;
                default:
                    result = "dyzz/list_23_%d.html";
            }
            return result;
        }
    }
}
