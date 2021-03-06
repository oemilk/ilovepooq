package com.sh.ilovepooq.rx;

import android.support.annotation.NonNull;

import com.lapism.searchview.SearchView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;

public class RxSearch {

    private static final int CHANGE_TIMEOUT = 300;
    private static final int SEARCH_MIN_LENGTH = 1;

    private BehaviorProcessor<String> changeProcessor;
    private BehaviorProcessor<String> submitProcessor;

    public RxSearch(@NonNull SearchView searchView) {

        changeProcessor = BehaviorProcessor.create();
        submitProcessor = BehaviorProcessor.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    submitProcessor.onNext(query);
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
                .filter(s -> s.length() > SEARCH_MIN_LENGTH);
    }

    public Flowable<String> getSubmit() {
        return submitProcessor;
    }

}
