package com.example.travelly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelly.Database.FlightInfo;
import com.example.travelly.Database.FlightsDatabaseHandler;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.TextStyle;
import org.threeten.bp.temporal.ChronoUnit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Flights extends AppCompatActivity {
    private ImageButton btnBack, btnFilter;
    private Date dateSelected;
    private RecyclerView recyclerViewCalendar, recyclerViewTicket;
    private TextView tvAnnounce;
    private FlightsDatabaseHandler db;
    private String departureCity, arrivalCity, departureDate;
    private List<FlightInfo> flightInfoList;
    private TicketItemAdapter adapterTicket;
    private CalendarAdapter adapterCalendar;
    private String departureTimeRange = "All", arrivalTimeRange = "All", sortBy = "Price";
    private double minPrice = 300, maxPrice = 900;
    private boolean isChooseCoffee = false, isChooseForkKnife = false, isChooseWifi = false, isChooseSnowFlake = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flights);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        departureCity = intent.getStringExtra("DEPARTURE_CITY");
        arrivalCity = intent.getStringExtra("ARRIVAL_CITY");
        departureDate = intent.getStringExtra("DEPARTURE_DATE");

        btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerViewCalendar = findViewById(R.id.recyclerViewCalendar);
        recyclerViewTicket = findViewById(R.id.recyclerViewTicket);

        recyclerViewCalendar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTicket.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        db = new FlightsDatabaseHandler(this);
        List<Date> dateList = getAroundCurrentDate();
        dateSelected = dateList.get(0);
        flightInfoList = db.getFlightsByDateAndCities(dateSelected.getDay(), dateSelected.getMonth(), dateSelected.getYear(), departureCity, arrivalCity);

        adapterTicket = new TicketItemAdapter(flightInfoList, this);;
        adapterCalendar = new CalendarAdapter(dateList, this, calculateDateDifference(departureDate), date -> {
            dateSelected = date;
            flightInfoList = db.getFlightsWithConditions(departureCity, arrivalCity, dateSelected.getDay(), dateSelected.getMonth(), dateSelected.getYear(), minPrice, maxPrice);
            flightInfoList = flightInDepartureRange(flightInfoList, departureTimeRange);
            if (sortBy.equals("Price")) {
                sortFlightsByPrice(flightInfoList);
            }
            else if (sortBy.equals("Departure time")) {
                sortFlightsByDepartureTime(flightInfoList);
            }
            else if (sortBy.equals("Lowest fare")) {
                retainLowestPriceFlight(flightInfoList);
            }
            adapterTicket.updateData(flightInfoList);
            tvAnnounce.setText(String.valueOf(flightInfoList.size()) + " flights available " + extractCityName(departureCity) + " to " + extractCityName(arrivalCity));
        });

        recyclerViewCalendar.setAdapter(adapterCalendar);
        recyclerViewTicket.setAdapter(adapterTicket);

        tvAnnounce = findViewById(R.id.textViewAnnounce);
        tvAnnounce.setText(String.valueOf(flightInfoList.size()) + " flights available " + extractCityName(departureCity) + " to " + extractCityName(arrivalCity));

        btnFilter = findViewById(R.id.buttonFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Flights.this, Filter.class);
                intent.putExtra("DEPARTURE_TIME_RANGE", departureTimeRange);
                intent.putExtra("ARRIVAL_TIME_RANGE", arrivalTimeRange);
                intent.putExtra("MIN-PRICE", minPrice);
                intent.putExtra("MAX-PRICE", maxPrice);
                intent.putExtra("SORT-BY", sortBy);
                intent.putExtra("COFFEE", isChooseCoffee);
                intent.putExtra("FORK_KNIFE", isChooseForkKnife);
                intent.putExtra("WIFI", isChooseWifi);
                intent.putExtra("SNOW_FLAKE", isChooseSnowFlake);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                departureTimeRange = data.getStringExtra("DEPARTURE");
                arrivalTimeRange = data.getStringExtra("ARRIVAL");
                minPrice = data.getDoubleExtra("PRICE-MIN", 200);
                maxPrice = data.getDoubleExtra("PRICE-MAX", 1000);
                sortBy = data.getStringExtra("SORT-BY");
                isChooseCoffee = data.getBooleanExtra("COFFEE", false);
                isChooseForkKnife = data.getBooleanExtra("FORK_KNIFE", false);
                isChooseWifi = data.getBooleanExtra("WIFI", false);
                isChooseSnowFlake = data.getBooleanExtra("SNOW_FLAKE", false);

                flightInfoList = db.getFlightsWithConditions(departureCity, arrivalCity, dateSelected.getDay(), dateSelected.getMonth(), dateSelected.getYear(), minPrice, maxPrice);
                flightInfoList = flightInDepartureRange(flightInfoList, departureTimeRange);
                if (sortBy.equals("Price")) {
                    sortFlightsByPrice(flightInfoList);
                }
                else if (sortBy.equals("Departure time")) {
                    sortFlightsByDepartureTime(flightInfoList);
                }
                else if (sortBy.equals("Lowest fare")) {
                    retainLowestPriceFlight(flightInfoList);
                }
                adapterTicket.updateData(flightInfoList);

                tvAnnounce.setText(String.valueOf(flightInfoList.size()) + " flights available " + extractCityName(departureCity) + " to " + extractCityName(arrivalCity));
            }
        }
    }

    public static void retainLowestPriceFlight(List<FlightInfo> flightInfoList) {
        if (flightInfoList == null || flightInfoList.isEmpty()) {
            return; // If the list is null or empty, do nothing
        }

        FlightInfo lowestPriceFlight = flightInfoList.get(0);
        for (FlightInfo flight : flightInfoList) {
            if (flight.getPrice() < lowestPriceFlight.getPrice()) {
                lowestPriceFlight = flight;
            }
        }

        flightInfoList.clear(); // Clear the list
        flightInfoList.add(lowestPriceFlight); // Add the flight with the lowest price back to the list
    }

    public static void sortFlightsByDepartureTime(List<FlightInfo> flightInfoList) {
        Collections.sort(flightInfoList, new Comparator<FlightInfo>() {
            @Override
            public int compare(FlightInfo f1, FlightInfo f2) {
                return (convertTo24HourFormatHaveDistance(f1.getDepartureTime()).compareTo(convertTo24HourFormatHaveDistance(f2.getDepartureTime())));
            }
        });
    }

    public static void sortFlightsByPrice(List<FlightInfo> flightInfoList) {
        Collections.sort(flightInfoList, new Comparator<FlightInfo>() {
            @Override
            public int compare(FlightInfo f1, FlightInfo f2) {
                return Float.compare(f1.getPrice(), f2.getPrice());
            }
        });
    }

    public static List<FlightInfo> flightInDepartureRange(List<FlightInfo> flightInfoList, String departureTimeRange) {
        Map<String, String[]> timeRanges = new HashMap<>();
        timeRanges.put("12AM - 04AM", new String[]{"00:00", "04:00"});
        timeRanges.put("04AM - 08AM", new String[]{"04:00", "08:00"});
        timeRanges.put("08AM - 12PM", new String[]{"08:00", "12:00"});
        timeRanges.put("12PM - 04PM", new String[]{"12:00", "16:00"});
        timeRanges.put("04PM - 08PM", new String[]{"16:00", "20:00"});
        timeRanges.put("08PM - 12AM", new String[]{"20:00", "00:00"});

        if (departureTimeRange.equals("All")) {
            return flightInfoList;
        }

        String[] range = timeRanges.get(departureTimeRange);
        if (range == null) {
            return new ArrayList<>();
        }

        String low = range[0];
        String high = range[1];

        List<FlightInfo> newFlightInfoList = new ArrayList<>();
        for (FlightInfo flightInfo : flightInfoList) {
            String date = flightInfo.getDepartureTime();
            String convertedTime = convertTo24HourFormatHaveDistance(date);
            if (low.compareTo(convertedTime) <= 0 && convertedTime.compareTo(high) <= 0) {
                newFlightInfoList.add(flightInfo);
            }
        }
        return newFlightInfoList;
    }

    private static String convertTo24HourFormatHaveDistance(String timeStr) {
        // Split the time string into components
        String[] parts = timeStr.split(" ");
        String time = parts[0];
        String period = parts[1];
        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        // Convert the hours based on the period (AM/PM)
        if (period.equals("AM")) {
            if (hours == 12) {
                hours = 0;  // Midnight case
            }
        } else {  // period is "PM"
            if (hours != 12) {
                hours += 12;  // Afternoon and evening case
            }
        }
        return String.format("%02d:%02d", hours, minutes);
    }

    public static int calculateDateDifference(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inputDate = LocalDate.parse(date, formatter);
        LocalDate today = LocalDate.now();

        return Math.abs((int) ChronoUnit.DAYS.between(inputDate, today));
    }

    private List<Date> getAroundCurrentDate() {
        List<Date> dates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 30; i++) {
            LocalDate date = currentDate.plusDays(i);
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            int day = date.getDayOfMonth();
            int month = date.getMonthValue();
            int year = date.getYear();
            dates.add(new Date(dayOfWeek, day, month, year));
        }
        return dates;
    }

    private String extractCityName(String input) {
        return input.replaceAll("\\s*\\(.*?\\)", "").trim();
    }
}