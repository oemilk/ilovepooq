package com.sh.ilovepooq.MVC.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sh.ilovepooq.Constants;
import com.sh.ilovepooq.R;
import com.sh.ilovepooq.MVC.model.ContentInfoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    public static final int LIST_LAYOUT_MANAGER_TYPE = 0;
    public static final int GRID_LAYOUT_MANAGER_TYPE = 1;

    private Context context;
    private ArrayList<ContentInfoModel> list;
    private int layoutManagerType;

    public RecyclerViewAdapter(Context context, ArrayList<ContentInfoModel> list) {
        this.context = context;
        this.list = list;
        layoutManagerType = LIST_LAYOUT_MANAGER_TYPE;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutManagerType
                == LIST_LAYOUT_MANAGER_TYPE ? R.layout.item_list_row
                : R.layout.item_grid_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ContentInfoModel model = list.get(position);
        final String imageURL = model.getImageURL();
        final String alt = model.getAlt();
        final String title = model.getTitle();
        final String hyperlink = model.getHyperlink();

        final ImageView imageView = holder.getImageView();
        displayIamge(model, imageView);

        holder.getTextViewTItle().setText(title);
        holder.getTextViewAlt().setText(alt);
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBrowserIntent(hyperlink);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getLoadingResult() == Constants.SUCCESS) {
                    Log.d(TAG, "success url : " + imageURL);
                    startBrowserIntent(hyperlink);
                } else {
                    Log.d(TAG, "failed url : " + imageURL);
                    displayIamge(model, imageView);
                }
            }
        });
    }

    private void startBrowserIntent(String hyperlink) {
        Log.d(TAG, "startBrowserIntent : " + hyperlink);
        if (hyperlink.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.toast_no_link_url), Toast
                    .LENGTH_LONG).show();
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(hyperlink));
            context.startActivity(browserIntent);
        }
    }

    private void displayIamge(final ContentInfoModel model, ImageView imageView) {
        Glide
                .with(context)
                .load(model.getImageURL())
                .into(imageView)
                .onLoadStarted(context.getDrawable(R.drawable.item_no_image));
    }

    protected void setLayoutManagerType(int layoutManagerType) {
        this.layoutManagerType = layoutManagerType;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final TextView textViewTItle;
        private final TextView textViewAlt;
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textViewTItle = (TextView) view.findViewById(R.id.textView_title);
            this.textViewAlt = (TextView) view.findViewById(R.id.textView_alt);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
        }

        public View getView() {
            return view;
        }

        public TextView getTextViewTItle() {
            return textViewTItle;
        }

        public TextView getTextViewAlt() {
            return textViewAlt;
        }

        public ImageView getImageView() {
            return imageView;
        }

    }

}
