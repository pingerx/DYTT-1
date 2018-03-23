package com.bzh.dytt;


import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

public class BaseViewModel extends ViewModel {

    protected DataRepository mDataRepository;

    @Inject
    public BaseViewModel(DataRepository repository) {
        mDataRepository = repository;
    }

}
