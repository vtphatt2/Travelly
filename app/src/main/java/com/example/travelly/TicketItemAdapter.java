package com.example.travelly;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelly.Database.FlightInfo;

import java.util.List;

public class TicketItemAdapter extends RecyclerView.Adapter<TicketItemAdapter.TicketItemViewHolder> {
    private List<FlightInfo> flightInfoList;
    private Context context;

    public TicketItemAdapter(List<FlightInfo> flightInfoList, Context context) {
        this.context = context;
        this.flightInfoList = flightInfoList;
    }

    @NonNull
    @Override
    public TicketItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketItemAdapter.TicketItemViewHolder holder, int position) {
        FlightInfo flightInfo = flightInfoList.get(position);
        holder.tvDepartureCity.setText(getCityName(flightInfo.getDepartureCity()));
        holder.tvCodeDepartureCity.setText(getCodeName(flightInfo.getDepartureCity()));
        holder.tvArrivalCity.setText(flightInfo.getArrivalCity());
        holder.tvDepartureDate.setText(flightInfo.getDate());
        holder.tvDepartureTime.setText(flightInfo.getDepartureTime());
        holder.tvPrice.setText("$" + String.valueOf(flightInfo.getPrice()));
        holder.tvNumber.setText(flightInfo.getNumber());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Seats.class);
            intent.putExtra("PRICE", flightInfo.getPrice());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return flightInfoList.size();
    }

    public static class TicketItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvDepartureCity, tvArrivalCity, tvCodeDepartureCity, tvCodeArrivalCity, tvDepartureDate, tvDepartureTime, tvPrice, tvNumber;

        public TicketItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDepartureCity = itemView.findViewById(R.id.textViewDepartureCity);
            tvArrivalCity = itemView.findViewById(R.id.textViewArrivalCity);
            tvCodeDepartureCity = itemView.findViewById(R.id.textViewCodeDepartureCity);
            tvCodeArrivalCity = itemView.findViewById(R.id.textViewCodeArrivalCity);
            tvDepartureDate = itemView.findViewById(R.id.textViewDepartureDate);
            tvDepartureTime = itemView.findViewById(R.id.textViewDepartureTime);
            tvPrice = itemView.findViewById(R.id.textViewPrice);
            tvNumber = itemView.findViewById(R.id.textViewNumber);
        }
    }

    public void updateData(List<FlightInfo> newDataList) {
        this.flightInfoList = newDataList;
        notifyDataSetChanged();
    }

    private String getCityName(String cityCode) {
        int startIndex = cityCode.indexOf(" (");
        if (startIndex != -1) {
            return cityCode.substring(0, startIndex);
        } else {
            return "";
        }
    }

    private String getCodeName(String cityCode) {
        int startIndex = cityCode.indexOf("(");
        int endIndex = cityCode.indexOf(")");

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return cityCode.substring(startIndex + 1, endIndex);
        } else {
            return "";
        }
    }
}
