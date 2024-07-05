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

import androidx.core.content.ContextCompat;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SeatAdapter extends BaseAdapter {
    private Context context;
    private List<Integer> data;
    private Set<Integer> bookedSeats, selectedSeats;
    private OnButtonClickListener listener;

    SeatAdapter(Context context, List<Integer> data, OnButtonClickListener listener) {
        this.context = context;
        this.data = data;
        this.bookedSeats = new HashSet<>();
        this.selectedSeats = new HashSet<>();
        this.listener = listener;
        populateBookedSeats();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_seat, parent, false);
        }

        Button btnSeat1 = convertView.findViewById(R.id.seat1);
        Button btnSeat2 = convertView.findViewById(R.id.seat2);
        Button btnSeat3 = convertView.findViewById(R.id.seat3);
        Button btnSeat4 = convertView.findViewById(R.id.seat4);
        TextView tvSeatRow = convertView.findViewById(R.id.textViewSeatRow);
        tvSeatRow.setText(String.valueOf(data.get(position)));

        int seat1Position = position * 4;
        int seat2Position = seat1Position + 1;
        int seat3Position = seat1Position + 2;
        int seat4Position = seat1Position + 3;
        setSeatColorBooked(btnSeat1, seat1Position);
        setSeatColorBooked(btnSeat2, seat2Position);
        setSeatColorBooked(btnSeat3, seat3Position);
        setSeatColorBooked(btnSeat4, seat4Position);

        setSeatColorSelected(btnSeat1, seat1Position);
        setSeatColorSelected(btnSeat2, seat2Position);
        setSeatColorSelected(btnSeat3, seat3Position);
        setSeatColorSelected(btnSeat4, seat4Position);

        btnSeat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick(position, 1, toggleSeatColor(btnSeat1));
                selectedSeats.add(4 * position);
            }
        });

        btnSeat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick(position, 2, toggleSeatColor(btnSeat2));
                selectedSeats.add(4 * position + 1);
            }
        });

        btnSeat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick(position, 3, toggleSeatColor(btnSeat3));
                selectedSeats.add(4 * position + 2);
            }
        });

        btnSeat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick(position, 4, toggleSeatColor(btnSeat4));
                selectedSeats.add(4 * position + 3);
            }
        });

        return convertView;
    }

    public interface OnButtonClickListener {
        void onButtonClick(int position, int buttonId, boolean toggle);
    }

    private void populateBookedSeats() {
        Random random = new Random();
        int n = data.size();
        int numberOfBookedSeats = random.nextInt(n/4 + 1) + 5;
        while (bookedSeats.size() < numberOfBookedSeats) {
            bookedSeats.add(random.nextInt(n * 4));
        }
    }

    private void setSeatColorBooked(Button button, int position) {
        if (bookedSeats.contains(position)) {
            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_700)));
        } else {
            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_50)));
        }
    }

    private void setSeatColorSelected(Button button, int position) {
        if (selectedSeats.contains(position)) {
            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.peach_300)));
        } else if (!bookedSeats.contains(position)){
            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green_50)));
        }
    }

    private boolean toggleSeatColor(Button button) {
        int green50 = ContextCompat.getColor(context, R.color.green_50);
        int peach300 = ContextCompat.getColor(context, R.color.peach_300);

        ColorStateList colorStateList = button.getBackgroundTintList();
        if (colorStateList != null) {
            int currentColor = colorStateList.getDefaultColor();
            if (currentColor == green50) {
                button.setBackgroundTintList(ColorStateList.valueOf(peach300));
                return true;
            }
            else if (currentColor == peach300) {
                button.setBackgroundTintList(ColorStateList.valueOf(green50));
            }
        }
        return false;
    }
}
