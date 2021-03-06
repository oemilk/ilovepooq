package com.sh.ilovepooq.main.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.sh.ilovepooq.R;
import com.sh.ilovepooq.base.BaseActivity;
import com.sh.ilovepooq.utils.ActivityUtils;
import com.sh.ilovepooq.utils.LogUtils;

public class MainActivity extends BaseActivity implements MainGridFragment.Callback,
        MainListFragment.Callback {

    private static final String TAG = LogUtils.makeLogTag(MainActivity.class);

    private static final String MAIN_GRID_TAG = "MAIN_GRID_TAG";
    private static final String MAIN_LIST_TAG = "MAIN_LIST_TAG";

    private FragmentManager fragmentManager;

    @Override
    protected int getContentViewResouce() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        fragmentManager = getFragmentManager();
        if (!ActivityUtils.hasFragment(fragmentManager, MAIN_GRID_TAG)
                && !ActivityUtils.hasFragment(fragmentManager, MAIN_LIST_TAG)) {
            ActivityUtils.addFragment(fragmentManager, MainGridFragment.newInstance(),
                    R.id.contentFrame, MAIN_GRID_TAG);
        }

        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    public void showListView() {
        LogUtils.d(TAG, "showListView");
        ActivityUtils.replaceFragment(fragmentManager, MainListFragment.newInstance(),
                R.id.contentFrame, MAIN_LIST_TAG);
    }

    @Override
    public void showGridView() {
        LogUtils.d(TAG, "showGridView");
        ActivityUtils.replaceFragment(fragmentManager, MainGridFragment.newInstance(),
                R.id.contentFrame, MAIN_LIST_TAG);
    }

}
