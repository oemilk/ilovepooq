package com.sh.ilovepooq.search.repository;

import com.sh.ilovepooq.model.SearchImageModel;
import com.sh.ilovepooq.remote.KakaoAPI;
import com.sh.ilovepooq.search.SearchContract;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

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
                .map(new Function<SearchImageModel, List<SearchImageModel.Document>>() {
                    @Override
                    public List<SearchImageModel.Document> apply(
                            @NonNull SearchImageModel searchImageModel) throws Exception {
                        return searchImageModel.getDocuments();
                    }
                });
    }

    @Override
    public Single<List<SearchImageModel.Document>> searchNextPage(String query) {
        String page = Integer.toString(pageNumber++);
        return kakaoAPI.getSearchImages(query, page, LOAD_ITEM_COUNT)
                .map(new Function<SearchImageModel, List<SearchImageModel.Document>>() {
                    @Override
                    public List<SearchImageModel.Document> apply(
                            @NonNull SearchImageModel searchImageModel) throws Exception {
                        return searchImageModel.getDocuments();
                    }
                });
    }

}
