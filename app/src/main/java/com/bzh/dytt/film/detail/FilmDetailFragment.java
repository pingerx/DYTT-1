package com.bzh.dytt.film.detail;

import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bzh.common.utils.UIUtils;
import com.bzh.data.film.FilmDetailEntity;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.FragmentArgs;
import com.bzh.dytt.base.basic.FragmentContainerActivity;
import com.bzh.dytt.base.basic_pageswitch.PageFragment;
import com.bzh.dytt.base.basic_pageswitch.PagePresenter;
import com.jakewharton.rxbinding.support.design.widget.RxAppBarLayout;

import butterknife.Bind;
import butterknife.OnClick;

public class FilmDetailFragment extends PageFragment implements IFilmDetailView {

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

    @Bind(R.id.appbar)
    AppBarLayout appbar;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.translationName)
    TextView translationName;
    @Bind(R.id.years)
    TextView years;
    @Bind(R.id.country)
    TextView country;
    @Bind(R.id.category)
    TextView category;
    @Bind(R.id.language)
    TextView language;
    @Bind(R.id.showTime)
    TextView showTime;
    @Bind(R.id.director)
    TextView director;
    @Bind(R.id.leadingPlayers)
    TextView leadingPlayers;
    @Bind(R.id.description)
    TextView description;
    @Bind(R.id.previewImage)
    ImageView previewImage;

    private FilmDetailPresenter filmDetailPresenter;

    @Override
    protected PagePresenter initPresenter() {
        filmDetailPresenter = new FilmDetailPresenter(getBaseActivity(), this, this);
        return filmDetailPresenter;
    }

    @Override
    public void initFab() {
        fab.setOnClickListener(filmDetailPresenter);
    }

    @Override
    public void setFilmDetail(FilmDetailEntity filmDetailEntity) {
        collapsingToolbar.setTitle(filmDetailEntity.getTranslationName());
        translationName.setText(filmDetailEntity.getTitle());
        years.setText(filmDetailEntity.getYears());
        country.setText(filmDetailEntity.getCountry());
        category.setText(filmDetailEntity.getCategory());
        language.setText(filmDetailEntity.getLanguage());
        showTime.setText(filmDetailEntity.getShowTime());
        director.setText(filmDetailEntity.getDirector());
        leadingPlayers.setText(filmDetailEntity.getLeadingPlayers().get(0));
        description.setText(filmDetailEntity.getDescription());
        Glide.with(this)
                .load(filmDetailEntity.getCoverUrl())
                .into(filmPoster);
        Glide.with(this)
                .load(filmDetailEntity.getPreviewImage())
                .asBitmap()
                .into(new BitmapImageViewTarget(previewImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        float ratio = width * 1.0F / height;
                        float targetHeight = UIUtils.getScreenWidth() * 1.0F / ratio;

                        ViewGroup.LayoutParams params = previewImage.getLayoutParams();
                        params.height = (int) targetHeight;
                        previewImage.setLayoutParams(params);

                        previewImage.setImageBitmap(resource);
                    }
                });

    }


    @Override
    public void initToolbar() {
        getBaseActivity().setSupportActionBar(toolbar);
        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getBaseActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_film_detail;
    }

    @OnClick(R.id.fab)
    public void onClickFab(View v) {
        filmDetailPresenter.onClick(v);
    }
}
