package com.sh.ilovepooq;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sh.ilovepooq.dagger.DaggerComponent;
import com.sh.ilovepooq.dagger.Module;
import com.sh.ilovepooq.view.RecyclerViewAdapter;

import java.io.Serializable;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MVP.View {

    private static final String TAG = "MainActivity";

    @Inject
    MVP.Presenter presenter;

    @Inject
    AdapterMVP.View adapterView;

    public static final int LIST_LAYOUT_MANAGER_TYPE = 0;
    public static final int GRID_LAYOUT_MANAGER_TYPE = 1;
    public static final int LIST_SPAN_COUNT = 2;
    public static final int GRID_SPAN_COUNT = 3;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Menu toolbarMenu;

    private int currentLayoutManagerType;
    private int spanCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_main);
        Log.d(TAG, "onCreate");

        recyclerViewAdapter = new RecyclerViewAdapter(this);

        DaggerComponent.builder().module(new Module(recyclerViewAdapter, this)).build()
                .inject(this);
        presenter.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_layout);
        menuItem.setVisible(false);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (currentLayoutManagerType == LIST_LAYOUT_MANAGER_TYPE) {
                    Log.d(TAG, "onMenuItemClick list");
                    menuItem.setIcon(getDrawable(R.drawable.ic_view_list));
                    setRecyclerViewLayoutManager(GRID_LAYOUT_MANAGER_TYPE);
                } else {
                    Log.d(TAG, "onMenuItemClick grid");
                    menuItem.setIcon(getDrawable(R.drawable.ic_view_grid));
                    setRecyclerViewLayoutManager(LIST_LAYOUT_MANAGER_TYPE);
                }
                return false;
            }
        });
        toolbarMenu = menu;
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (currentLayoutManagerType == GRID_LAYOUT_MANAGER_TYPE) {
            Log.d(TAG, "mCurrentLayoutManagerType is GRID_LAYOUT_MANAGER");
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.d(TAG, "onConfigurationChanged : ORIENTATION_LANDSCAPE");
                spanCount = GRID_SPAN_COUNT;
            } else {
                Log.d(TAG, "onConfigurationChanged : ORIENTATION_PORTRAIT");
                spanCount = LIST_SPAN_COUNT;
            }
            ((GridLayoutManager) linearLayoutManager).setSpanCount(spanCount);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.d(TAG, "onSaveInstanceState");
        outState.putSerializable(Constants.BUNDLE_KEY_LAYOUT_MANAGER, currentLayoutManagerType);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState");
        if (savedInstanceState != null) {
            Serializable serializable = savedInstanceState
                    .getSerializable(Constants.BUNDLE_KEY_LAYOUT_MANAGER);
            if (serializable != null) {
                currentLayoutManagerType = (int) savedInstanceState
                        .getSerializable(Constants.BUNDLE_KEY_LAYOUT_MANAGER);
                setRecyclerViewLayoutManager(currentLayoutManagerType);
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void setUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().getThemedContext();

        progressBar = (ProgressBar) findViewById(R.id.loading);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        currentLayoutManagerType = LIST_LAYOUT_MANAGER_TYPE;
        setRecyclerViewLayoutManager(currentLayoutManagerType);
        presenter.parsingHTMLURL();
    }

    @Override
    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkErrorDialog() {
        showErrorDialog(getString(R.string.dialog_disconnected_network_title),
                getString(R.string.dialog_disconnected_network_message));
    }

    @Override
    public void showParsingErrorDialog() {
        showErrorDialog(getString(R.string.dialog_parsing_error_title),
                getString(R.string.dialog_parsing_error_message));
    }

    @Override
    public void showEmptyTextView() {
        Log.d(TAG, "List is empty.");
        TextView textView = (TextView) findViewById(R.id.textview);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToolbarMenu() {
        toolbarMenu.findItem(R.id.action_layout).setVisible(true);
    }

    private void setRecyclerViewLayoutManager(int layoutManagerType) {
        Log.d(TAG, "setRecyclerViewLayoutManager");

        int scrollPosition = 0;

        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstVisibleItemPosition();
            Log.d(TAG, "scrollPosition : " + scrollPosition);
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER_TYPE:
                Log.d(TAG, "GRID_LAYOUT_MANAGER_TYPE");
                if (getResources().getConfiguration().orientation
                        == Configuration.ORIENTATION_LANDSCAPE) {
                    spanCount = GRID_SPAN_COUNT;
                } else {
                    spanCount = LIST_SPAN_COUNT;
                }
                linearLayoutManager = new GridLayoutManager(this, spanCount);
                currentLayoutManagerType = GRID_LAYOUT_MANAGER_TYPE;
                break;
            case LIST_LAYOUT_MANAGER_TYPE:
                Log.d(TAG, "LIST_LAYOUT_MANAGER_TYPE");
                linearLayoutManager = new LinearLayoutManager(this);
                currentLayoutManagerType = LIST_LAYOUT_MANAGER_TYPE;
                break;
            default:
                Log.d(TAG, "default");
                linearLayoutManager = new LinearLayoutManager(this);
                currentLayoutManagerType = LIST_LAYOUT_MANAGER_TYPE;
        }

        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.setLayoutManagerType(currentLayoutManagerType);
            recyclerView.setAdapter(recyclerViewAdapter);
        }

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.scrollToPosition(scrollPosition);
    }

    private void showErrorDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    finish();
                }
                return true;
            }
        });

        builder.create().show();
    }

}
