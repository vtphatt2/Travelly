package com.example.travelly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.SlotViewHolder> {
    private List<String> options;
    private int selectedPosition = 0;
    private OnItemClickListener listener;

    void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    FilterAdapter(List<String> options) {
        this.options = options;
    }

    @Override
    public FilterAdapter.SlotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option_filter, parent, false);
        return new SlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterAdapter.SlotViewHolder holder, int position) {
        String option = options.get(position);
        holder.tvOption.setText(option);

        if (selectedPosition == position) {
            holder.tvOption.setBackgroundResource(R.drawable.selected_item_filter);
            holder.tvOption.setTextColor(ContextCompat.getColor(holder.tvOption.getContext(), R.color.white));
        } else {
            holder.tvOption.setBackgroundResource(R.drawable.default_item_filter);
            holder.tvOption.setTextColor(ContextCompat.getColor(holder.tvOption.getContext(), R.color.green_500));
        }

        holder.itemView.setOnClickListener(v -> {
            notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);

            if (listener != null) {
                listener.onItemClick(options.get(selectedPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public static class SlotViewHolder extends RecyclerView.ViewHolder {
        TextView tvOption;
        public SlotViewHolder(View itemView) {
            super(itemView);
            tvOption = itemView.findViewById(R.id.textViewOption);
        }
    }
}
