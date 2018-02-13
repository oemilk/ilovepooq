package com.sh.ilovepooq.di.components;

import android.app.Application;

import com.sh.ilovepooq.base.App;
import com.sh.ilovepooq.di.modules.AppModule;
import com.sh.ilovepooq.di.modules.FragmentModule;
import com.sh.ilovepooq.di.modules.HTMLParserModule;
import com.sh.ilovepooq.di.modules.KakaoAPIModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class,
        FragmentModule.class, HTMLParserModule.class, KakaoAPIModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }

}
