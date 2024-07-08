package com.example.travelly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Seats extends AppCompatActivity implements SeatAdapter.OnButtonClickListener {
    private ListView listViewSeat;
    private SeatAdapter adapterSeat;
    private List<String> seatCode = new ArrayList<>();
    private TextView tvSelectedSeat, tvTotalPrice;
    private ImageButton btnBack;
    private float priceTicket;
    private Button btnContinue;
    private String departureCity, arrivalCity, departureDate, departureTime, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seats);
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
        priceTicket = intent.getFloatExtra("PRICE", 0);

        List<Integer> SeatRow = new ArrayList<Integer>();
        listViewSeat = findViewById(R.id.listViewSeat);
        for (int i = 1 ; i <= 40 ; ++i) {
            SeatRow.add(i);
        }
        adapterSeat = new SeatAdapter(this, SeatRow, this);
        listViewSeat.setAdapter(adapterSeat);

        tvSelectedSeat = findViewById(R.id.textViewSelectedSeat);
        tvTotalPrice = findViewById(R.id.textViewTotalPrice);

        btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnContinue = findViewById(R.id.buttonContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seatCode.size() > 0) {
                    Intent intent = new Intent(Seats.this, BoardingPass.class);
                    intent.putExtra("DEPARTURE_CITY", departureCity);
                    intent.putExtra("ARRIVAL_CITY", arrivalCity);
                    intent.putExtra("DEPARTURE_DATE", departureDate);
                    intent.putExtra("DEPARTURE_TIME", departureTime);
                    intent.putExtra("NUMBER", number);
                    intent.putExtra("SEAT_CODE", String.join(",", seatCode));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onButtonClick(int position, int buttonId, boolean toggle) {
        String seatcode;
        position++;

        if (buttonId == 1) {
            seatcode = String.valueOf(position) + "A";
        }
        else if (buttonId == 2) {
            seatcode = String.valueOf(position) + "B";
        }
        else if (buttonId == 3) {
            seatcode = String.valueOf(position) + "C";
        }
        else {
            seatcode = String.valueOf(position) + "D";
        }

        if (toggle) {
            seatCode.add(seatcode);
        }
        else {
            seatCode.removeIf(str -> str.equals(seatcode));
        }

        String str = "Traveller " + String.valueOf(seatCode.size()) + " / Seat ";
        for (int i = 0 ; i < seatCode.size() ; ++i) {
            if (i == seatCode.size() - 1) {
                str += seatCode.get(i);
            }
            else {
                str += seatCode.get(i) + ", ";
            }
        }
        tvSelectedSeat.setText(str);
        tvTotalPrice.setText(String.valueOf(seatCode.size() * priceTicket));
    }
}