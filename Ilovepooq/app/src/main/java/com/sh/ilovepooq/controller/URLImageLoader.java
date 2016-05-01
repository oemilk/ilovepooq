package com.sh.ilovepooq.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sh.ilovepooq.Constants;
import com.sh.ilovepooq.R;

public class URLImageLoader {

    private final String TAG = "URLImageLoader";
    private final int FADE_TIME = 300;

    private static URLImageLoader ourInstance = new URLImageLoader();
    private ImageLoader mImageLoader;

    public static URLImageLoader getInstance() {
        return ourInstance;
    }

    private URLImageLoader() {
    }

    public void init(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory() // Set a same memory size.
                .tasksProcessingOrder(QueueProcessingType.LIFO) // Last-In-First-Out
                .build();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(config);
    }

    public void displayImage(String url, ImageView imageView, final URLImageLoaderCallback callback) {
        mImageLoader.displayImage(url, imageView, getOption(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.d(TAG, "onLoadingStarted : " + imageUri);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Log.d(TAG, "onLoadingFailed : " + imageUri);
                callback.onLoadingImageFailed(failReason.getType().ordinal());
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.d(TAG, "onLoadingComplete : " + imageUri);
                callback.onLoadingImageSucceed();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                Log.d(TAG, "onLoadingCancelled : " + imageUri);
                callback.onLoadingImageFailed(Constants.FAIL_CANCEL);
            }
        });

    }

    private DisplayImageOptions getOption() {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.item_no_image)
                .showImageOnFail(R.drawable.item_error_image)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .displayer(new FadeInBitmapDisplayer(FADE_TIME))
                .build();
    }

    public void destroy() {
        if (mImageLoader != null) {
            mImageLoader.destroy();
        }
    }
}
