package com.sh.ilovepooq.MVC.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
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

import com.sh.ilovepooq.Constants;
import com.sh.ilovepooq.R;
import com.sh.ilovepooq.MVC.controller.HTMLParsingAsyncTask;
import com.sh.ilovepooq.MVC.controller.HTMLParsingCallback;
import com.sh.ilovepooq.MVC.controller.HTMLParsingThread;
import com.sh.ilovepooq.MVC.model.ContentInfoModel;
import com.sh.ilovepooq.utils.NetworkUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class MVCMainActivity extends AppCompatActivity {

    private final String TAG = "MVCMainActivity";

    public final int LIST_LAYOUT_MANAGER_TYPE = 0;
    public final int GRID_LAYOUT_MANAGER_TYPE = 1;
    public final int LIST_SPAN_COUNT = 2;
    public final int GRID_SPAN_COUNT = 3;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private ProgressBar mProgressBar;
    private Menu mToolbarMenu;

    private int mCurrentLayoutManagerType;
    private int mSpanCount;

    private HTMLParsingAsyncTask mHTMLParsingAsyncTask = null;

    private HTMLParsingCallback mHtmlParsingCallback = new HTMLParsingCallback() {
        @Override
        public void onHTMLParsingSucceed(ArrayList<ContentInfoModel> list) {
            Log.d(TAG, "parsing success");
            setRecyclerViewUI(list);
        }

        @Override
        public void onHTMLParsingFailed() {
            Log.d(TAG, "parsing fail");
            showErrorDialog(getString(R.string.dialog_parsing_error_title),
                    getString(R.string.dialog_parsing_error_message));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc_main);
        Log.d(TAG, "onCreate");
        init();
    }

    private void init() {
        if (NetworkUtils.isNetworkConnected(this)) {
            Log.d(TAG, "Network is fine.");

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().getThemedContext();

            mProgressBar = (ProgressBar) findViewById(R.id.loading);
            mProgressBar.setVisibility(View.VISIBLE);

            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(this);
            mCurrentLayoutManagerType = LIST_LAYOUT_MANAGER_TYPE;
            setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
            parsingHTMLURL();
        } else {
            Log.d(TAG, "Network is not fine.");
            showErrorDialog(getString(R.string.dialog_disconnected_network_title), getString(R.string.dialog_disconnected_network_message));
        }
    }

    private Handler mHandler;

    private void parsingHTMLURL() {
        Log.d(TAG, "parsingHTMLURL");
        // Ues an asyncTask for getting datas
//        mHTMLParsingAsyncTask = new HTMLParsingAsyncTask(mHtmlParsingCallback);
//        mHTMLParsingAsyncTask.execute();

        // Ues a thread for getting datas
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == HTMLParsingThread.SUCCESS) {
                    ArrayList<ContentInfoModel> list = (ArrayList<ContentInfoModel>) msg.obj;
                    setRecyclerViewUI(list);
                } else {
                    showErrorDialog(getString(R.string.dialog_parsing_error_title),
                            getString(R.string.dialog_parsing_error_message));
                }
                super.handleMessage(msg);
            }
        };

        HTMLParsingThread thread = new HTMLParsingThread(mHandler);
        thread.start();

    }

    private void setRecyclerViewUI(ArrayList<ContentInfoModel> list) {
        mProgressBar.setVisibility(View.GONE);
        if (!list.isEmpty()) {
            Log.d(TAG, "List is not empty.");
//            mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, list, mURLImageLoader);
            mRecyclerViewAdapter = new RecyclerViewAdapter(MVCMainActivity.this, list);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
            mToolbarMenu.findItem(R.id.action_layout).setVisible(true);
        } else {
            Log.d(TAG, "List is empty.");
            TextView textView = (TextView) findViewById(R.id.textview);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void setRecyclerViewLayoutManager(int layoutManagerType) {
        Log.d(TAG, "setRecyclerViewLayoutManager");

        int scrollPosition = 0;

        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            Log.d(TAG, "scrollPosition : " + scrollPosition);
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER_TYPE:
                Log.d(TAG, "GRID_LAYOUT_MANAGER_TYPE");
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mSpanCount = GRID_SPAN_COUNT;
                } else {
                    mSpanCount = LIST_SPAN_COUNT;
                }
                mLayoutManager = new GridLayoutManager(this, mSpanCount);
                mCurrentLayoutManagerType = GRID_LAYOUT_MANAGER_TYPE;
                break;
            case LIST_LAYOUT_MANAGER_TYPE:
                Log.d(TAG, "LIST_LAYOUT_MANAGER_TYPE");
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LIST_LAYOUT_MANAGER_TYPE;
                break;
            default:
                Log.d(TAG, "default");
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LIST_LAYOUT_MANAGER_TYPE;
        }

        if (mRecyclerViewAdapter != null) {
            mRecyclerViewAdapter.setLayoutManagerType(mCurrentLayoutManagerType);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    private void showErrorDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MVCMainActivity.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_layout);
        menuItem.setVisible(false);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (mCurrentLayoutManagerType == LIST_LAYOUT_MANAGER_TYPE) {
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
        mToolbarMenu = menu;
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (mCurrentLayoutManagerType == GRID_LAYOUT_MANAGER_TYPE) {
            Log.d(TAG, "mCurrentLayoutManagerType is GRID_LAYOUT_MANAGER");
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.d(TAG, "onConfigurationChanged : ORIENTATION_LANDSCAPE");
                mSpanCount = GRID_SPAN_COUNT;
            } else {
                Log.d(TAG, "onConfigurationChanged : ORIENTATION_PORTRAIT");
                mSpanCount = LIST_SPAN_COUNT;
            }
            ((GridLayoutManager) mLayoutManager).setSpanCount(mSpanCount);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.d(TAG, "onSaveInstanceState");
        outState.putSerializable(Constants.BUNDLE_KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState");
        if (savedInstanceState != null) {
            Serializable serializable = savedInstanceState.getSerializable(Constants.BUNDLE_KEY_LAYOUT_MANAGER);
            if (serializable != null) {
                mCurrentLayoutManagerType = (int) savedInstanceState.getSerializable(Constants.BUNDLE_KEY_LAYOUT_MANAGER);
                setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (mHTMLParsingAsyncTask != null) {
            mHTMLParsingAsyncTask.cancel(true);
        }
    }
}
