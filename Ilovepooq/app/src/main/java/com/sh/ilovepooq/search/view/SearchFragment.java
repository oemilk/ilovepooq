package com.sh.ilovepooq.search.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lapism.searchview.SearchView;
import com.sh.ilovepooq.R;
import com.sh.ilovepooq.base.BaseFragment;
import com.sh.ilovepooq.base.Constants;
import com.sh.ilovepooq.model.SearchImageModel;
import com.sh.ilovepooq.rx.RxSearch;
import com.sh.ilovepooq.search.SearchContract;
import com.sh.ilovepooq.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subscribers.DisposableSubscriber;

public class SearchFragment extends BaseFragment implements SearchContract.View {

    private static final String TAG = LogUtils.makeLogTag(SearchFragment.class);

    private static final int PORTRAIT_SPAN_COUNT = 2;
    private static final int LANDSCAPE_SPAN_COUNT = 3;
    private static final int LOAD_ITEM_COUNT = 10;

    @Inject
    SearchContract.Presenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.text_empty)
    TextView textView;

    private SearchView searchView;

    private SearchAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    private List<SearchImageModel.Document> contentInfoModelList = new ArrayList<>();

    private Unbinder unbinder;

    private String searchQuery;

    private boolean isLoadingState;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, root);

        gridLayoutManager = new GridLayoutManager(root.getContext(), PORTRAIT_SPAN_COUNT);

        setGridLayoutManagerSpanCount(getResources().getConfiguration().orientation);

        adapter = new SearchAdapter(contentInfoModelList, link -> {
            if (link == null || link.isEmpty()) {
                showToast(R.string.toast_no_link_url);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
                int itemCount = manager.getItemCount();
                int itemPosition = manager.findLastVisibleItemPosition();

                if (!isLoadingState && itemCount <= itemPosition + LOAD_ITEM_COUNT) {
                    searchNextPage();
                }
            }
        });

        searchView = getActivity().findViewById(R.id.searchView);
        searchView.setHint(android.R.string.search_go);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        progressBar.setVisibility(View.VISIBLE);

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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                menuItem.collapseActionView();
                if (searchView.getVisibility() == View.VISIBLE) {
                    searchView.setVisibility(View.GONE);
                    searchView.close(true);
                } else {
                    searchView.setVisibility(View.VISIBLE);
                    searchView.open(true);
                }
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return false;
            }
        });

        RxSearch rxSearch = new RxSearch(searchView);
        rxSearch.getSubmit().subscribeWith(new DisposableSubscriber<String>() {
            @Override
            public void onNext(String s) {
                searchQuery = s;
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
        presenter.initRxSearch(rxSearch);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setGridLayoutManagerSpanCount(newConfig.orientation);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SearchView.SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && results.size() > 0) {
                String searchWrd = results.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, true);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showList(@NonNull List list) {
        LogUtils.d(TAG, "showList");
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        contentInfoModelList.clear();
        contentInfoModelList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNextPageList(@NonNull List list) {
        LogUtils.d(TAG, "showNextPageList");
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        contentInfoModelList.addAll(list);
        adapter.notifyDataSetChanged();
        isLoadingState = false;
    }

    @Override
    public void showAutoList(@NonNull List list) {
        LogUtils.d(TAG, "showAutoList");
    }

    @Override
    public void showEmpty() {
        LogUtils.d(TAG, "showEmpty");
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        contentInfoModelList.clear();
    }

    @Override
    public void showNextPageEmpty() {
        LogUtils.d(TAG, "showNextPageEmpty");
        progressBar.setVisibility(View.GONE);
        showToast(R.string.toast_no_more_search_query);
    }

    @Override
    public void showError(String message) {
        LogUtils.d(TAG, "showError");
        progressBar.setVisibility(View.GONE);
        showToast(message);
    }

    private void setGridLayoutManagerSpanCount(int orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager.setSpanCount(LANDSCAPE_SPAN_COUNT);
        } else {
            gridLayoutManager.setSpanCount(PORTRAIT_SPAN_COUNT);
        }
    }

    private void searchNextPage() {
        isLoadingState = true;
        progressBar.setVisibility(View.VISIBLE);
        presenter.startSearchingNextPage(searchQuery);
    }

}
