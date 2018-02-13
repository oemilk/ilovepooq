package com.sh.ilovepooq.base;

import android.app.Application;

import com.sh.ilovepooq.di.base.AppComponent;
import com.sh.ilovepooq.di.base.AppModule;
import com.sh.ilovepooq.di.base.DaggerAppComponent;
import com.sh.ilovepooq.di.remote.HTMLParserModule;
import com.sh.ilovepooq.di.remote.KakaoAPIModule;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .hTMLParserModule(new HTMLParserModule())
                .kakaoAPIModule(new KakaoAPIModule())
                .build();
    }

    protected AppComponent getAppComponent() {
        return appComponent;
    }

}
