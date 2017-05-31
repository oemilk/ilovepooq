package com.sh.ilovepooq.MVC.controller;

public interface URLImageLoaderCallback {
    void onLoadingImageSucceed();

    void onLoadingImageFailed(int type);
}
