package com.sh.ilovepooq.di.base;

import com.sh.ilovepooq.di.main.MainComponent;
import com.sh.ilovepooq.di.main.MainModule;
import com.sh.ilovepooq.di.remote.HTMLParserModule;
import com.sh.ilovepooq.di.remote.KakaoAPIModule;
import com.sh.ilovepooq.di.search.SearchComponent;
import com.sh.ilovepooq.di.search.SearchModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, HTMLParserModule.class, KakaoAPIModule.class})
public interface AppComponent {

    MainComponent plus(MainModule module);

    SearchComponent plus(SearchModule module);

}
