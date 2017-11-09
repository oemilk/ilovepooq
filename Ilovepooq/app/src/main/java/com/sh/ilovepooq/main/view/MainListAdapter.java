package com.sh.ilovepooq.main.view;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh.ilovepooq.R;
import com.sh.ilovepooq.databinding.ItemListRowBinding;
import com.sh.ilovepooq.model.ContentInfoModel;
import com.sh.ilovepooq.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private List<ContentInfoModel> list;

    private ItemClick itemClick;

    private int lastPosition = -1;

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
        if (position > lastPosition) {
            Animation animation;
            if (position % 2 == 0) {
                animation = AnimationUtils.loadAnimation(
                        holder.imageView.getContext(), R.anim.up_from_bottom_list);
            } else {
                animation = AnimationUtils.loadAnimation(
                        holder.imageView.getContext(), R.anim.up_from_bottom_list_reverse);
            }
            holder.cardView.startAnimation(animation);
            lastPosition = position;
        }

        ContentInfoModel model = list.get(holder.getAdapterPosition());
        holder.binding.setContent(model);
        holder.binding.setAdapter(this);
    }

    public void startBrowser(String hyperlink) {
        itemClick.startBrowser(hyperlink);
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        ImageUtils.displayIamge(url, view);
    }

    interface ItemClick {
        void startBrowser(String link);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemListRowBinding binding;
        private final View view;

        @BindView(R.id.cardView)
        CardView cardView;

        @BindView(R.id.textView_title)
        TextView textViewTItle;

        @BindView(R.id.textView_alt)
        TextView textViewAlt;

        @BindView(R.id.imageView)
        ImageView imageView;

        ViewHolder(View view) {
            super(view);

            binding = DataBindingUtil.bind(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

    }

}
