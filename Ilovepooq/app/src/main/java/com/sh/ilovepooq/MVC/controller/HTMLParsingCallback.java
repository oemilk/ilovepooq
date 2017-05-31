package com.sh.ilovepooq.MVC.controller;

import com.sh.ilovepooq.MVC.model.ContentInfoModel;

import java.util.ArrayList;

public interface HTMLParsingCallback {
    void onHTMLParsingSucceed(ArrayList<ContentInfoModel> list);

    void onHTMLParsingFailed();
}
