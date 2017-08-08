package com.sh.ilovepooq.MVP.presenter;

import android.content.Context;
import android.util.Log;

import com.sh.ilovepooq.MVP.AdapterMVP;
import com.sh.ilovepooq.MVP.MVP;
import com.sh.ilovepooq.MVP.model.ContentInfoModel;
import com.sh.ilovepooq.utils.NetworkUtils;

import java.util.ArrayList;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class MVPPresenter implements MVP.Presenter {

    private static final String TAG = "MVPPresenter";

    @Nullable
    private MVP.View view;
    private MVP.Model model;
    private AdapterMVP.Model adaptermodel;

    @Inject
    public MVPPresenter(MVP.Model model, MVP.View view, AdapterMVP.Model adapterModel) {
        this.model = model;
        this.view = view;
        this.adaptermodel = adapterModel;
    }

    @Override
    public void init(Context context) {
        if (view != null) {
            if (NetworkUtils.isNetworkConnected(context)) {
                Log.d(TAG, "Network is fine.");
                view.setUI();
            } else {
                Log.d(TAG, "Network is not fine.");
                view.showNetworkErrorDialog();
            }

            model.setPresenter(this);
        }
    }

    @Override
    public void parsingHTMLURL() {
        Log.d(TAG, "parsingHTMLURL");
        model.parsingHTMLURL();
    }

    @Override
    public void parsingFailed() {
        if (view != null) {
            view.showParsingErrorDialog();
            view.hideProgressbar();
        }
    }

    @Override
    public void parsingSucceed(ArrayList<ContentInfoModel> list) {
        if (list.isEmpty()) {
            if (view != null) {
                view.showEmptyTextView();
                view.hideProgressbar();
            }
        } else {
            if (adaptermodel != null) {
                adaptermodel.addAll(list);
            }
            if (view != null) {
                view.showToolbarMenu();
                view.hideProgressbar();
            }
        }
    }

}
