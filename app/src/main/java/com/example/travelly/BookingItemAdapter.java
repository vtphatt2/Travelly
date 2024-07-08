package com.example.travelly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BookingItemAdapter extends BaseAdapter {
    private Context context;
    private List<BookingItem> itemList;
    private View.OnClickListener onItemClickListener;

    public BookingItemAdapter(Context context, List<BookingItem> itemList, View.OnClickListener onItemClickListener) {
        this.context = context;
        this.itemList = itemList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.booking_item_view, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageViewItem);
        BookingItem item = itemList.get(position);
        imageView.setImageResource(item.getImageResId());

        convertView.setTag(position);
        convertView.setOnClickListener(onItemClickListener);

        return convertView;
    }
}
