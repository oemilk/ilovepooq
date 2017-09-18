package com.sh.ilovepooq.search.repository;

import com.sh.ilovepooq.model.SearchImageModel;
import com.sh.ilovepooq.remote.KakaoAPI;
import com.sh.ilovepooq.search.SearchContract;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class SearchRepository implements SearchContract.Repository {

    private static final String FIRST_PAGE_NUMBER = "1";
    private static final String LOAD_ITEM_COUNT = "10";

    private KakaoAPI kakaoAPI;

    private int pageNumber = 1;

    public SearchRepository(KakaoAPI kakaoAPI) {
        this.kakaoAPI = kakaoAPI;
    }

    @Override
    public Single<List<SearchImageModel.Document>> search(String query) {
        pageNumber = 1;
        return kakaoAPI.getSearchImages(query, FIRST_PAGE_NUMBER, LOAD_ITEM_COUNT)
                .map(searchImageModel -> searchImageModel.getDocuments());
    }

    @Override
    public Single<List<SearchImageModel.Document>> searchNextPage(String query) {
        String page = Integer.toString(pageNumber++);
        return kakaoAPI.getSearchImages(query, page, LOAD_ITEM_COUNT)
                .map(searchImageModel -> searchImageModel.getDocuments());
    }

    @Override
    public Flowable<List<SearchImageModel.Document>> autoSearch(String query) {
        return kakaoAPI.getAutoSearchImages(query, FIRST_PAGE_NUMBER, LOAD_ITEM_COUNT)
                .concatMap(searchImageModel -> Flowable.just(searchImageModel.getDocuments()));
    }

}
