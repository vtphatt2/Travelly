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
    private int selectedPosition = -1;
    private OnItemClickListener listener;
    private String savedOption;
    private int adapterType;

    void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String item, int type);
    }

    public void setSavedOption(String savedOption) {
        this.savedOption = savedOption;
        for (int i = 0 ; i < options.size() ; ++i) {
            if (options.get(i).equals(savedOption)) {
                selectedPosition = i;
            }
        }
    }

    FilterAdapter(List<String> options, int type) {
        this.options = options;
        this.adapterType = type;
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

        if (holder.tvOption.getText().toString().equals(savedOption)) {
            selectedPosition = position;
            savedOption = "";
        }

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
                listener.onItemClick(options.get(selectedPosition), adapterType);
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
