package com.sh.ilovepooq.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragment(@NonNull FragmentManager fragmentManager,
                                   @NonNull Fragment fragment, int frameId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, tag);
        transaction.commit();
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, int frameId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.commit();
    }

    public static void replaceFragmentAddToBackStack(@NonNull FragmentManager fragmentManager,
                                                     @NonNull Fragment fragment, int frameId,
                                                     String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void removeFragment(@NonNull FragmentManager fragmentManager,
                                      @NonNull String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragmentManager.findFragmentByTag(tag));
        transaction.commit();
    }

    public static void hideFragment(@NonNull FragmentManager fragmentManager,
                                    @NonNull String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragmentManager.findFragmentByTag(tag));
        transaction.commit();
    }

    public static void showFragment(@NonNull FragmentManager fragmentManager, @NonNull String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragmentManager.findFragmentByTag(tag));
        transaction.commit();
    }

    public static void showFragment(@NonNull FragmentManager fragmentManager,
                                    @NonNull Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    public static boolean hasFragment(@NonNull FragmentManager fragmentManager,
                                      @NonNull String tag) {
        return fragmentManager.findFragmentByTag(tag) != null;
    }

}
