package com.sh.ilovepooq.dagger.base;

import com.sh.ilovepooq.dagger.main.MainComponent;
import com.sh.ilovepooq.dagger.main.MainModule;
import com.sh.ilovepooq.dagger.remote.HTMLParserModule;
import com.sh.ilovepooq.dagger.remote.KakaoAPIModule;
import com.sh.ilovepooq.dagger.search.SearchComponent;
import com.sh.ilovepooq.dagger.search.SearchModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, HTMLParserModule.class, KakaoAPIModule.class})
public interface AppComponent {

    MainComponent plus(MainModule module);

    SearchComponent plus(SearchModule module);

}
