package com.sh.ilovepooq.controller;

public interface URLImageLoaderCallback {
    void onLoadingImageSucceed();

    void onLoadingImageFailed(int type);
}
