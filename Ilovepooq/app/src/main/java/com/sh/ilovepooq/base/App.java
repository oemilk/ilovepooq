package com.sh.ilovepooq.base;

import android.app.Application;

import com.sh.ilovepooq.dagger.base.AppComponent;
import com.sh.ilovepooq.dagger.base.AppModule;
import com.sh.ilovepooq.dagger.base.DaggerAppComponent;
import com.sh.ilovepooq.dagger.main.MainComponent;
import com.sh.ilovepooq.dagger.main.MainModule;
import com.sh.ilovepooq.dagger.remote.HTMLParserModule;

public class App extends Application {

    private AppComponent appComponent;
    private MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .hTMLParserModule(new HTMLParserModule())
                .build();
    }

    public MainComponent createMainComponent() {
        mainComponent = appComponent.plus(new MainModule());
        return mainComponent;
    }

    public void releaseMainComponent() {
        mainComponent = null;
    }

}
