package com.sh.ilovepooq.base;

import android.app.Application;

import com.sh.ilovepooq.dagger.base.AppComponent;
import com.sh.ilovepooq.dagger.base.AppModule;
import com.sh.ilovepooq.dagger.base.DaggerAppComponent;
import com.sh.ilovepooq.dagger.main.MainComponent;
import com.sh.ilovepooq.dagger.main.MainModule;
import com.sh.ilovepooq.dagger.remote.HTMLParserModule;
import com.sh.ilovepooq.dagger.remote.KakaoAPIModule;
import com.sh.ilovepooq.dagger.search.SearchComponent;
import com.sh.ilovepooq.dagger.search.SearchModule;

public class App extends Application {

    private AppComponent appComponent;
    private MainComponent mainComponent;
    private SearchComponent searchComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .hTMLParserModule(new HTMLParserModule())
                .kakaoAPIModule(new KakaoAPIModule())
                .build();
    }

    public MainComponent createMainComponent() {
        mainComponent = appComponent.plus(new MainModule());
        return mainComponent;
    }

    public SearchComponent createSearchComponent() {
        searchComponent = appComponent.plus(new SearchModule());
        return searchComponent;
    }

    public void releaseMainComponent() {
        mainComponent = null;
    }

    public void releaseSearchComponent() {
        searchComponent = null;
    }

}
