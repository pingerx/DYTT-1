package com.bzh.dytt.di

import com.bzh.dytt.ui.home.HomeFragment
import com.bzh.dytt.ui.home.HomeListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainHomePageFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHomeListPageFragment(): HomeListFragment

//    @ContributesAndroidInjector
//    internal abstract fun contributeSearchFragment(): SearchFragment
}
