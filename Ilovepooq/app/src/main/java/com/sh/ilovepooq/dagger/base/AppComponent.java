package com.sh.ilovepooq.dagger.base;

import com.sh.ilovepooq.dagger.main.MainComponent;
import com.sh.ilovepooq.dagger.main.MainModule;
import com.sh.ilovepooq.dagger.remote.HTMLParserModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, HTMLParserModule.class})
public interface AppComponent {

    MainComponent plus(MainModule module);

}
