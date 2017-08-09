package com.sh.ilovepooq.main.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import com.sh.ilovepooq.base.App;
import com.sh.ilovepooq.base.BaseFragment;
import com.sh.ilovepooq.main.MainListContract;
import com.sh.ilovepooq.model.ContentInfoModel;
import com.sh.ilovepooq.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainListFragment extends BaseFragment implements MainListContract.View {

    private static final String TAG = LogUtils.makeLogTag(MainListFragment.class);

    @Inject
    MainListContract.Presenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.text_empty)
    TextView textView;

    private MainListAdapter adapter;

    private List<ContentInfoModel> contentInfoModelList = new ArrayList<>();

    private Unbinder unbinder;

    private Callback callback;

    public static MainListFragment newInstance() {
        return new MainListFragment();
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

        ((App) getActivity().getApplication()).createMainComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_list, container, false);
        unbinder = ButterKnife.bind(this, root);

        adapter = new MainListAdapter(contentInfoModelList, new MainListAdapter.ItemClick() {
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
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

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

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((App) getActivity().getApplication()).releaseMainComponent();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_view_grid));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_layout:
                callback.showGridView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    interface Callback {
        void showGridView();
    }

}
