package com.sh.ilovepooq.search.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh.ilovepooq.R;
import com.sh.ilovepooq.model.SearchImageModel;
import com.sh.ilovepooq.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAutoAdapter<T> extends ArrayAdapter<T> {

    private List<T> list;
    private ArrayFilter filter;
    private int resource;
    private Context context;

    public SearchAutoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<T>
            list) {
        super(context, resource);
        this.context = context;
        this.list = list;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ArrayFilter();
        }
        return filter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder v;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            v = new ViewHolder(convertView);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        SearchImageModel.Document document = (SearchImageModel.Document) list.get(position);

        v.textView.setText(document.getDisplaySitename());
        ImageUtils.displayIamge(document.getThumbnailUrl(),
                v.imageView, android.R.drawable.ic_menu_report_image);
        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.text)
        TextView textView;
        @BindView(R.id.image)
        ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            results.values = list;
            results.count = list.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}
