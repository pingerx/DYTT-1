package com.bzh.domain.interactor;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

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
public abstract class UseCase {

    private final Scheduler workThreadExecutor;
    private final Scheduler postThreadExecutor;

    protected UseCase(Scheduler workThreadExecutor, Scheduler postThreadExecutor) {
        this.workThreadExecutor = workThreadExecutor;
        this.postThreadExecutor = postThreadExecutor;
    }

    private Subscription subscription = Subscriptions.empty();

    protected abstract Observable buildUseCaseObservable();

    @SuppressWarnings("unchecked")
    public void execute(Subscriber useCaseSubscribe) {
        subscription = buildUseCaseObservable()
                .subscribeOn(workThreadExecutor)
                .subscribeOn(postThreadExecutor)
                .observeOn(postThreadExecutor)
                .subscribe(useCaseSubscribe);
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
