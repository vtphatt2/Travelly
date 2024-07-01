package com.example.travelly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.NumberViewHolder> {
    private List<Integer> numberList;
    private int selectedPosition = -1;
    private Context context;

    public CalendarAdapter(List<Integer> numberList, Context context) {
        this.numberList = numberList;
        this.context = context;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar, parent, false);
        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        holder.numberText.setText(String.valueOf(numberList.get(position)));
        if (selectedPosition == position) {
            holder.numberText.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));
        } else {
            holder.numberText.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }

        holder.numberText.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return numberList.size();
    }

    public static class NumberViewHolder extends RecyclerView.ViewHolder {
        TextView numberText;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            numberText = itemView.findViewById(R.id.number_text);
        }
    }
}
