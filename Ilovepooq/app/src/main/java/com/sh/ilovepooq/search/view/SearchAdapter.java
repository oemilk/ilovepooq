package com.sh.ilovepooq.search.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh.ilovepooq.R;
import com.sh.ilovepooq.model.SearchImageModel;
import com.sh.ilovepooq.utils.ImageUtils;
import com.sh.ilovepooq.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<SearchImageModel.Document> list;

    private ItemClick itemClick;

    SearchAdapter(@NonNull List<SearchImageModel.Document> list, @NonNull ItemClick itemClick) {
        this.list = list;
        this.itemClick = itemClick;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SearchImageModel.Document model = list.get(holder.getAdapterPosition());
        final String imageURL = model.getThumbnailUrl();
        final String alt = TextUtils.parseDateFromString(model.getDatetime());
        final String title = model.getDisplaySitename();
        final String hyperlink = model.getDocUrl();

        ImageUtils.displayIamge(imageURL, holder.imageView);

        holder.textViewTItle.setText(title);
        holder.textViewAlt.setText(alt);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.startBrowser(hyperlink);
            }
        });
    }

    interface ItemClick {
        void startBrowser(String link);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        @BindView(R.id.textView_title)
        TextView textViewTItle;

        @BindView(R.id.textView_alt)
        TextView textViewAlt;

        @BindView(R.id.imageView)
        ImageView imageView;

        ViewHolder(View view) {
            super(view);

            this.view = view;
            ButterKnife.bind(this, view);
        }

    }

}
