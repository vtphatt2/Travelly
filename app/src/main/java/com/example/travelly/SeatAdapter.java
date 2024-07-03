package com.example.travelly;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class SeatAdapter extends BaseAdapter {
    private Context context;
    private List<Integer> data;
    private LayoutInflater inflater;

    SeatAdapter(Context context, List<Integer> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_seats, parent, false);
        }

        Button btnSeat1 = convertView.findViewById(R.id.seat1);
        Button btnSeat2 = convertView.findViewById(R.id.seat2);
        Button btnSeat3 = convertView.findViewById(R.id.seat3);
        Button btnSeat4 = convertView.findViewById(R.id.seat4);
        TextView tvSeatRow = convertView.findViewById(R.id.textViewSeatRow);
        tvSeatRow.setText(String.valueOf(data.get(position)));

        btnSeat1.setOnClickListener(v -> btnSeat1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000"))));
        btnSeat2.setOnClickListener(v -> btnSeat2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000"))));
        btnSeat3.setOnClickListener(v -> btnSeat3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000"))));
        btnSeat4.setOnClickListener(v -> btnSeat4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000"))));

        return convertView;
    }
}
