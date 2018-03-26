package com.bzh.dytt.home;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.RecyclerView;

import com.bzh.dytt.data.VideoDetail;
import com.bzh.dytt.data.network.Resource;

import java.util.List;

import javax.inject.Inject;

public class NewMovieFragment extends SingleListFragment<VideoDetail> {

    @Inject
    ViewModelProvider.Factory mFactory;

    public static NewMovieFragment newInstance() {
        return new NewMovieFragment();
    }

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
        ((NewMovieViewModel) mViewModel).refresh();
    }
}
