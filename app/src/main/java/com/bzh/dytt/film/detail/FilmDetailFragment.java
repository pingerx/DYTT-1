package com.bzh.dytt.film.detail;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic.FragmentArgs;
import com.bzh.dytt.base.basic.FragmentContainerActivity;
import com.bzh.dytt.film.list.IFilmDetailView;

import butterknife.Bind;

public class FilmDetailFragment extends BaseFragment implements IFilmDetailView {

    public static final String FILM_URL = "FILM_URL";

    public static void launch(BaseActivity from, String url) {
        FragmentArgs fragmentArgs = new FragmentArgs();
        fragmentArgs.add(FILM_URL, url);
        FragmentContainerActivity.launch(from, FilmDetailFragment.class, fragmentArgs);
    }

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.film_poster)
    ImageView filmPoster;

    private FilmDetailPresenter filmDetailPresenter;

    @Override
    protected void initFragmentConfig() {
        filmDetailPresenter = new FilmDetailPresenter(getBaseActivity(), this, this);
        filmDetailPresenter.initFragmentConfig();
    }

    @Override
    public void initFab() {
        fab.setOnClickListener(filmDetailPresenter);
    }

    @Override
    public void setFilmPoster(String url) {
        Glide.with(this)
                .load(url)
                .into(filmPoster);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_film_detail;
    }

    @Override
    protected void onFirstUserVisible() {
        filmDetailPresenter.onFirstUserVisible();
    }

    @Override
    protected void onUserVisible() {
        filmDetailPresenter.onUserVisible();
    }

    @Override
    protected void onUserInvisible() {
        filmDetailPresenter.onUserInvisible();
    }
}
