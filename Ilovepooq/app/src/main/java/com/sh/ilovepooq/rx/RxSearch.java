package com.sh.ilovepooq.rx;

import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Predicate;
import io.reactivex.processors.BehaviorProcessor;

public class RxSearch {

    private static final int CHANGE_TIMEOUT = 300;
    private static final int SEARCH_MIN_LENGTH = 1;

    private BehaviorProcessor<String> changeProcessor;

    public RxSearch(@NonNull SearchView searchView) {

        changeProcessor = BehaviorProcessor.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    //
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {
                    changeProcessor.onNext(newText);
                }
                return true;
            }
        });
    }

    public Flowable<String> getChange() {
        return changeProcessor
                .throttleWithTimeout(CHANGE_TIMEOUT, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull String s)
                            throws Exception {
                        return s.length() > SEARCH_MIN_LENGTH;
                    }
                });
    }

}
