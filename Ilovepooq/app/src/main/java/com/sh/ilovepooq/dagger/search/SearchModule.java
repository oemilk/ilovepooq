package com.sh.ilovepooq.dagger.search;

import com.sh.ilovepooq.remote.KakaoAPI;
import com.sh.ilovepooq.search.SearchContract;
import com.sh.ilovepooq.search.presenter.SearchPresenter;
import com.sh.ilovepooq.search.repository.SearchRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    @SearchScope
    public SearchContract.Presenter providePresenter(SearchContract.Repository repository) {
        return new SearchPresenter(repository);
    }

    @Provides
    @SearchScope
    public SearchContract.Repository provideRepository(KakaoAPI kakaoAPI) {
        return new SearchRepository(kakaoAPI);
    }

}
