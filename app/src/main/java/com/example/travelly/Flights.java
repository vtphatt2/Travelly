package com.example.travelly;

import android.content.Intent;
import android.os.Bundle;
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
    private ImageButton btnBack;
    private Date dateSelected;
    private RecyclerView recyclerViewCalendar, recyclerViewTicket;
    private TextView tvAnnounce;
    private FlightsDatabaseHandler db;
    private String departureCity, arrivalCity;


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

        Intent intent = getIntent();
        departureCity = intent.getStringExtra("DEPARTURE_CITY");
        arrivalCity = intent.getStringExtra("ARRIVAL_CITY");

        db = new FlightsDatabaseHandler(this);
        List<Date> dateList = getAroundCurrentDate();
        List<FlightInfo> flightInfoList = db.getFlightsByDateAndCities(dateList.get(0).getDay(), dateList.get(0).getMonth(), dateList.get(0).getYear(), departureCity, arrivalCity);

        TicketItemAdapter adapterTicket = new TicketItemAdapter(flightInfoList, this);;
        CalendarAdapter adapterCalendar = new CalendarAdapter(dateList, this, date -> {
            dateSelected = date;
            adapterTicket.updateData(db.getFlightsByDateAndCities(dateSelected.getDay(), dateSelected.getMonth(), dateSelected.getYear(), departureCity, arrivalCity));
        });

        recyclerViewCalendar.setAdapter(adapterCalendar);
        recyclerViewTicket.setAdapter(adapterTicket);

        tvAnnounce = findViewById(R.id.textViewAnnounce);
        tvAnnounce.setText(String.valueOf(flightInfoList.size()) + " flights available " + extractCityName(departureCity) + " to " + extractCityName(arrivalCity));
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