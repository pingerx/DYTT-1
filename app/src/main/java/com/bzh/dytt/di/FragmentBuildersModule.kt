package com.bzh.dytt.di

import com.bzh.dytt.ui.detail.DetailFragment
import com.bzh.dytt.ui.home.HomeFragment
import com.bzh.dytt.ui.home.HomeListFragment
import com.bzh.dytt.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainHomePageFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeListPageFragment(): HomeListFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailPageFragment(): DetailFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSearchFragment(): SearchFragment
}
