package com.sh.ilovepooq.search.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sh.ilovepooq.R;
import com.sh.ilovepooq.base.App;
import com.sh.ilovepooq.base.BaseFragment;
import com.sh.ilovepooq.base.Constants;
import com.sh.ilovepooq.model.SearchImageModel;
import com.sh.ilovepooq.search.SearchContract;
import com.sh.ilovepooq.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchFragment extends BaseFragment implements SearchContract.View {

    private static final String TAG = LogUtils.makeLogTag(SearchFragment.class);

    private static final int PORTRAIT_SPAN_COUNT = 2;
    private static final int LANDSCAPE_SPAN_COUNT = 3;

    @Inject
    SearchContract.Presenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.text_empty)
    TextView textView;

    private SearchAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    private List<SearchImageModel.Document> contentInfoModelList = new ArrayList<>();

    private Unbinder unbinder;

    private String searchQuery;

    public static SearchFragment newInstance(String searchQuery) {
        Bundle args = new Bundle();
        args.putString(Constants.ARGUMENT_SEARCH_QUERY, searchQuery);

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            searchQuery = args.getString(Constants.ARGUMENT_SEARCH_QUERY);
        }

        setRetainInstance(true);

        ((App) getActivity().getApplication()).createSearchComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, root);

        gridLayoutManager = new GridLayoutManager(root.getContext(), PORTRAIT_SPAN_COUNT);

        adapter = new SearchAdapter(contentInfoModelList, new SearchAdapter.ItemClick() {
            @Override
            public void startBrowser(String link) {
                if (link == null || link.isEmpty()) {
                    showToast(R.string.toast_no_link_url);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(intent);
                }
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
        presenter.startSearching(searchQuery);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.clear();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((App) getActivity().getApplication()).releaseSearchComponent();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager.setSpanCount(LANDSCAPE_SPAN_COUNT);
        } else {
            gridLayoutManager.setSpanCount(PORTRAIT_SPAN_COUNT);
        }
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
}