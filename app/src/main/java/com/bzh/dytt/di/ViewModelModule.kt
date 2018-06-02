package com.bzh.dytt.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.bzh.dytt.base.BaseViewModel
import com.bzh.dytt.ui.home.HomeViewModel
import com.bzh.dytt.ui.search.SearchViewModel
import com.bzh.dytt.util.ViewModelFactory
import com.bzh.dytt.viewmodel.ImdbViewModel
import com.bzh.dytt.viewmodel.LoadableMoviePageViewModel
import com.bzh.dytt.viewmodel.NewMovieViewModel
import com.bzh.dytt.viewmodel.VideoDetailPageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(VideoDetailPageViewModel::class)
    abstract fun bindVideoDetailPageViewModel(videoDetailPageViewModel: VideoDetailPageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewMovieViewModel::class)
    abstract fun bindNewMoviePageViewModel(newMovieViewModel: NewMovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BaseViewModel::class)
    abstract fun bindBaseViewModel(baseViewModel: BaseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoadableMoviePageViewModel::class)
    abstract fun bindLoadableMoviePageViewModel(loadableMoviePageViewModel: LoadableMoviePageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ImdbViewModel::class)
    abstract fun bindImdbViewModel(imdbViewModel: ImdbViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
