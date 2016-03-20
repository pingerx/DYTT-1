package com.bzh.dytt.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.dytt.ui.activity.base.BaseActivity;
import com.bzh.dytt.ui.config.UIConfig;
import com.bzh.dytt.eventbus.EventCenter;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-20<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    protected BaseActivity baseActivity;
    private FragmentConfig fragmentConfig;
    private boolean isPrepared;
    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof BaseActivity) {
            baseActivity = (BaseActivity) getActivity();
        }

        fragmentConfig = new FragmentConfig();
        initUIConfig(fragmentConfig);
        if (fragmentConfig.isApplyButterKnife) {
            ButterKnife.bind(this, view);
        }

        if (fragmentConfig.isApplyEventBus) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (fragmentConfig.isApplyButterKnife) {
            ButterKnife.unbind(this);
        }

        if (fragmentConfig.isApplyEventBus) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void initUIConfig(FragmentConfig fragmentConfig) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        Log.d(TAG, "onResume() called with: " + "getUserVisibleHint() = [" + getUserVisibleHint() + "]");
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called with: " + "getUserVisibleHint() = [" + getUserVisibleHint() + "]");
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint() called with: " + "isVisibleToUser = [" + isVisibleToUser + "]");
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                Log.d(TAG, "onUserVisible() called with: ");
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                Log.d(TAG, "onUserInvisible() called with: ");
                onUserInvisible();
            }
        }
    }

    protected synchronized void initPrepare() {
        Log.d(TAG, "initPrepare() called with: " + "");
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    protected void onEventMainThread(EventCenter eventCenter) {
        if (eventCenter != null) {
            onEventComing(eventCenter);
        }
    }

    protected void onEventComing(EventCenter eventCenter) {
    }

    protected abstract int getContentView();

    /**
     * when fragment is visible for the first time, here we can do some initialized work or refresh data only once
     */
    protected abstract void onFirstUserVisible();

    /**
     * when fragment is invisible for the first time
     */
    protected void onFirstUserInvisible() {
        Log.d(TAG, "onFirstUserInvisible() called with: " + "");
        // here we do not recommend do something
    }

    /**
     * this method like the fragment's lifecycle method onResume()
     */
    protected abstract void onUserVisible();

    /**
     * this method like the fragment's lifecycle method onPause()
     */
    protected abstract void onUserInvisible();

    protected final static class FragmentConfig extends UIConfig {

    }
}
