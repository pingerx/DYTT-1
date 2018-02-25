package com.bzh.dytt.di;

import com.bzh.dytt.BasicApp;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,MainActivityModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        AppComponent build();
    }

    void inject(BasicApp basicApp);
}
