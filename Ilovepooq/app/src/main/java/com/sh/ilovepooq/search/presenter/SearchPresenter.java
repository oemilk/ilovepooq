package com.sh.ilovepooq.search.presenter;

import com.sh.ilovepooq.base.BaseContract;
import com.sh.ilovepooq.model.SearchImageModel;
import com.sh.ilovepooq.search.SearchContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.Presenter {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private SearchContract.View view;
    private SearchContract.Repository repository;

    public SearchPresenter(SearchContract.Repository repository) {
        this.repository = repository;
    }

    @Override
    public void setView(BaseContract.View view) {
        this.view = (SearchContract.View) view;
    }

    @Override
    public void clear() {
        if (!disposable.isDisposed()) {
            disposable.clear();
        }
    }

    @Override
    public void startSearching(@NonNull String query) {
        disposable.add(
                repository.search(query)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(
                                new DisposableSingleObserver<List<SearchImageModel.Document>>() {
                                    @Override
                                    public void onSuccess(
                                            @NonNull List<SearchImageModel.Document> list) {
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
