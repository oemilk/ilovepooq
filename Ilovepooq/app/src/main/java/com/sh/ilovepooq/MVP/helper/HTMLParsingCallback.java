package com.sh.ilovepooq.MVP.helper;

import com.sh.ilovepooq.MVP.model.ContentInfoModel;

import java.util.ArrayList;

public interface HTMLParsingCallback {
    void onHTMLParsingSucceed(ArrayList<ContentInfoModel> list);

    void onHTMLParsingFailed();
}
