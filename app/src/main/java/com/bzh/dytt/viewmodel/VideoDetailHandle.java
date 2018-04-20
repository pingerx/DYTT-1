package com.bzh.dytt.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import com.bzh.dytt.DataRepository;
import com.bzh.dytt.data.entity.VideoDetail;

public class VideoDetailHandle implements Observer<VideoDetail> {

    private MutableLiveData<VideoDetail> mLiveData = new MutableLiveData<>();

    private DataRepository mDataRepository;

    public VideoDetailHandle(DataRepository dataRepository) {
        mDataRepository = dataRepository;
    }

    @MainThread
    public void register() {
        if (mLiveData != null) {
            mLiveData.observeForever(this);
        }
    }

    @MainThread
    public void unregister() {
        if (mLiveData != null) {
            mLiveData.removeObserver(this);
            mLiveData = null;
        }
    }

    @MainThread
    @Override
    public void onChanged(@Nullable VideoDetail videoDetail) {
        if (videoDetail != null && !videoDetail.isValidVideoItem()) {
            mDataRepository.parseVideoDetail(videoDetail);
        }
    }

    @MainThread
    public MutableLiveData<VideoDetail> getVideoDetail() {
        return mLiveData;
    }
}
