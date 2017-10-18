package com.sh.ilovepooq.search.presenter;

import com.sh.ilovepooq.base.BaseContract;
import com.sh.ilovepooq.model.SearchImageModel;
import com.sh.ilovepooq.rx.RxSearch;
import com.sh.ilovepooq.search.SearchContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

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

    @Override
    public void startSearchingNextPage(@NonNull String query) {
        disposable.add(
                repository.searchNextPage(query)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(
                                new DisposableSingleObserver<List<SearchImageModel.Document>>() {
                                    @Override
                                    public void onSuccess(
                                            @NonNull List<SearchImageModel.Document> list) {
                                        if (list.isEmpty()) {
                                            view.showNextPageEmpty();
                                        } else {
                                            view.showNextPageList(list);
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        view.showError(e.getMessage());
                                    }
                                })
        );

    }

    @Override
    public void initRxSearch(RxSearch rxSearch) {
        disposable.addAll(
                rxSearch.getChange()
                        .switchMap(s -> repository.autoSearch(s))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSubscriber<List<SearchImageModel.Document>>() {
                            @Override
                            public void onNext(List<SearchImageModel.Document> list) {
                                view.showAutoList(list);
                            }

                            @Override
                            public void onError(Throwable t) {
                                view.showError(t.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                //
                            }
                        }),

                rxSearch.getSubmit()
                        .switchMap(s -> repository.search(s)
                                .toFlowable()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSubscriber<List<SearchImageModel.Document>>() {
                            @Override
                            public void onNext(List<SearchImageModel.Document> list) {
                                if (list.isEmpty()) {
                                    view.showEmpty();
                                } else {
                                    view.showList(list);
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                view.showError(t.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                //
                            }
                        })

        );
    }

}
