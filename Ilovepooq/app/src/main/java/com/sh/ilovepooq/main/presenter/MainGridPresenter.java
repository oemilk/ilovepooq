package com.sh.ilovepooq.main.presenter;

import com.sh.ilovepooq.base.BaseContract;
import com.sh.ilovepooq.main.MainGridContract;
import com.sh.ilovepooq.model.ContentInfoModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainGridPresenter implements MainGridContract.Presenter {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private MainGridContract.View view;
    private MainGridContract.Repository repository;

    public MainGridPresenter(MainGridContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public void setView(BaseContract.View view) {
        this.view = (MainGridContract.View) view;
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
                                if (list.isEmpty()) {
                                    view.showEmpty();
                                } else {
                                    view.showList(list);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                view.showError(e.getMessage());
                            }
                        })
        );
    }

}
