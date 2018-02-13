package com.sh.ilovepooq.di.main;

import com.sh.ilovepooq.di.base.FragmentScope;
import com.sh.ilovepooq.main.MainGridContract;
import com.sh.ilovepooq.main.presenter.MainGridPresenter;
import com.sh.ilovepooq.main.MainListContract;
import com.sh.ilovepooq.main.presenter.MainListPresenter;
import com.sh.ilovepooq.main.repository.MainRepository;
import com.sh.ilovepooq.remote.HTMLParser;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    @FragmentScope
    public MainGridContract.Presenter provideGridPresenter(MainGridContract.Repository repository) {
        return new MainGridPresenter(repository);
    }

    @Provides
    @FragmentScope
    public MainGridContract.Repository provideGridRepository(HTMLParser parser) {
        return new MainRepository(parser);
    }

    @Provides
    @FragmentScope
    public MainListContract.Presenter provideListPresenter(MainListContract.Repository repository) {
        return new MainListPresenter(repository);
    }

    @Provides
    @FragmentScope
    public MainListContract.Repository provideListRepository(HTMLParser parser) {
        return new MainRepository(parser);
    }

}
