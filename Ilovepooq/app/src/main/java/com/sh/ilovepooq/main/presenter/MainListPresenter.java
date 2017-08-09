package com.sh.ilovepooq.main.presenter;

import com.sh.ilovepooq.base.BaseContract;
import com.sh.ilovepooq.main.MainListContract;
import com.sh.ilovepooq.model.ContentInfoModel;
import com.sh.ilovepooq.utils.LogUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainListPresenter implements MainListContract.Presenter {

    private static final String TAG = LogUtils.makeLogTag(MainListPresenter.class);

    private final CompositeDisposable disposable = new CompositeDisposable();

    private MainListContract.View view;
    private MainListContract.Repository repository;

    public MainListPresenter(MainListContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public void setView(BaseContract.View view) {
        this.view = (MainListContract.View) view;
    }

    @Override
    public void clear() {
        if (!disposable.isDisposed()) {
            disposable.clear();
        }
    }

    @Override
    public void startParsing() {
        disposable.add(
                repository.parsing()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<List<ContentInfoModel>>() {
                            @Override
                            public void onSuccess(@NonNull List<ContentInfoModel> list) {
                                LogUtils.d(TAG, "onSuccess");
                                if (list.isEmpty()) {
                                    view.showEmpty();
                                } else {
                                    view.showList(list);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                LogUtils.d(TAG, "onError" + e.getMessage());
                                view.showError(e.getMessage());
                            }
                        })
        );
    }

}
