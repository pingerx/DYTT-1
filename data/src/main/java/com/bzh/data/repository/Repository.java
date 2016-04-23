package com.bzh.data.repository;

import android.support.annotation.IntRange;

import com.bzh.data.basic.MeiZiEntity;
import com.bzh.data.comic.ComicNetWorkData;
import com.bzh.data.comic.IComicDataStore;
import com.bzh.data.comic.IComicService;
import com.bzh.data.film.DetailEntity;
import com.bzh.data.film.FilmNetWorkDataStore;
import com.bzh.data.film.IFilmDataStore;
import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.film.IFilmService;
import com.bzh.data.game.GameNetWorkDataStore;
import com.bzh.data.game.IGameDataStore;
import com.bzh.data.game.IGameService;
import com.bzh.data.meizi.IMeiZiDataStore;
import com.bzh.data.meizi.IMeiZiService;
import com.bzh.data.meizi.MeiZiNetWorkDataStore;
import com.bzh.data.tv.ITvDataStore;
import com.bzh.data.tv.TvNetWorkDataStore;
import com.bzh.data.tv.ITvService;
import com.bzh.data.variety.IVarietyDataStore;
import com.bzh.data.variety.IVarietyService;
import com.bzh.data.variety.VarietyNetWorkDataStore;

import java.util.ArrayList;

import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class Repository implements IFilmDataStore, ITvDataStore, IVarietyDataStore, IComicDataStore, IGameDataStore, IMeiZiDataStore {

    private static volatile Repository repository;
    private static volatile IFilmService filmService;
    private static volatile ITvService tvService;
    private static volatile IVarietyService varietyService;
    private static volatile IComicService iComicService;
    private static volatile IGameService iGameService;
    private static volatile IMeiZiService iMeiZiService;

    public static Repository getInstance() {
        Repository tmp = repository;
        if (tmp == null) {
            synchronized (Repository.class) {
                tmp = repository;
                if (tmp == null) {
                    tmp = new Repository();
                    repository = tmp;
                    filmService = RetrofitManager.getInstance().getFilmService();
                    tvService = RetrofitManager.getInstance().getTvService();
                    varietyService = RetrofitManager.getInstance().getVarietyService();
                    iComicService = RetrofitManager.getInstance().getComicService();
                    iGameService = RetrofitManager.getInstance().getGameService();
                    iMeiZiService = RetrofitManager.getInstance().getiMeiZiService();
                }
            }
        }
        return tmp;
    }

    private Repository() {

    }

    private FilmNetWorkDataStore filmNetWorkDataStore;
    private TvNetWorkDataStore tvNetWorkDataStore;
    private VarietyNetWorkDataStore varietyNetWorkDataStore;
    private ComicNetWorkData comicNetWorkData;
    private GameNetWorkDataStore gameNetWorkDataStore;
    private MeiZiNetWorkDataStore meiZiNetWorkDataStore;

    private IMeiZiDataStore getMeiZiDataStore() {
        if (meiZiNetWorkDataStore == null) {
            meiZiNetWorkDataStore = new MeiZiNetWorkDataStore(iMeiZiService);
        }
        return meiZiNetWorkDataStore;
    }

    private IFilmDataStore getFilmDataStore() {
        if (filmNetWorkDataStore == null) {
            filmNetWorkDataStore = new FilmNetWorkDataStore(filmService);
        }
        return filmNetWorkDataStore;
    }

    private ITvDataStore getTvDataStore() {
        if (tvNetWorkDataStore == null) {
            tvNetWorkDataStore = new TvNetWorkDataStore(tvService);
        }
        return tvNetWorkDataStore;
    }

    private IVarietyDataStore getVarietyDataStore() {
        if (varietyNetWorkDataStore == null) {
            varietyNetWorkDataStore = new VarietyNetWorkDataStore(varietyService);
        }
        return varietyNetWorkDataStore;
    }

    private ComicNetWorkData getComicDataStore() {
        if (comicNetWorkData == null) {
            comicNetWorkData = new ComicNetWorkData(iComicService);
        }
        return comicNetWorkData;
    }

    private GameNetWorkDataStore getGameDataStore() {
        if (gameNetWorkDataStore == null) {
            gameNetWorkDataStore = new GameNetWorkDataStore(iGameService);
        }
        return gameNetWorkDataStore;
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getDomestic(@IntRange(from = 1, to = 87) int index) {
        return getFilmDataStore().getDomestic(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getNewest(@IntRange(from = 1, to = 131) int index) {
        return getFilmDataStore().getNewest(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getEuropeAmerica(@IntRange(from = 1, to = 147) int index) {
        return getFilmDataStore().getEuropeAmerica(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getJapanSouthKorea(@IntRange(from = 1, to = 25) int index) {
        return getFilmDataStore().getJapanSouthKorea(index);
    }

    @Override
    public Observable<DetailEntity> getFilmDetail(String filmStr) {
        return getFilmDataStore().getFilmDetail(filmStr);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getChineseDomesticTv(@IntRange(from = 1, to = 31) int index) {
        return getTvDataStore().getChineseDomesticTv(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getHKTTv(@IntRange(from = 1, to = 5) int index) {
        return getTvDataStore().getHKTTv(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getChineseTv(@IntRange(from = 1, to = 33) int index) {
        return getTvDataStore().getChineseTv(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getJapanSouthKoreaTV(@IntRange(from = 1, to = 45) int index) {
        return getTvDataStore().getJapanSouthKoreaTV(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getEuropeAmericaTV(@IntRange(from = 1, to = 22) int index) {
        return getTvDataStore().getEuropeAmericaTV(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> get2013NewestChineseVariety(@IntRange(from = 1, to = 98) int index) {
        return getVarietyDataStore().get2013NewestChineseVariety(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> get2013ChineseVariety(@IntRange(from = 1, to = 46) int index) {
        return getVarietyDataStore().get2013ChineseVariety(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> get2013HKTVariety(@IntRange(from = 1, to = 44) int index) {
        return getVarietyDataStore().get2013HKTVariety(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> get2013OtherVariety(@IntRange(from = 1, to = 8) int index) {
        return getVarietyDataStore().get2013OtherVariety(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> get2009ChineseVariety(@IntRange(from = 1, to = 69) int index) {
        return getVarietyDataStore().get2009ChineseVariety(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getComic(@IntRange(from = 1, to = 45) int index) {
        return getComicDataStore().getComic(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getNewComic(@IntRange(from = 1, to = 8) int index) {
        return getComicDataStore().getNewComic(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getSSComic(@IntRange(from = 1, to = 5) int index) {
        return getComicDataStore().getSSComic(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getOtherComic(@IntRange(from = 1, to = 3) int index) {
        return getComicDataStore().getOtherComic(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getGCComic(@IntRange(from = 1, to = 2) int index) {
        return getComicDataStore().getGCComic(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getHZWComic(@IntRange(from = 1, to = 5) int index) {
        return getComicDataStore().getHZWComic(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getHYComic(@IntRange(from = 1, to = 5) int index) {
        return getComicDataStore().getHYComic(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getGame(@IntRange(from = 1, to = 369) int index) {
        return getGameDataStore().getGame(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getHotGame(@IntRange(from = 1, to = 8) int index) {
        return getGameDataStore().getHotGame(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getClassicGame(@IntRange(from = 1, to = 199) int index) {
        return getGameDataStore().getClassicGame(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getNewestGame(@IntRange(from = 1, to = 146) int index) {
        return getGameDataStore().getNewestGame(index);
    }


    @Override
    public Observable<ArrayList<MeiZiEntity>> getMeiZi(int index) {
        return getMeiZiDataStore().getMeiZi(index);
    }
}
