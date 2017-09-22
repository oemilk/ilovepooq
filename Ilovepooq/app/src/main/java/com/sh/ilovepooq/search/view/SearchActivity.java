package com.sh.ilovepooq.search.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.sh.ilovepooq.R;
import com.sh.ilovepooq.base.BaseActivity;
import com.sh.ilovepooq.base.Constants;
import com.sh.ilovepooq.utils.ActivityUtils;
import com.sh.ilovepooq.utils.LogUtils;

public class SearchActivity extends BaseActivity {

    private static final String TAG = LogUtils.makeLogTag(SearchActivity.class);

    private static final String SEARCH_TAG = "SEARCH_TAG";

    private FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String searchQuery = getIntent().getStringExtra(Constants.EXTRA_SEARCH_QUERY);

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
    }

}
