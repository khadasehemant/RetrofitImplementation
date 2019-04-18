package com.example.retrofitimplementation.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.retrofitimplementation.Models.RowData;
import com.example.retrofitimplementation.R;

import java.util.List;

public class CustomBaseAdapter  extends BaseAdapter {
    Context context;
    List<RowData> rowItems;

    public CustomBaseAdapter(Context context, List<RowData> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView img_icon;
        TextView txt_title;
        TextView txt_name;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
            holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowData rowItem = (RowData) getItem(position);

        holder.txt_name.setText(rowItem.getName());
        holder.txt_title.setText(rowItem.getTitle());

        Glide.with(context)
                .load(rowItem.getImage())
                .into(holder.img_icon);
        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}
