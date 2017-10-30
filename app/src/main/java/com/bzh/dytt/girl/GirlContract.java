package com.bzh.dytt.girl;


import com.bzh.dytt.IPresenter;
import com.bzh.dytt.IView;

public interface GirlContract {

    interface View extends IView<Presenter> {

    }

    interface Presenter extends IPresenter {

    }
}
