package com.bzh.data.film.datasource;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.bzh.common.utils.SystemUtils;
import com.bzh.data.exception.TaskException;
import com.bzh.data.film.entity.FilmDetailEntity;
import com.bzh.data.film.entity.FilmEntity;
import com.bzh.data.service.RetrofitManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　电影<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class FilmNetWorkDataStore implements IFilmDataStore {

    public static final String NAME = "◎片名";
    public static final String YEARS = "◎年代";
    public static final String COUNTRY = "◎国家";
    public static final String CATEGORY = "◎类别";
    public static final String LANGUAGE = "◎语言";
    public static final String SUBTITLE = "◎字幕";
    public static final String FILEFORMAT = "◎文件格式";
    public static final String VIDEOSIZE = "◎视频尺寸";
    public static final String FILESIZE = "◎文件大小";
    public static final String SHOWTIME = "◎片长";
    public static final String DIRECTOR = "◎导演";
    public static final String LEADINGPLAYERS = "◎主演";
    public static final String DESCRIPTION = "◎简介";
    public static final String TRANSLATIONNAME = "◎译名";

    private final RetrofitManager retrofitManager;

    private Func1<String, ArrayList<FilmEntity>> transformFilmEntity = new Func1<String, ArrayList<FilmEntity>>() {
        @Override
        public ArrayList<FilmEntity> call(String s) {
            Document document = Jsoup.parse(s);
            Elements elements = document.select("div.co_content8").select("ul");
            Elements hrefs = elements.select("a[href]");

            Pattern pattern = Pattern.compile("^\\[.*\\]$");
            ArrayList<FilmEntity> filmEntities = new ArrayList<>();
            for (Element element : hrefs) {
                String fullName = element.text();
                if (pattern.matcher(fullName).matches()) {
                    continue;
                }
                FilmEntity filmEntity = new FilmEntity();
                filmEntity.setName(fullName.substring(0, fullName.lastIndexOf("》") + 1));
                filmEntity.setUrl(element.attr("href"));
                filmEntities.add(filmEntity);
            }

            Elements fonts = elements.select("font");
            for (int i = 0; i < fonts.size(); i++) {
                String fullName = fonts.get(i).text();
                filmEntities.get(i).setPublishTime(fullName.substring(fullName.indexOf("：") + 1, fullName.indexOf("点击")).trim());
            }
            return filmEntities;
        }
    };

    private Func1<String, FilmDetailEntity> transformHtmlToEntity = new Func1<String, FilmDetailEntity>() {
        @Override
        public FilmDetailEntity call(String s) {
            FilmDetailEntity entity = new FilmDetailEntity();
            Document document = Jsoup.parse(s);
            String html = document.select("div.co_content8").select("ul").toString();
            html = html.substring(0, html.indexOf("</table>"));

            String publishTime = getPublishTime(html);
            String title = document.select("div.co_area2").select("div.title_all").select("font").first().text();
            String coverUrl = document.select("div.co_content8").select("ul").select("img").first().attr("src");
            String previewImage = document.select("div.co_content8").select("ul").select("img").last().attr("src");
            String downloadUrl = document.select("div.co_content8").select("ul").select("a").first().attr("href");

            Pattern pattern = Pattern.compile("◎译　　名.*<br>");
            Matcher matcher = pattern.matcher(html);
            if (matcher.find()) {
                String result = matcher.group();
                String[] split = result.split("<br>");
                for (int i = 0; i < split.length; i++) {
                    String info = split[i].replaceAll("　", "").trim();
                    if (info.contains(NAME)) {
                        info = info.substring(info.indexOf(NAME) + NAME.length());
                        entity.setName(info);
                    } else if (info.contains(TRANSLATIONNAME)) {
                        info = info.substring(info.indexOf(TRANSLATIONNAME) + TRANSLATIONNAME.length());
                        entity.setTranslationName(info);
                    } else if (info.contains(YEARS)) {
                        info = info.substring(info.indexOf(YEARS) + YEARS.length());
                        entity.setYears(info);
                    } else if (info.contains(COUNTRY)) {
                        info = info.substring(info.indexOf(COUNTRY) + COUNTRY.length());
                        entity.setCountry(info);
                    } else if (info.contains(CATEGORY)) {
                        info = info.substring(info.indexOf(CATEGORY) + CATEGORY.length());
                        entity.setCategory(info);
                    } else if (info.contains(LANGUAGE)) {
                        info = info.substring(info.indexOf(LANGUAGE) + LANGUAGE.length());
                        entity.setLanguage(info);
                    } else if (info.contains(SUBTITLE)) {
                        info = info.substring(info.indexOf(SUBTITLE) + SUBTITLE.length());
                        entity.setSubtitle(info);
                    } else if (info.contains(FILEFORMAT)) {
                        info = info.substring(info.indexOf(FILEFORMAT) + FILEFORMAT.length());
                        entity.setFileFormat(info);
                    } else if (info.contains(VIDEOSIZE)) {
                        info = info.substring(info.indexOf(VIDEOSIZE) + VIDEOSIZE.length());
                        entity.setVideoSize(info);
                    } else if (info.contains(FILESIZE)) {
                        info = info.substring(info.indexOf(FILESIZE) + FILESIZE.length());
                        entity.setFileSize(info);
                    } else if (info.contains(SHOWTIME)) {
                        info = info.substring(info.indexOf(SHOWTIME) + SHOWTIME.length());
                        entity.setShowTime(info);
                    } else if (info.contains(DIRECTOR)) {
                        info = info.substring(info.indexOf(DIRECTOR) + DIRECTOR.length());
                        entity.setDirector(info);
                    } else if (info.contains(LEADINGPLAYERS)) {
                        info = info.substring(info.indexOf(LEADINGPLAYERS) + LEADINGPLAYERS.length());
                        if (entity.getLeadingPlayers() == null) {
                            entity.setLeadingPlayers(new ArrayList<String>());
                        }
                        entity.getLeadingPlayers().add(info);
                    } else if (i == split.length - 1) {
                        entity.setDescription(info);
                    } else {
                        if (entity.getLeadingPlayers() == null) {
                            entity.setLeadingPlayers(new ArrayList<String>());
                        }
                        entity.getLeadingPlayers().add(info);
                    }
                }
            }

            entity.setTitle(title);
            entity.setPublishTime(publishTime);
            entity.setCoverUrl(coverUrl);
            entity.setPreviewImage(previewImage);
            entity.setDownloadUrl(downloadUrl);
            return entity;
        }
    };


    public FilmNetWorkDataStore(RetrofitManager retrofitManager) {
        this.retrofitManager = retrofitManager;
    }


    @Override
    public Observable<ArrayList<FilmEntity>> getDomestic(@IntRange(from = 1, to = 87) int index) {
        return getNewWorkObservable(retrofitManager.getFilmService()
                .getDomestic(index));
    }

    /**
     * 获取最新电影列表
     *
     * @param index 索引范围为1 ~ 131
     */
    @Override
    public Observable<ArrayList<FilmEntity>> getNewest(@IntRange(from = 1, to = 131) final int index) {
        return getNewWorkObservable(retrofitManager.getFilmService()
                .getNewest(index));
    }

    @Override
    public Observable<ArrayList<FilmEntity>> getEuropeAmerica(@IntRange(from = 1, to = 147) int index) {
        return getNewWorkObservable(retrofitManager.getFilmService()
                .getEuropeAmerica(index));
    }

    @Override
    public Observable<ArrayList<FilmEntity>> getJapanSouthKorea(@IntRange(from = 1, to = 25) int index) {
        return getNewWorkObservable(retrofitManager.getFilmService()
                .getJapanSouthKorea(index));
    }

    /**
     * network processing
     */
    @NonNull
    private Observable<ArrayList<FilmEntity>> getNewWorkObservable(final Observable<ResponseBody> observable) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<FilmEntity>>() {
            @Override
            public void call(Subscriber<? super ArrayList<FilmEntity>> subscriber) {
                if (SystemUtils.getNetworkType() == SystemUtils.NETWORK_TYPE_NONE) {
                    subscriber.onError(new TaskException(TaskException.ERROR_NONE_NETWORK));
                } else {
                    try {
                        observable.map(transformCharset)
                                .map(transformFilmEntity)
                                .subscribe(subscriber);
                    } catch (TaskException e) {
                        subscriber.onError(e);
                    }
                }
            }
        });
    }

    /**
     * 获取某电影的详细信息
     */
    @Override
    public Observable<FilmDetailEntity> getFilmDetail(final String filmStr) {
        return Observable.create(new Observable.OnSubscribe<FilmDetailEntity>() {
            @Override
            public void call(Subscriber<? super FilmDetailEntity> subscriber) {
                if (SystemUtils.getNetworkType() == SystemUtils.NETWORK_TYPE_NONE) {
                    subscriber.onError(new TaskException(TaskException.ERROR_NONE_NETWORK));
                } else {
                    try {
                        retrofitManager
                                .getFilmService()
                                .getFilmDetail(filmStr)
                                .map(transformCharset)
                                .map(transformHtmlToEntity)
                                .subscribe(subscriber);
                    } catch (TaskException e) {
                        subscriber.onError(e);
                    }
                }
            }
        });
    }

    @NonNull
    private String getPublishTime(String html) {
        String publishTime;
        Pattern pattern = Pattern.compile("发布时间：.*&");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            publishTime = matcher.group();
            publishTime = publishTime.substring(publishTime.indexOf("：") + 1, publishTime.length() - 1);
        } else {
            publishTime = "";
        }
        return publishTime;
    }

}
