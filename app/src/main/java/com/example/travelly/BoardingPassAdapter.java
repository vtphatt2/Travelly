package com.example.travelly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.travelly.R;

import java.util.List;
import java.util.Random;

public class BoardingPassAdapter extends BaseAdapter {
    private Context context;
    private List<String> items;
    private String departureCity, arrivalCity, departureDate, departureTime, number;

    public BoardingPassAdapter(Context context, List<String> items, String departureCity, String arrivalCity, String departureDate, String departureTime, String number) {
        this.context = context;
        this.items = items;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.number = number;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_boarding_pass, parent, false);
        }

        TextView tvDepartureCityCode, tvDepartureCity, tvArrivalCityCode, tvArrivalCity, tvDepartureDate, tvDepartureTime, tvGeneralInfo, tvSeat, tvTicket, tvCode;
        tvDepartureCityCode = convertView.findViewById(R.id.textViewDepartureCityCode);
        tvDepartureCity = convertView.findViewById(R.id.textViewDepartureCity);
        tvArrivalCityCode = convertView.findViewById(R.id.textViewArrivalCityCode);
        tvArrivalCity = convertView.findViewById(R.id.textViewArrivalCity);
        tvDepartureDate = convertView.findViewById(R.id.textViewDate);
        tvDepartureTime = convertView.findViewById(R.id.textDepartureTime);
        tvGeneralInfo = convertView.findViewById(R.id.textViewGeneralInfo);
        tvSeat = convertView.findViewById(R.id.tvSeat);
        tvTicket = convertView.findViewById(R.id.textViewTicket);
        tvCode = convertView.findViewById(R.id.textViewCode);

        tvDepartureCityCode.setText(getCodeName(departureCity));
        tvDepartureCity.setText(getCityName(departureCity));
        tvArrivalCityCode.setText(getCodeName(arrivalCity));
        tvArrivalCity.setText(getCityName(arrivalCity));
        tvDepartureDate.setText(departureDate);
        tvDepartureTime.setText(departureTime);
        tvGeneralInfo.setText(getCityName(departureCity) + " Airways Flight " + number);
        tvSeat.setText(items.get(position));
        tvTicket.setText(number + String.valueOf(position));

        Random random = new Random();
        char firstLetter = (char) (random.nextInt(26) + 'A');
        StringBuilder numbers = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            numbers.append(random.nextInt(10));
        }
        String code = firstLetter + numbers.toString();

        tvCode.setText(code);

        return convertView;
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
