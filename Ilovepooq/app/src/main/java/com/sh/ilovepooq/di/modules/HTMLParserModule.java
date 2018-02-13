package com.sh.ilovepooq.di.modules;

import com.sh.ilovepooq.remote.HTMLParser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class HTMLParserModule {

    @Provides
    @Singleton
    HTMLParser provideHtmlParser() {
        return new HTMLParser();
    }

}
