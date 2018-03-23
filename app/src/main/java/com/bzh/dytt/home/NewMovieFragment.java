package com.bzh.dytt.home;


import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bzh.dytt.R;
import com.bzh.dytt.data.VideoDetail;
import com.bzh.dytt.data.network.Resource;
import com.bzh.dytt.task.FetchVideoDetailTask;
import com.bzh.dytt.util.GlideApp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewMovieFragment extends SingleListFragment<VideoDetail> {

    public static NewMovieFragment newInstance() {
        return new NewMovieFragment();
    }

    @Inject
    ViewModelProvider.Factory mFactory;

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new MovieListAdapter(this.getContext());
    }

    @Override
    protected void setListData(List<VideoDetail> listData) {
        ((MovieListAdapter) mAdapter).setItems(listData);
    }

    @Override
    protected LiveData<Resource<List<VideoDetail>>> getLiveData() {
        return ((NewMovieViewModel) mViewModel).getNewMovieList();
    }


    @Override
    protected ViewModel createViewModel() {
        return ViewModelProviders.of(this, mFactory).get(NewMovieViewModel.class);
    }

    @Override
    protected void doRefresh() {
        ((NewMovieViewModel) mViewModel).getNewMovieList(true).observe(this, mObserver);
    }
}
