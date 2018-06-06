package com.bzh.dytt.di

import com.bzh.dytt.ui.*
import com.bzh.dytt.ui.home.HomeFragment
import com.bzh.dytt.ui.home.HomeListFragment
import com.bzh.dytt.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainHomePageFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHomeListPageFragment(): HomeListFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHomePageFragment(): AllMoviePageFragment

    @ContributesAndroidInjector
    internal abstract fun contributeVideoDetailFragment(): VideoDetailPageFragment

    @ContributesAndroidInjector
    internal abstract fun contributeNewMovieFragment(): NewMovieFragment

    @ContributesAndroidInjector
    internal abstract fun contributeLoadbleMoviePageFragment(): LoadableMoviePageFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    internal abstract fun contributeImdbMovieFragment(): ImdbMovieFragment
}
