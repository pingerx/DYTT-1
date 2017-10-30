package com.bzh.dytt.girl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.dytt.BaseFragment;
import com.bzh.dytt.R;

public class GirlFragment extends BaseFragment<GirlContract.Presenter> implements GirlContract.View {

    public static GirlFragment newInstance() {
        return new GirlFragment();
    }


    @Override
    protected void doCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = new GirlPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.girl_page, container, false);
    }

}
