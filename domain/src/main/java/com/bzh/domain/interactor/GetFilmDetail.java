package com.bzh.domain.interactor;

import com.bzh.data.repository.Repository;

import rx.Observable;
import rx.Scheduler;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-19<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class GetFilmDetail extends UseCase {

    private final String filmUrl;

    protected GetFilmDetail(String filmUrl, Scheduler workThreadExecutor, Scheduler postThreadExecutor) {
        super(workThreadExecutor, postThreadExecutor);
        this.filmUrl = filmUrl;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return Repository.getInstance().getFilmDetail(filmUrl);
    }
}
