package com.sh.ilovepooq.di.search;

import com.sh.ilovepooq.di.base.FragmentScope;
import com.sh.ilovepooq.remote.KakaoAPI;
import com.sh.ilovepooq.search.SearchContract;
import com.sh.ilovepooq.search.presenter.SearchPresenter;
import com.sh.ilovepooq.search.repository.SearchRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    @FragmentScope
    public SearchContract.Presenter providePresenter(SearchContract.Repository repository) {
        return new SearchPresenter(repository);
    }

    @Provides
    @FragmentScope
    public SearchContract.Repository provideRepository(KakaoAPI kakaoAPI) {
        return new SearchRepository(kakaoAPI);
    }

}
