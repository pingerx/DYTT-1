package com.bzh.dytt.film.detail;

import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    // 片名
    @Bind(R.id.layout_name)
    LinearLayout layout_name;
    @Bind(R.id.name)
    TextView name;

    // 译名
    @Bind(R.id.layout_translationName)
    LinearLayout layout_translationName;
    @Bind(R.id.translationName)
    TextView translationName;

    // 年代
    @Bind(R.id.layout_years)
    LinearLayout layout_years;
    @Bind(R.id.years)
    TextView years;

    // 国家
    @Bind(R.id.layout_country)
    LinearLayout layout_country;
    @Bind(R.id.country)
    TextView country;

    // 类型
    @Bind(R.id.layout_category)
    LinearLayout layout_category;
    @Bind(R.id.category)
    TextView category;

    // 语言
    @Bind(R.id.layout_language)
    LinearLayout layout_language;
    @Bind(R.id.language)
    TextView language;

    // 字幕
    @Bind(R.id.layout_subtitle)
    LinearLayout layout_subtitle;
    @Bind(R.id.subtitle)
    TextView subtitle;

    // 字幕
    @Bind(R.id.layout_showTime)
    LinearLayout layout_showTime;
    @Bind(R.id.showTime)
    TextView showTime;

    // 集数
    @Bind(R.id.layout_episodeNumber)
    LinearLayout layout_episodeNumber;
    @Bind(R.id.episodeNumber)
    TextView episodeNumber;

    // 来源
    @Bind(R.id.layout_source)
    LinearLayout layout_source;
    @Bind(R.id.source)
    TextView source;

    // IMDB评分
    @Bind(R.id.layout_imdb)
    LinearLayout layout_imdb;
    @Bind(R.id.imdb)
    TextView imdb;

    // 发布时间
    @Bind(R.id.layout_publishTime)
    LinearLayout layout_publishTime;
    @Bind(R.id.publishTime)
    TextView publishTime;

    // 上映时间
    @Bind(R.id.layout_playtime)
    LinearLayout layout_playtime;
    @Bind(R.id.playtime)
    TextView playtime;

    // 视频格式
    @Bind(R.id.layout_fileFormat)
    LinearLayout layout_fileFormat;
    @Bind(R.id.fileFormat)
    TextView fileFormat;

    // 视频尺寸
    @Bind(R.id.layout_videoSize)
    LinearLayout layout_videoSize;
    @Bind(R.id.videoSize)
    TextView videoSize;

    // 文件大小
    @Bind(R.id.layout_fileSize)
    LinearLayout layout_fileSize;
    @Bind(R.id.fileSize)
    TextView fileSize;

    // 导演
    @Bind(R.id.layout_director)
    LinearLayout layout_director;
    @Bind(R.id.director)
    TextView director;

    // 编辑
    @Bind(R.id.layout_screenWriters)
    LinearLayout layout_screenWriters;
    @Bind(R.id.screenWriters)
    TextView screenWriters;

    // 主演
    @Bind(R.id.layout_leadingPlayers)
    LinearLayout layout_leadingPlayers;
    @Bind(R.id.leadingPlayers)
    TextView leadingPlayers;

    // 描述
    @Bind(R.id.layout_description)
    LinearLayout layout_description;
    @Bind(R.id.description)
    TextView description;

    // 预览
    @Bind(R.id.layout_previewImage)
    LinearLayout layout_previewImage;
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

    public void setText(TextView textView, LinearLayout layout, String str) {
        textView.setText(TextUtils.isEmpty(str) ? "" : str);
        layout.setVisibility(TextUtils.isEmpty(str) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setFilmDetail(FilmDetailEntity filmDetailEntity) {
        collapsingToolbar.setTitle(filmDetailEntity.getTranslationName());

        setText(name, layout_name, filmDetailEntity.getName());                  // 1. 名字
        setText(translationName, layout_translationName, filmDetailEntity.getTitle());     // 2. 译名
        setText(years, layout_years, filmDetailEntity.getYears());                // 3. 年代
        setText(country, layout_country, filmDetailEntity.getCountry());            // 4. 国家
        setText(category, layout_category, filmDetailEntity.getCategory());          // 5．类型
        setText(language, layout_language, filmDetailEntity.getLanguage());          // 6. 语言
        setText(showTime, layout_showTime, filmDetailEntity.getShowTime());          // 7. 片长
        setText(publishTime, layout_publishTime, filmDetailEntity.getPublishTime());    // 8. 发布时间
        setText(playtime, layout_playtime, filmDetailEntity.getPlaytime());          // 9. 上映时间
        setText(subtitle, layout_subtitle, filmDetailEntity.getSubtitle());          // 10. 字母
        setText(fileFormat, layout_fileFormat, filmDetailEntity.getFileFormat());      // 11. 文件格式
        setText(videoSize, layout_videoSize, filmDetailEntity.getVideoSize());        // 12.　视频尺寸
        setText(fileSize, layout_fileSize, filmDetailEntity.getFileSize());        // 12.　文件大小
        setText(imdb, layout_imdb, filmDetailEntity.getImdb());                  // 13. 评分
        setText(episodeNumber, layout_episodeNumber, filmDetailEntity.getEpisodeNumber());// 14 集数
        setText(source, layout_source, filmDetailEntity.getSource());               // 15. 来源
        if (filmDetailEntity.getDirectors() != null &&filmDetailEntity.getDirectors().size() > 0) {
            setText(director, layout_director, filmDetailEntity.getDirectors().get(0));// 16. 导演
        }else {
            layout_director.setVisibility(View.GONE);
        }
        if (filmDetailEntity.getScreenWriters() != null &&filmDetailEntity.getScreenWriters().size() > 0) {
            setText(screenWriters, layout_screenWriters, filmDetailEntity.getScreenWriters().get(0));// 17. 编辑
        }else {
            layout_screenWriters.setVisibility(View.GONE);
        }
        if (filmDetailEntity.getLeadingPlayers() != null &&filmDetailEntity.getLeadingPlayers().size() > 0) {
            setText(leadingPlayers, layout_leadingPlayers, filmDetailEntity.getLeadingPlayers().get(0));// 18. 主演
        }else {
            layout_leadingPlayers.setVisibility(View.GONE);
        }

        setText(description, layout_description, filmDetailEntity.getDescription());// 19. 描述

        Glide.with(this)
                .load(filmDetailEntity.getCoverUrl())
                .into(filmPoster);

        if (TextUtils.isEmpty(filmDetailEntity.getPreviewImage())) {
            layout_previewImage.setVisibility(View.GONE);
        } else {
            layout_previewImage.setVisibility(View.VISIBLE);
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
