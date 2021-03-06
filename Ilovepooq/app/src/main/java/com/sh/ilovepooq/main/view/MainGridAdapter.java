package com.sh.ilovepooq.main.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh.ilovepooq.R;
import com.sh.ilovepooq.model.ContentInfoModel;
import com.sh.ilovepooq.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class MainGridAdapter extends RecyclerView.Adapter<MainGridAdapter.ViewHolder> {

    private List<ContentInfoModel> list;

    private ItemClick itemClick;

    private int lastPosition = -1;

    MainGridAdapter(@NonNull List<ContentInfoModel> list, @NonNull ItemClick itemClick) {
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
                .inflate(R.layout.item_main_grid_row, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.view.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                ContentInfoModel model = list.get(adapterPosition);
                String title = model.getTitle();
                String imageURL = model.getImageURL();
                itemClick.startSearch(holder.imageView, title, imageURL);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition != RecyclerView.NO_POSITION) {
            final ContentInfoModel model = list.get(adapterPosition);
            final String imageURL = model.getImageURL();
            final String alt = model.getAlt();
            final String title = model.getTitle();

            ImageUtils.displayIamge(imageURL, holder.imageView);

            holder.textViewTItle.setText(title);
            holder.textViewAlt.setText(alt);

            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(
                        holder.imageView.getContext(), R.anim.up_from_bottom_grid);
                holder.view.startAnimation(animation);
                lastPosition = position;
            }
        }
    }

    interface ItemClick {
        void startSearch(ImageView imageView, String searchQuery, String imageURL);
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
