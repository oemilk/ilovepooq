package com.sh.ilovepooq.remote;

import com.sh.ilovepooq.base.Constants;
import com.sh.ilovepooq.model.SearchImageModel;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KakaoAPI {

    @GET(Constants.API_SEARCH_IMAGE_URL)
    Single<SearchImageModel> getSearchImages(
            @Query(Constants.API_SEARCH_IMAGE_QUERY) String query,
            @Query(Constants.API_SEARCH_IMAGE_PAGE) String page,
            @Query(Constants.API_SEARCH_IMAGE_SIZE) String size
    );

    @GET(Constants.API_SEARCH_IMAGE_URL)
    Flowable<SearchImageModel> getAutoSearchImages(
            @Query(Constants.API_SEARCH_IMAGE_QUERY) String query,
            @Query(Constants.API_SEARCH_IMAGE_PAGE) String page,
            @Query(Constants.API_SEARCH_IMAGE_SIZE) String size
    );

}
