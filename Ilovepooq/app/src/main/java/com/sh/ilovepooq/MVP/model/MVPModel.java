package com.sh.ilovepooq.MVP.model;

import android.util.Log;

import com.sh.ilovepooq.MVP.helper.HTMLParsingAsyncTask;
import com.sh.ilovepooq.MVP.helper.HTMLParsingCallback;
import com.sh.ilovepooq.MVP.MVP;

import java.util.ArrayList;

public class MVPModel implements MVP.Model {

    private static final String TAG = "MVPModel";

    private MVP.Presenter presenter;

    @Override
    public void setPresenter(MVP.Presenter presenter) {
        this.presenter = presenter;
    }

    private HTMLParsingAsyncTask htmlParsingAsyncTask;
    private HTMLParsingCallback htmlParsingCallback = new HTMLParsingCallback() {
        @Override
        public void onHTMLParsingSucceed(ArrayList<ContentInfoModel> list) {
            Log.d(TAG, "parsing success");
            presenter.parsingSucceed(list);
        }

        @Override
        public void onHTMLParsingFailed() {
            Log.d(TAG, "parsing fail");
            presenter.parsingFailed();
        }
    };

    @Override
    public void parsingHTMLURL() {
        htmlParsingAsyncTask = new HTMLParsingAsyncTask(htmlParsingCallback);
        htmlParsingAsyncTask.execute();
    }

}
