package com.sh.ilovepooq.main;

import android.support.annotation.NonNull;

import com.sh.ilovepooq.base.BaseContract;
import com.sh.ilovepooq.model.ContentInfoModel;

import java.util.List;

import io.reactivex.Single;

public interface MainGridContract {

    interface View<T> extends BaseContract.View {

        void showList(@NonNull List<ContentInfoModel> list);

        void showEmpty();

        void showError(String message);

    }

    interface Presenter extends BaseContract.Presenter {

        void startParsing();

    }

    interface Repository extends BaseContract.Repository {

        Single<List<ContentInfoModel>> parsing();

    }

}
