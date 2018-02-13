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
                .inflate(R.layout.item_search_grid_row, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.view.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                String hyperlink = list.get(adapterPosition).getDocUrl();
                itemClick.startBrowser(hyperlink);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition != RecyclerView.NO_POSITION) {
            final SearchImageModel.Document model = list.get(adapterPosition);
            final String imageURL = model.getThumbnailUrl();
            final String alt = TextUtils.parseDateFromString(model.getDatetime());
            final String title = model.getDisplaySitename();

            ImageUtils.displayIamge(imageURL, holder.imageView);

            holder.textViewTItle.setText(title);
            holder.textViewAlt.setText(alt);
        }
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
