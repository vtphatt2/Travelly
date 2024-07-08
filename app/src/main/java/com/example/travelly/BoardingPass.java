package com.example.travelly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelly.Database.BoardingPassAdapter;

import java.util.Arrays;
import java.util.List;

public class BoardingPass extends AppCompatActivity {
    private ImageButton btnBack;
    private String departureCity, arrivalCity, departureDate, departureTime, number;
    private List<String> seatCode;
    private ListView lvBoardingPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_boarding_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        departureCity = intent.getStringExtra("DEPARTURE_CITY");
        arrivalCity = intent.getStringExtra("ARRIVAL_CITY");
        departureDate = intent.getStringExtra("DEPARTURE_DATE");
        departureTime = intent.getStringExtra("DEPARTURE_TIME");
        number = intent.getStringExtra("NUMBER");
        String listString = getIntent().getStringExtra("SEAT_CODE");
        if (listString != null) {
            seatCode = Arrays.asList(listString.split(","));
        }

        btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvBoardingPass = findViewById(R.id.listViewBoardingPass);
        BoardingPassAdapter adapter = new BoardingPassAdapter(this, seatCode, departureCity, arrivalCity, departureDate, departureTime, number);
        lvBoardingPass.setAdapter(adapter);
    }
}