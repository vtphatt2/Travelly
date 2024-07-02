package com.example.travelly;

import android.database.Cursor;
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
import org.threeten.bp.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Flights extends AppCompatActivity {
    ImageButton btnBack;
    Date dateSelected;
    RecyclerView recyclerViewCalendar, recyclerViewTicket;
    FlightsDatabaseHandler db;

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

        List<Date> dateList = getAroundCurrentDate();
        List<FlightInfo> flightInfoList = getFlightInfoFromDatabase();
        flightInfoList = getFlightsByDateAndCities(dateList.get(0).getDay(), dateList.get(0).getMonth(), dateList.get(0).getYear(), null, null);

        TicketItemAdapter adapterTicket = new TicketItemAdapter(flightInfoList, this);
        CalendarAdapter adapterCalendar = new CalendarAdapter(dateList, this, date -> {
            dateSelected = date;
            adapterTicket.updateData(getFlightsByDateAndCities(dateSelected.getDay(), dateSelected.getMonth(), dateSelected.getYear(), null, null));
        });


        recyclerViewCalendar.setAdapter(adapterCalendar);
        recyclerViewTicket.setAdapter(adapterTicket);
    }

    private List<FlightInfo> getFlightsByDateAndCities(int flightDay, int flightMonth, int flightYear, String departureCity, String arrivalCity) {
        List<FlightInfo> flightInfoList = new ArrayList<>();
        db = new FlightsDatabaseHandler(this);
        Cursor res = db.getFlightsByDateAndCities(flightDay, flightMonth, flightYear, departureCity, arrivalCity);

        if (res.moveToFirst()) {
            do {
                int idIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_ID_FLIGHTS);
                int departureCityIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_DEPARTURE_CITY);
                int arrivalCityIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_ARRIVAL_CITY);
                int dayIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_FLIGHT_DAY);
                int monthIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_FLIGHT_MONTH);
                int yearIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_FLIGHT_YEAR);
                int departureTimeIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_DEPARTURE_TIME);
                int priceIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_PRICE);
                int numberIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_FLIGHT_NUMBER);

                if (idIndex >= 0 && departureCityIndex >= 0 && arrivalCityIndex >= 0 && dayIndex >= 0 && monthIndex >= 0 &&
                        yearIndex >= 0 && departureTimeIndex >= 0 && priceIndex >= 0 && numberIndex >= 0) {

                    int id = res.getInt(idIndex);
                    departureCity = res.getString(departureCityIndex);
                    arrivalCity = res.getString(arrivalCityIndex);
                    int day = res.getInt(dayIndex);
                    int month = res.getInt(monthIndex);
                    int year = res.getInt(yearIndex);
                    String departureTime = res.getString(departureTimeIndex);
                    float price = res.getFloat(priceIndex);
                    String number = res.getString(numberIndex);

                    FlightInfo flight = new FlightInfo(id, departureCity, arrivalCity, day, month, year, departureTime, price, number);
                    flightInfoList.add(flight);

                    Log.d("FlightInfo", "ID: " + res.getInt(idIndex) + ", DepartureCity: " + res.getString(departureCityIndex));
                }
            } while (res.moveToNext());
        }

        res.close();
        db.close();
        return flightInfoList;
    }

    private List<FlightInfo> getFlightInfoFromDatabase() {
        List<FlightInfo> flightInfoList = new ArrayList<>();

        db = new FlightsDatabaseHandler(this);
        db.clearAllRecords();
        db.insertSampleData();
        Cursor res = db.getAllFlights();

        if (res.moveToFirst()) {
            do {
                int idIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_ID_FLIGHTS);
                int departureCityIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_DEPARTURE_CITY);
                int arrivalCityIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_ARRIVAL_CITY);
                int dayIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_FLIGHT_DAY);
                int monthIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_FLIGHT_MONTH);
                int yearIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_FLIGHT_YEAR);
                int departureTimeIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_DEPARTURE_TIME);
                int priceIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_PRICE);
                int numberIndex = res.getColumnIndex(FlightsDatabaseHandler.COL_FLIGHT_NUMBER);

                if (idIndex >= 0 && departureCityIndex >= 0 && arrivalCityIndex >= 0 && dayIndex >= 0 && monthIndex >= 0 &&
                        yearIndex >= 0 && departureTimeIndex >= 0 && priceIndex >= 0 && numberIndex >= 0) {

                    int id = res.getInt(idIndex);
                    String departureCity = res.getString(departureCityIndex);
                    String arrivalCity = res.getString(arrivalCityIndex);
                    int day = res.getInt(dayIndex);
                    int month = res.getInt(monthIndex);
                    int year = res.getInt(yearIndex);
                    String departureTime = res.getString(departureTimeIndex);
                    float price = res.getFloat(priceIndex);
                    String number = res.getString(numberIndex);

                    FlightInfo flight = new FlightInfo(id, departureCity, arrivalCity, day, month, year, departureTime, price, number);
                    flightInfoList.add(flight);

                    Log.d("FlightInfo", "ID: " + res.getInt(idIndex) + ", DepartureCity: " + res.getString(departureCityIndex));
                }
            } while (res.moveToNext());
        }

        res.close();
        db.close();
        return flightInfoList;
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
}