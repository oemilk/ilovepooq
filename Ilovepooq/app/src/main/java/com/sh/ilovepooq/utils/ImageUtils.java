package com.sh.ilovepooq.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtils {

    public static void displayIamge(String imageUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .into(imageView);
    }

    public static void displayIamge(String imageUrl, ImageView imageView, int drawable) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .into(imageView)
                .onLoadStarted(imageView.getContext().getDrawable(drawable));
    }

}
