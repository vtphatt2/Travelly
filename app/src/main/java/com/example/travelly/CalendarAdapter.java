package com.example.travelly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private List<Date> dateList;
    private int selectedPosition = 0;
    private Context context;

    public CalendarAdapter(List<Date> dateList, Context context) {
        this.dateList = dateList;
        this.context = context;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar, parent, false);
        return new CalendarViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
//        holder.numberText.setText(String.valueOf(numberList.get(position)));
        holder.tvDayOfWeek.setText(dateList.get(position).getDayOfWeek());
        holder.tvDay.setText(dateList.get(position).getDay());

        if (selectedPosition == position) {
            holder.llDate.setBackgroundColor(context.getResources().getColor(R.color.peach_50));
        } else {
            holder.llDate.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        }

        holder.llDate.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public static class CalendarViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llDate;
        TextView tvDay, tvDayOfWeek;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            llDate = itemView.findViewById(R.id.llDate);
            tvDay = llDate.findViewById(R.id.textViewDay);
            tvDayOfWeek = llDate.findViewById(R.id.textViewDayOfWeek);
        }
    }
}
