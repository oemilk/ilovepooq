package com.sh.ilovepooq.search.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.sh.ilovepooq.R;
import com.sh.ilovepooq.base.BaseActivity;
import com.sh.ilovepooq.base.Constants;
import com.sh.ilovepooq.utils.ActivityUtils;
import com.sh.ilovepooq.utils.ImageUtils;
import com.sh.ilovepooq.utils.LogUtils;

public class SearchActivity extends BaseActivity {

    private static final String TAG = LogUtils.makeLogTag(SearchActivity.class);

    private static final String SEARCH_TAG = "SEARCH_TAG";

    private FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra(Constants.EXTRA_SEARCH_QUERY);
        String imageURL = intent.getStringExtra(Constants.EXTRA_IMAGE_URL);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(searchQuery);
        }

        fragmentManager = getSupportFragmentManager();
        if (!ActivityUtils.hasFragment(fragmentManager, SEARCH_TAG)) {
            ActivityUtils.addFragment(fragmentManager, SearchFragment.newInstance(searchQuery),
                    R.id.contentFrame, SEARCH_TAG);
        }

        setToolbarLayout(imageURL);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    private void setToolbarLayout(String imageURL) {
        ImageView imageView = findViewById(R.id.imageView);
        ImageUtils.displayIamgeWithPalette(imageURL, imageView);

        CollapsingToolbarLayout layout = findViewById(R.id.collapsing_toolbar_layout);
        layout.setExpandedTitleColor(Color.TRANSPARENT);
        ViewCompat.getTransitionName(imageView);
        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener((barLayout, verticalOffset) -> {
            if(layout.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(layout)) {
                imageView.setTransitionName("");
            } else {
                imageView.setTransitionName(getString(R.string.transition_grid));
            }
        });
    }

}
