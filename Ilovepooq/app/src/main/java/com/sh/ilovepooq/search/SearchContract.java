package com.sh.ilovepooq.search;

import android.support.annotation.NonNull;

import com.sh.ilovepooq.base.BaseContract;
import com.sh.ilovepooq.model.SearchImageModel;

import java.util.List;

import io.reactivex.Single;

public interface SearchContract {

    interface View<T> extends BaseContract.View {

        void showList(@NonNull List<SearchImageModel.Document> list);

        void showNextPageList(@NonNull List<SearchImageModel.Document> list);

        void showEmpty();

        void showNextPageEmpty();

        void showError(String message);

    }

    interface Presenter extends BaseContract.Presenter {

        void startSearching(@NonNull String query);

        void startSearchingNextPage(@NonNull String query);

    }

    interface Repository extends BaseContract.Repository {

        Single<List<SearchImageModel.Document>> search(String query);

        Single<List<SearchImageModel.Document>> searchNextPage(String query);

    }

}
