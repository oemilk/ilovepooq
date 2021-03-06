package com.sh.ilovepooq.main.view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sh.ilovepooq.R;
import com.sh.ilovepooq.base.BaseFragment;
import com.sh.ilovepooq.base.Constants;
import com.sh.ilovepooq.main.MainGridContract;
import com.sh.ilovepooq.model.ContentInfoModel;
import com.sh.ilovepooq.search.view.SearchActivity;
import com.sh.ilovepooq.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainGridFragment extends BaseFragment implements MainGridContract.View {

    private static final String TAG = LogUtils.makeLogTag(MainGridFragment.class);

    private static final int PORTRAIT_SPAN_COUNT = 2;
    private static final int LANDSCAPE_SPAN_COUNT = 3;

    @Inject
    MainGridContract.Presenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.text_empty)
    TextView textView;

    private MainGridAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    private List<ContentInfoModel> contentInfoModelList = new ArrayList<>();

    private Callback callback;

    public static MainGridFragment newInstance() {
        return new MainGridFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_grid, container, false);
        setUnbinder(ButterKnife.bind(this, root));

        gridLayoutManager = new GridLayoutManager(root.getContext(), PORTRAIT_SPAN_COUNT);

        setGridLayoutManagerSpanCount(getResources().getConfiguration().orientation);

        adapter = new MainGridAdapter(contentInfoModelList, (imageView, searchQuery, imageURL) -> {
            if (searchQuery == null || searchQuery.isEmpty()) {
                showToast(R.string.toast_no_search_query);
            } else {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(Constants.EXTRA_SEARCH_QUERY, searchQuery);
                intent.putExtra(Constants.EXTRA_IMAGE_URL, imageURL);

                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(getActivity(), imageView,
                                ViewCompat.getTransitionName(imageView));

                startActivity(intent, options.toBundle());
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        presenter.setView(this);
        presenter.startParsing();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.clear();
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_layout:
                callback.showListView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setGridLayoutManagerSpanCount(newConfig.orientation);
    }

    @Override
    public void showList(@NonNull List list) {
        LogUtils.d(TAG, "showList");
        textView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        contentInfoModelList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmpty() {
        LogUtils.d(TAG, "showEmpty");
        recyclerView.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        LogUtils.d(TAG, "showError");
        showToast(message);
    }

    private void setGridLayoutManagerSpanCount(int orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager.setSpanCount(LANDSCAPE_SPAN_COUNT);
        } else {
            gridLayoutManager.setSpanCount(PORTRAIT_SPAN_COUNT);
        }
    }

    interface Callback {
        void showListView();
    }

}
