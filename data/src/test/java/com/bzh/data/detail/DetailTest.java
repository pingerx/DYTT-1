package com.bzh.data.detail;

import android.support.annotation.NonNull;

import com.bzh.data.ApplicationTestCase;
import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.film.FilmDetailEntity;
import com.bzh.data.film.FilmNetWorkDataStore;
import com.bzh.data.film.IFilmService;
import com.bzh.data.repository.RetrofitManager;
import com.bzh.data.tv.ITvService;
import com.bzh.data.tv.TvNetWorkDataStore;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import rx.Subscriber;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-26<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class DetailTest extends ApplicationTestCase {

    private ITvService tvService;
    private TvNetWorkDataStore tvNetWorkDataStore;
    private Gson gson;
    private IFilmService filmService;
    private FilmNetWorkDataStore filmNetWorkDataStore;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        tvService = RetrofitManager.getInstance().getTvService();
        filmService = RetrofitManager.getInstance().getFilmService();
        tvNetWorkDataStore = new TvNetWorkDataStore(tvService);
        filmNetWorkDataStore = new FilmNetWorkDataStore(filmService);
    }

    @Test
    public void testGetFilmDetail() throws Exception {
        filmNetWorkDataStore.getFilmDetail("/html/gndy/dyzz/20160324/50538.html").subscribe(new Subscriber<FilmDetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onNext(FilmDetailEntity filmDetailEntity) {
                assertNotNull(filmDetailEntity);
                System.out.println(gson.toJson(filmDetailEntity));
            }
        });
    }

    @Test
    public void testGetTvDetail() throws Exception {
        tvNetWorkDataStore.getTvDetail("/html/tv/hytv/20160226/50311.html").subscribe(new Subscriber<FilmDetailEntity>() {
            @Override
            public void onCompleted() {
                System.out.println("");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("e = [" + e + "]");
            }

            @Override
            public void onNext(FilmDetailEntity filmDetailEntity) {
                System.out.println("filmDetailEntity = [" + filmDetailEntity + "]");
            }
        });
    }

    @Test
    public void testGetRIHanTvDetail() throws Exception {
        tvNetWorkDataStore.getTvDetail("/html/tv/rihantv/20160329/50567.html").subscribe(getRiHanSubscriber());
        tvNetWorkDataStore.getTvDetail("/html/tv/rihantv/20160329/50566.html").subscribe(getRiHanSubscriber());
        tvNetWorkDataStore.getTvDetail("/html/tv/rihantv/20160329/50565.html").subscribe(getRiHanSubscriber());
        tvNetWorkDataStore.getTvDetail("/html/tv/rihantv/20160328/50558.html").subscribe(getRiHanSubscriber());
        tvNetWorkDataStore.getTvDetail("/html/tv/rihantv/20160320/50506.html").subscribe(getRiHanSubscriber());
        tvNetWorkDataStore.getTvDetail("/html/tv/rihantv/20160123/50028.html").subscribe(getRiHanSubscriber());
        tvNetWorkDataStore.getTvDetail("/html/tv/rihantv/20150519/48105.html").subscribe(getRiHanSubscriber());
    }

    @NonNull
    private Subscriber<FilmDetailEntity> getRiHanSubscriber() {
        return new Subscriber<FilmDetailEntity>() {
            @Override
            public void onCompleted() {
                System.out.println("");
            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onNext(FilmDetailEntity filmDetailEntity) {
                System.out.println(gson.toJson(filmDetailEntity));
                assertNotNull(filmDetailEntity);
                assertNotNull(filmDetailEntity.getName());
                assertNotNull(filmDetailEntity.getSource());
                assertNotNull(filmDetailEntity.getCategory());
                assertNotNull(filmDetailEntity.getPlaytime());
                assertNotNull(filmDetailEntity.getScreenWriters().size() > 0);
                assertNotNull(filmDetailEntity.getDirectors());
                assertNotNull(filmDetailEntity.getLeadingPlayers());
                assertNotNull(filmDetailEntity.getEpisodeNumber());
                assertNotNull(filmDetailEntity.getDescription());
            }
        };
    }

    @Test
    public void testGet欧美TvDetail() throws Exception {
        tvNetWorkDataStore.getTvDetail("/html/tv/oumeitv/20160404/50634.html").subscribe(new Subscriber<FilmDetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onNext(FilmDetailEntity filmDetailEntity) {
                System.out.println(gson.toJson(filmDetailEntity));
                assertNotNull(filmDetailEntity);
                assertNotNull(filmDetailEntity.getTranslationName());
                assertNotNull(filmDetailEntity.getName());
                assertNotNull(filmDetailEntity.getYears());
                assertNotNull(filmDetailEntity.getCountry());
                assertNotNull(filmDetailEntity.getCategory());
                assertNotNull(filmDetailEntity.getLanguage());
                assertNotNull(filmDetailEntity.getPlaytime());
                assertNotNull(filmDetailEntity.getSource());
                assertNotNull(filmDetailEntity.getEpisodeNumber());
                assertNotNull(filmDetailEntity.getShowTime());
                assertNotNull(filmDetailEntity.getDirectors());
                assertNotNull(filmDetailEntity.getScreenWriters());
                assertNotNull(filmDetailEntity.getLeadingPlayers());
                assertNotNull(filmDetailEntity.getDescription());
                assertTrue(filmDetailEntity.getDirectors().size() > 0);
                assertTrue(filmDetailEntity.getScreenWriters().size() > 0);
                assertTrue(filmDetailEntity.getLeadingPlayers().size() > 0);
            }
        });
        tvNetWorkDataStore.getTvDetail("/html/tv/oumeitv/20160305/50383.html").subscribe(new Subscriber<FilmDetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onNext(FilmDetailEntity filmDetailEntity) {
                System.out.println(gson.toJson(filmDetailEntity));
                assertNotNull(filmDetailEntity);
                assertNotNull(filmDetailEntity.getTranslationName());
                assertNotNull(filmDetailEntity.getName());
                assertNotNull(filmDetailEntity.getYears());
                assertNotNull(filmDetailEntity.getCountry());
                assertNotNull(filmDetailEntity.getCategory());
                assertNotNull(filmDetailEntity.getLanguage());
                assertNotNull(filmDetailEntity.getPlaytime());
                assertNotNull(filmDetailEntity.getSource());
                assertNotNull(filmDetailEntity.getEpisodeNumber());
                assertNotNull(filmDetailEntity.getShowTime());
                assertNotNull(filmDetailEntity.getImdb());
                assertNotNull(filmDetailEntity.getDirectors());
                assertNotNull(filmDetailEntity.getScreenWriters());
                assertNotNull(filmDetailEntity.getLeadingPlayers());
                assertNotNull(filmDetailEntity.getDescription());
                assertTrue(filmDetailEntity.getDirectors().size() > 0);
                assertTrue(filmDetailEntity.getScreenWriters().size() > 0);
                assertTrue(filmDetailEntity.getLeadingPlayers().size() > 0);
            }
        });
        tvNetWorkDataStore.getTvDetail("/html/tv/oumeitv/20151113/49502.html").subscribe(new Subscriber<FilmDetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onNext(FilmDetailEntity filmDetailEntity) {
                System.out.println(gson.toJson(filmDetailEntity));
                assertNotNull(filmDetailEntity);
                assertNotNull(filmDetailEntity.getTranslationName());
                assertNotNull(filmDetailEntity.getName());
                assertNotNull(filmDetailEntity.getYears());
                assertNotNull(filmDetailEntity.getCountry());
                assertNotNull(filmDetailEntity.getCategory());
                assertNotNull(filmDetailEntity.getLanguage());
                assertNotNull(filmDetailEntity.getPlaytime());
                assertNotNull(filmDetailEntity.getEpisodeNumber());
                assertNotNull(filmDetailEntity.getShowTime());
                assertNotNull(filmDetailEntity.getImdb());
                assertNotNull(filmDetailEntity.getDirectors());
                assertNotNull(filmDetailEntity.getScreenWriters());
                assertNotNull(filmDetailEntity.getLeadingPlayers());
                assertNotNull(filmDetailEntity.getDescription());
                assertTrue(filmDetailEntity.getDirectors().size() > 0);
                assertTrue(filmDetailEntity.getScreenWriters().size() > 0);
                assertTrue(filmDetailEntity.getLeadingPlayers().size() > 0);
            }
        });
    }


}