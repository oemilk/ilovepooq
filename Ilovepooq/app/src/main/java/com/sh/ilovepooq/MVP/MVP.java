package com.sh.ilovepooq.MVP;

import android.content.Context;

import com.sh.ilovepooq.MVP.model.ContentInfoModel;

import java.util.ArrayList;

public interface MVP {

    interface Model {
        void setPresenter(Presenter presenter);

        void parsingHTMLURL();
    }

    interface View {
        void setUI();

        void hideProgressbar();

        void showNetworkErrorDialog();

        void showParsingErrorDialog();

        void showEmptyTextView();

        void showToolbarMenu();
    }

    interface Presenter {
//        void setView(View MVPView);

        void init(Context context);

        void parsingHTMLURL();

        void parsingFailed();

        void parsingSucceed(ArrayList<ContentInfoModel> list);
    }

}
