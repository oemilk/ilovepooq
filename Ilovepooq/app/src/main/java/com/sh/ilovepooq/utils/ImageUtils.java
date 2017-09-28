package com.sh.ilovepooq.utils;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

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

    public static void displayIamgeWithPalette(String imageUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(imageUrl)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap>
                            transition) {
                        super.onResourceReady(bitmap, transition);

                        Palette palette = Palette.from(bitmap).generate();
                        Palette.Swatch vibrant = palette.getVibrantSwatch();
                        if (vibrant != null) {
                            imageView.setBackgroundColor(vibrant.getRgb());
                        }
                    }
                });
    }

}
