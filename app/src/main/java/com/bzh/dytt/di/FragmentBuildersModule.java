package com.bzh.dytt.di;

import com.bzh.dytt.ui.ColorHuntFragment;
import com.bzh.dytt.ui.GirlFragment;
import com.bzh.dytt.ui.AllMoviePageFragment;
import com.bzh.dytt.ui.ImdbMovieFragment;
import com.bzh.dytt.ui.LoadableMoviePageFragment;
import com.bzh.dytt.ui.NewMovieFragment;
import com.bzh.dytt.ui.VideoDetailPageFragment;
import com.bzh.dytt.search.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract AllMoviePageFragment contributeHomePageFragment();

    @ContributesAndroidInjector
    abstract ColorHuntFragment contributeColorHuntFragment();

    @ContributesAndroidInjector
    abstract GirlFragment contributeGirlFragment();

    @ContributesAndroidInjector
    abstract VideoDetailPageFragment contributeVideoDetailFragment();

    @ContributesAndroidInjector
    abstract NewMovieFragment contributeNewMovieFragment();

    @ContributesAndroidInjector
    abstract LoadableMoviePageFragment contributeLoadbleMoviePageFragment();

    @ContributesAndroidInjector
    abstract SearchFragment contributeSearchFragment();

    @ContributesAndroidInjector
    abstract ImdbMovieFragment contributeImdbMovieFragment();
}
