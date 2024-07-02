package com.example.travelly;

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

        CalendarAdapter adapterCalendar = new CalendarAdapter(dateList, this, date -> {
            dateSelected = date;
        });
        TicketItemAdapter adapterTicket = new TicketItemAdapter(flightInfoList, this);

        recyclerViewCalendar.setAdapter(adapterCalendar);
        recyclerViewTicket.setAdapter(adapterTicket);
    }

    private List<FlightInfo> getFlightInfoFromDatabase() {
        List<FlightInfo> flightInfoList = new ArrayList<>();

        return flightInfoList;
    }

    private List<Date> getAroundCurrentDate() {
        List<Date> dates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 30; i++) {
            LocalDate date = currentDate.plusDays(i);
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            int day = date.getDayOfMonth();
            dates.add(new Date(dayOfWeek, day));
        }

        return dates;
    }
}