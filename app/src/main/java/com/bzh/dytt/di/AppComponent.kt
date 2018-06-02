package com.bzh.dytt.di

import android.app.Application
import com.bzh.dytt.base.BasicApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


/**
 * The injector class used in Dagger 2 is called a component.
 * It assigns references in our activities, services, or fragments to have access to singletons we earlier defined.
 * We will need to annotate this class with a @Component annotation.
 *
 * http://a.codekk.com/detail/Android/%E6%89%94%E7%89%A9%E7%BA%BF/Dagger%20%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, MainActivityModule::class, AppModule::class])
interface AppComponent {

    fun inject(basicApp: BasicApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }
}
