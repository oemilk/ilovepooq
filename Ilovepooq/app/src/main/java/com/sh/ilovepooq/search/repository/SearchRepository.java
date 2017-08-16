package com.sh.ilovepooq.search.repository;

import com.sh.ilovepooq.model.SearchImageModel;
import com.sh.ilovepooq.remote.KakaoAPI;
import com.sh.ilovepooq.search.SearchContract;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class SearchRepository implements SearchContract.Repository {

    private KakaoAPI kakaoAPI;

    public SearchRepository(KakaoAPI kakaoAPI) {
        this.kakaoAPI = kakaoAPI;
    }

    @Override
    public Single<List<SearchImageModel.Document>> search(String query) {
        return kakaoAPI.getSearchImages(query)
                .map(new Function<SearchImageModel, List<SearchImageModel.Document>>() {
                    @Override
                    public List<SearchImageModel.Document> apply(
                            @NonNull SearchImageModel searchImageModel) throws Exception {
                        return searchImageModel.getDocuments();
                    }
                });
    }

}
