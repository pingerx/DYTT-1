package com.bzh.dytt.di;

import android.app.Application;

import com.bzh.dytt.BasicApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * The injector class used in Dagger 2 is called a component.
 * It assigns references in our activities, services, or fragments to have access to singletons we earlier defined.
 * We will need to annotate this class with a @Component annotation.
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        MainActivityModule.class,
        AppModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        Builder appModule(AppModule appModule);

        AppComponent build();
    }

    void inject(BasicApp basicApp);
}
