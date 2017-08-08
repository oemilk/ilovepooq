package com.sh.ilovepooq.helper;

import com.sh.ilovepooq.model.ContentInfoModel;

import java.util.ArrayList;

public interface HTMLParsingCallback {
    void onHTMLParsingSucceed(ArrayList<ContentInfoModel> list);

    void onHTMLParsingFailed();
}
