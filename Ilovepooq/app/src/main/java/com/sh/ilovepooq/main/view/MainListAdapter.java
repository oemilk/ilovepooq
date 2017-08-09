package com.sh.ilovepooq.main.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh.ilovepooq.R;
import com.sh.ilovepooq.model.ContentInfoModel;
import com.sh.ilovepooq.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private List<ContentInfoModel> list;

    private ItemClick itemClick;

    MainListAdapter(@NonNull List<ContentInfoModel> list, @NonNull ItemClick itemClick) {
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
                .inflate(R.layout.item_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ContentInfoModel model = list.get(holder.getAdapterPosition());
        final String imageURL = model.getImageURL();
        final String alt = model.getAlt();
        final String title = model.getTitle();
        final String hyperlink = model.getHyperlink();

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
